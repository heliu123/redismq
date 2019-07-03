>利用 `redis` 实现延迟消息队列。
```
├─redismq-common    //生产者和消费者公用代码，比如队列信息和redis配置信息
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─sqlb
│  │  │  │          └─redismq
│  │  │  │              └─common
│  │  │  │                  ├─config    //存放mq的消费队列胚子信息
│  │  │  │                  ├─msmq      //核心mq代码
│  │  │  │                  └─utils     //操作redis的工具类
│  │  │  └─resources
│  │  │      └─configure
├─redismq-consumer      //消费者
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─sqlb
│  │  │  │          └─redismq
│  │  │  │              └─consumer
│  │  │  │                  └─task   //包含定时任务，用来消费队列消息
│  │  │  └─resources
└─redismq-product       //生产者
    ├─src
    │  ├─main
    │  │  ├─java
    │  │  │  └─com
    │  │  │      └─sqlb
    │  │  │          └─redismq
    │  │  │              └─product
    │  │  │                  └─controller   //产生消息的生产者
    │  │  └─resources
```

实际上是利用了定时任务 `@Scheduled(cron="*/5 * * * * *")` 每隔5秒钟去redis服务中取出消费者发出的存储在redis中的消息，然后判断是否要消费。


原文访问地址：https://blog.csdn.net/She_lock/article/details/80609397