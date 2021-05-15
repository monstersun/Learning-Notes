# Stream

为了降低切换消息中间件的成本，在消息中间件之上多出来一个消息驱动层，Stream就是为了解决这个问题诞生的

目前只支持RabbitMq和Kafka



通过Binder层来解耦



## 使用

1. pom

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
   </dependency>
   ```

2. yml

   

   ```yml
   spring:
     cloud:
       stream:
         binders:
           defaultRabbit: # 名称
             type: rabbit # 消息组件名称
             environment: # 中间件配置
               spring:
                 rabbitmq:
                   host: localhost
                   port: 5672
                   username: guest
                   password: guest
         bindings: # 服务的整合处理
           output: # 这个名字是一个通道的名称！（在注入bean的时候切记要一致）
             destination: studyExchange # rabbit 要使用的Exchange
             content-type: application/json # 消息格式
             binder: defaultRabbit # 设置绑定消息服务的具体设置
   ```

3. 生产者和消费者

   1. 生产者搭建
   2. 消费者搭建

# 重复消费

加 group就行了