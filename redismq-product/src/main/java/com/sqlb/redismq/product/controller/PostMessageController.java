package com.sqlb.redismq.product.controller;


import com.alibaba.fastjson.JSONObject;
import com.sqlb.redismq.common.msmq.Message;
import com.sqlb.redismq.common.msmq.RedisMQ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @作者：潘恩赐
 * @Email: 666666@panenci.com
 * @创建时间：11:24 2018/6/7
 * @描叙：  发送消息
 * @修改人：
 * @修改时间：
 */
@RestController
@PropertySource("classpath:configure/redis.properties")
public class PostMessageController {

    @Resource
    private RedisMQ redisMQ;
    @Value("${mq.queue.first}")
    private String MQ_QUEUE_FIRST;

    @RequestMapping("/postMsg")
    public Message testMq(@RequestParam(value = "msg",defaultValue = "你好，这是一条来至product的短信") String msg) {

        JSONObject jObj = new JSONObject();
        jObj.put("msg",  msg);

        String seqId = UUID.randomUUID().toString();

        // 将有效信息放入消息队列和消息池中
        Message message = new Message();
        message.setBody(jObj.toJSONString());
        // 可以添加延迟配置
        message.setDelay(1*60*1000);
        message.setTopic("SMS");
        message.setCreateTime(System.currentTimeMillis());
        message.setId(seqId);
        // 设置消息池ttl，防止长期占用
        message.setTtl(20 * 60);
        message.setStatus(0);
        message.setPriority(0);

        //将消息存入redis中
        redisMQ.addMsgPool(message);

        redisMQ.enMessage(MQ_QUEUE_FIRST,
                message.getCreateTime() + message.getDelay() + message.getPriority(), message.getId());

       return message;
    }

}
