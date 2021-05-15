## OpenFeign

### 1. 概述

1. 官方文档

   https://docs.spring.io/spring-cloud-openfeign/docs/2.2.5.RELEASE/reference/html/#spring-cloud-feign

2. 用处

   可以使http客户端调用更方便，简化了我们自己封装客户端调用其他服务的接口。

   一个接口加一个注解就能完成客户端访问的代码编写


### 2. 使用方法

1. 引入依赖

   ```xml
   <!-- openfeign-->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-openfeign</artifactId>
   </dependency>
   ```

2. 启动类增加注解

   ```java
   @SpringBootApplication
   @EnableDiscoveryClient
   @EnableFeignClients
   public class OpenFeignOrder {
       public static void main(String[] args) {
           SpringApplication.run(OpenFeignOrder.class, args);
       }
   }
   ```

3. 新建接口

   ```java
   @Component // 不可少，少了就会报bean找不到
   @FeignClient(value = "cloud-payment-service")
   public interface PaymentFeignService {
       // @RequestParam注解不可少，少了就会报415的错误
       @GetMapping(value = "/payment")
       CommonResultDto<Payment> getPaymentById(@RequestParam("id") String id);
   }
   ```

   ![image-20200922164049878](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200922164049878.png)

## 3. Feign超时

1. 默认等待一秒，超过1秒就抛出异常

2. 修改超时等待时间

   ```yml
   # 顶格写，没有前缀
   ribbon:
     # openfeign有两个时长，一个是与目标主机建立过程的时间，一个是建立连接后再获取内容的过程锁等待的时间
     # 读取超时时长
     ReadTimeout: 5000
     # 连接超时时长
     ConnectTimeout: 5000
   ```
   
3. ribbon可配置部分

   ```properties
   ribbon.ReadTimeout=1000 // 处理请求的超时时间，默认为1秒
   ribbon.ConnectTimeout=1000 //连接建立的超时时长，默认1秒
   ribbon.MaxAutoRetries=1 //同一台实例的最大重试次数，但是不包括首次调用，默认为1次
   ribbon.MaxAutoRetriesNextServer=0 //重试负载均衡其他实例的最大重试次数，不包括首次调用，默认为0次
   ribbon.OkToRetryOnAllOperations=false //是否对所有操作都重试，默认false
   ```

4. Feign可配置部分

   ```properties
   # 使用hystrix代替
   feign.hystrix.enabled=false //Feign是否启用断路器,默认为false
   feign.client.config.default.connectTimeout=10000 //Feign的连接建立超时时间，默认为10秒
   feign.client.config.default.readTimeout=60000 //Feign的请求处理超时时间，默认为60
   feign.client.config.default.retryer=feign.Retryer.Default //Feign使用默认的超时配置，在该类源码中可见，默认单次请求
   ```

### 4. OpenFeign日志打印功能

OpenFeign提供了日志打印的功能

1. 日志级别

   1. NONE： 默认的，不显示任何日志
   2. BASIC： 仅记录请求方法、URL、响应状态码、执行时间
   3. HEADERS：除了BASIC中定义的信息外，还有请求和响应头信息
   4. FULL：除了Header中定义的信息之外，还有请求和响应的正文以及元数据

2. 使用方法

   1. 创建日志级别（openFeign）配置类

   ```java
   @Configuration
   public class FeignConfig {
   
       @Bean
       public Logger.Level feignLoggerLevelFull() {
           return Logger.Level.FULL;s
       }
   }
   ```

   2. application.yml中打开日志

   ```yml
   logging:
     level:
       com.atguigu.springcloud.service.PaymentFeignService: debug
   ```

### 4. 使用了OpenFeign之后的项目架构

1. 将接口写在api包中，从service包里面单独抽出来

![image-20200924130310214](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200924130310214.png)

2. 创建接口controller接口

```java
public interface UserService {

    @PostMapping(value = "/user/userCheck", consumes = MediaType.APPLICATION_JSON_VALUE)
    CommonResultDto userCheck(@RequestBody UserInfo userInfo);

}
```

3. service包中实现接口
4. 在需要调用的微服务中创建FeignClient接口继承controller接口

```java
@Component
@FeignClient(value = "mytools-user-service")
public interface UserCheckClient extends UserService {
    
}
```



