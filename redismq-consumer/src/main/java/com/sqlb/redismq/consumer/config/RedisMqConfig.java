package com.sqlb.redismq.consumer.config;

import com.sqlb.redismq.common.msmq.RedisMQ;
import com.sqlb.redismq.common.msmq.Route;
import com.sqlb.redismq.common.utils.JedisUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @作者：潘恩赐
 * @Email: 666666@panenci.com
 * @创建时间：8:32 2018/6/11
 * @描叙：  组建供 消费的消息队列
 * @修改人：
 * @修改时间：
 */
@Component
public class RedisMqConfig  {

    @Resource
    private JedisUtils jedisUtils;

    @Resource
    private RedisMQ redisMQ;

    /**
     * 消息队列监听器<br>
     * 监听所有路由器，将消息队列中的消息路由到待消费列表
     */
    @Scheduled(cron="*/5 * * * * *")
    public void monitor() {
        // 获取消息路由
        int route_size;
        if (null == redisMQ.getRoutes() || 1 > (route_size = redisMQ.getRoutes().size())) return;
        String queue, list;
        Set<String> set;
        for (int i = 0; i < route_size; i++) {
            queue = redisMQ.getRoutes().get(i).getQueue();
            list = redisMQ.getRoutes().get(i).getList();
            //获取队列中的消息，根据score降序，score大的优先
            set = jedisUtils.getSoredSetByRange(queue, 0, redisMQ.getMonitorCount(), true);
            if (null != set) {
                long current = System.currentTimeMillis();
                long score;
                for (String id : set) {
                    //取出队列score
                    score = jedisUtils.getScore(queue, id).longValue();
                    if (current >= score) {
                        // 添加到list 一个先进先出队列
                        if (jedisUtils.insertList(list, id)) {
                            // 删除queue中的元素
                            redisMQ.deMessage(queue, id);
                        } /// if end~
                    } /// if end~
                } /// for end~
            } /// if end~
        } /// for end~
    }
}
