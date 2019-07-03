package com.sqlb.redismq.common.config;

import com.sqlb.redismq.common.msmq.RedisMQ;
import com.sqlb.redismq.common.msmq.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者：潘恩赐
 * @Email: 666666@panenci.com
 * @创建时间：10:04 2018/6/6
 * @描叙：  消息队列配置
 * @修改人：
 * @修改时间：
 */
@Configuration
@PropertySource("classpath:configure/mq.properties")
public class MqConfig {

    @Bean(name = "redisMQ")
    @Primary
    public RedisMQ getRedisMq() {
        RedisMQ redisMQ = new RedisMQ();
        // 配置监听队列元素数量
        redisMQ.setMonitorCount(monitorCount);
        // 配置路由表
        redisMQ.setRoutes(routeList());
        return redisMQ;
    }

    /**
     * 返回路由表
     * @return
     */
    public List<Route> routeList() {
        List<Route> routeList = new ArrayList<>();
        Route routeFirst = new Route(queueFirst, listFirst);
        Route routeSecond = new Route(queueSecond, listSecond);
        routeList.add(routeFirst);
        routeList.add(routeSecond);
        return routeList;
    }

    @Value("${mq.monitor.count}")
    private int monitorCount;
    @Value("${mq.queue.first}")
    private String queueFirst;
    @Value("${mq.queue.second}")
    private String queueSecond;
    @Value("${mq.consumer.first}")
    private String listFirst;
    @Value("${mq.consumer.second}")
    private String listSecond;
}