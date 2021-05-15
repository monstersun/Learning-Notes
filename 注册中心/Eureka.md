

## Eureka服务注册中心

![image-20200918170802979](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200918170802979.png)



### 1. 基础知识

1. 服务治理

只要是微服务架构势必涉及到服务间调用的问题，如果项目的体量不大，那么微服务之间的相互调用能够理得清，我们能够人为的去掌控。一旦项目的体量很大，服务间的调用非常复杂，这个时候就需要有第三方介入来管理各个服务，这就是服务治理（自己理解）

通过服务注册中心，服务可以实现服务的注册与发现，负载均衡，容灾处理

2. 服务注册

![image-20200919104037348](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200919104037348.png)

3. Eureka俩组件

EurekaServer注册中心和EurekaClient客户端

保持心跳连接，如果一个周期（90s)中没有收到心跳，那么server会移除client的注册

### 2.安装服务端

**新建module->添加依赖->写yml->写启动类->启动**

#### 2.1 添加依赖

```xml
<!-- eureka-server -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

#### 2.2 写yml

```yml
server:
  port: 7001

eureka:
  instance:
    hostname: localhost #eureka服务端实例名称
  client:
    # false表示不像注册中心自己注册自己
    register-with-eureka: false
    # false表示自己就是注册中心，主要职责是去维护服务，不需要检索服务
    fetch-registry: false
    # 设置与Eureka Server交互的地址，查询服务和注册服务都要依赖这个地址
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

#### 2.3 主启动类

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7001.class);
    }
}
```

### 3. 引入客户端

**引入依赖->修改yml->增加**

#### 3.1 增加依赖
```xml
<!-- eureka client -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

#### 3.2 修改yml

```yml
eureka:
  client:
      #表示自己是客户端，要注册到eureka注册中心
      register-with-eureka: true
      #从eureka注册中心获取注册表，默认为true，单节点无所谓，集群环境下一定要配置为true来配合ribbon实现负载均衡
      fetchRegistry: true
      service-url:
        defaultZone: http://localhost:7001/eureka
```

#### 3.3 主启动类增加注解@EnableEurekaClient

```java
@SpringBootApplication
@EnableEurekaClient
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class);
    }
}
```

### 4. Eureka集群原理

服务注册调用原理

![image-20200919131059234](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200919131059234.png)

**互相注册，相互守望**

![image-20200919131245845](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200919131245845.png)

### 5. Eureka集群

#### 5.1 新建一个Eureka服务7002，搭建集群

pom和启动类都可以复制（名字得改），yml与单机版的略有不同注册地址变了

```yml
# 7001
eureka:
  instance:
    hostname: eureka7001.com #eureka服务端实例名称
  client:
    # false表示不像注册中心自己注册自己
    register-with-eureka: false
    # false表示自己就是注册中心，主要职责是去维护服务，不需要检索服务
    fetch-registry: false
    # 设置与Eureka Server交互的地址，查询服务和注册服务都要依赖这个地址
    service-url:
#      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
      defaultZone: http://eureka7002.com:7002/eureka/
      
# 7002
eureka:
  instance:
    hostname: eureka7002.com #eureka服务端实例名称
  client:
    # false表示不像注册中心自己注册自己
    register-with-eureka: false
    # false表示自己就是注册中心，主要职责是去维护服务，不需要检索服务
    fetch-registry: false
    # 设置与Eureka Server交互的地址，查询服务和注册服务都要依赖这个地址
    service-url:
#      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
      defaultZone: http://eureka7001.com:7001/eureka/
```

启动7001， 7002，集群就搭建完毕了

#### 5.2 构建支付服务集群

1. 复制出一个8002支付服务，仅修改启动类名称已经yml中的port

```yaml
server:
  port: 8001 # 8002
eureka:
  client:
    #表示自己是客户端，要注册到eureka注册中心
    register-with-eureka: true
    #从eureka注册中心获取注册表，默认为true，单节点无所谓，集群环境下一定要配置为true来配合ribbon实现负载均衡
    fetchRegistry: true
    service-url:
    #注册到集群
      defaultZone: http://eureka7001.com:7001/eureka/, http://eureka7002.com:7002/eureka/
```



2. 启动两个支付服务，自动在服务注册中心注册

![image-20200919150639507](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200919150639507.png)

#### 5.3 修改订单服务，使其通过注册中心获取地址remote call

1. 修改请求地址

```java
// CLOUD-PAYMENT-SERVICE 为服务名称
private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
```

2. 修改restTemplate配置类，为restTemplate加上**@LoadBalance**注解

```java
@Bean
@LoadBalanced // Ribbon 负载均衡,不加报错
public RestTemplate getRestTemplate() {
    return new RestTemplate();
}
```

![image-20200919151116818](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200919151116818.png)



### 6. eureka管理面，服务实例名修改，以及ip提示

```yml
eureka:
  client:
    #表示自己是客户端，要注册到eureka注册中心
    register-with-eureka: true
    #从eureka注册中心获取注册表，默认为true，单节点无所谓，集群环境下一定要配置为true来配合ribbon实现负载均衡
    fetchRegistry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/, http://eureka7002.com:7002/eureka/
  instance:
    instance-id: payment8002 # 改服务实例名
    prefer-ip-address: true  # 改鼠标悬浮在服务名上左下角提示的ip地址
```

### 7.Eureka服务发现

就是拿到服务的信息

### 8. Eureka自我保护机制

简单说，就是有服务和注册中心没心跳了，Eureka不会立马把服务给踢出

这是一种处于高可用考虑的设计，原则就是宁可保存有不健康的服务，也不把可能是健康的服务“杀”掉。

因为，Eureka服务中心可能出现网络故障（拥塞）收心跳有延迟，Eureka收不到服务的心跳，实际上服务是健康的，网络恢复就能直接使用，此时如果把服务踢出显然不合理

#### 8.x 如何禁止Eureka自我保护

```yml
# eureka server
eureka:
  server:
    enable-self-preservation: false # 开启自我保护机制，默认是true
    eviction-interval-timer-in-ms: 2000 # 清除无效节点的周期
    
# eureka client
eureka:
  instance:
    lease-renewal-interval-in-seconds: 1 # eureka发送心跳的时间间隔
    lease-expiraton-duration-in-seconds: 2 # eureka服务端等待心跳的上限
```

