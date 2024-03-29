package com.sqlb.redismq.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @作者：潘恩赐
 * @Email: 666666@panenci.com
 * @创建时间：10:10 2018/6/6
 * @描叙：
 * @修改人：
 * @修改时间：
 */
@Configuration
@PropertySource("classpath:configure/redis.properties")
public class RedisConfig {

    @Bean(name = "jedisPool")
    @Primary
    public JedisPool getJedisPool() {
        return new JedisPool(jedisPoolConfig(), host, port, 0, null, database);
    }

    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(5000);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(10000);
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(10000);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        jedisPoolConfig.setJmxEnabled(true);
        jedisPoolConfig.setJmxNamePrefix("");
        jedisPoolConfig.setBlockWhenExhausted(false);

        return jedisPoolConfig;
    }

    @Value("${redis.master.host}")
    private String host;
    @Value("${redis.master.port}")
    private int port;
    @Value("${redis.master.password}")
    private String password;
    @Value("${redis.master.database}")
    private int database;
    @Value("${redis.pool.maxActive}")
    private int maxTotal;
    @Value("${redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${redis.pool.maxActive}")
    private int numTestsPerEvictionRun;
    @Value("${redis.pool.maxWait}")
    private int maxWaitMillis;
    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${redis.pool.testOnReturn}")
    private boolean testOnReturn;
}
