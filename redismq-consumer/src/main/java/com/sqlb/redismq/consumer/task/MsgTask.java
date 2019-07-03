package com.sqlb.redismq.consumer.task;

import com.alibaba.fastjson.JSONObject;
import com.sqlb.redismq.common.msmq.RedisMQ;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @作者：潘恩赐
 * @Email: 666666@panenci.com
 * @创建时间：10:06 2018/6/6
 * @描叙：  消费者
 * @修改人：
 * @修改时间：
 */
@Component
public class MsgTask {

    @Resource
    private RedisMQ redisMQ;
    // @Value("${mq.list.first}") private String MQ_LIST_FIRST;

    @Scheduled(cron="*/5 * * * * *")
    public void sendMsg() {
        // 消费 这里是demo，所以只取出来List:1中的待消费队列，因为已知只在List:1存了消息
        List<String> msgs = redisMQ.consume(redisMQ.getRoutes().get(0).getList());
        int len;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null != msgs && 0 < (len = msgs.size())) {
            // 将每一条消息转为JSONObject
            JSONObject jObj;
            for (int i = 0; i < len; i++) {
                if (!StringUtils.isEmpty(msgs.get(i))) {
                    jObj = JSONObject.parseObject(msgs.get(i));
                    // 取出消息
                    Date d = new Date();
                    System.out.println(sdf.format(d)+ jObj.toJSONString());
                }
            }
        }
    }
}
