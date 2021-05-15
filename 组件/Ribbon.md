## Ribbon

### 1. 简介

1. 是什么

   Ribbon是一个能够提供服务间调用负载均衡能力的框架

2. 能干嘛

   提供服务间调用的负载均衡的能力

3. 官网资料

   

4. pom

   ```xml
   <!--ribbon-->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
   </dependency>
   ```

   

### 2. RestTemplate

### getForEntity/postForEntity

用法和getForObject/postForObject差不多，就是对返回的数据进行封装，多了状态码，可以利用状态码来判断

![image-20200921195009128](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200921195009128.png)

### 3. Ribbon常见的负载均衡算法

1. 轮询

   拿到服务信息列表，从头开始，按顺序往后每有一次访问就往后移一位，到尾部了，有重新跳回头部

   感觉可以像快排一样优化一下，每次开始的位置随机，这样每个节点的开始位置就不同，这样每个节点最开始访问的节点也不同。

   考虑一种极限的情况，每一次请求，多个节点同时访问同一个服务，然后同时向后移动一个位置，下次访问又是同时访问同一个接口，这样的情况，性能极差，如果使用起始位置随机的算法，这样就会避免这样的情况发生

2. 随机

   从随机列表中随机去一个服务区访问

### 4. 替换Ribbon原则

1. 在application上层目录建立一个rule文件夹

   ![image-20200922152142862](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200922152142862.png)

   如果rules包放在启动类同级目录的话，componentScan能够扫描到，会导致全局都生效

2. 编写一个配置类，显式将需要用的规则类配置

```java
@Configuration
public class MyRule {

    @Bean
    public IRule randomRule() {
        return new RandomRule();
    }
}
```

3. 在启动类增加@RibbonClient注解

```java
@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name = "cloud-payment-service", configuration = MyRule.class)
public class RibbonOrder {
    public static void main(String[] args) {
        SpringApplication.run(RibbonOrder.class, args);
    }
}
```

### 5. 手写负载均衡算法

1. 自旋锁和CAS

   CAS是JUC提供的AtomicInteger的原子操作，compareAndSet(current, next)， 这个方法会拿内存中的值和current比较，如果相同就把内存中的值设为next，并返回true，否则返回false

   ```java
   // 自旋锁
   private int getAndIncrement() {
       int next;
       int current;
       do {
           current = visitTimes.get();
           next = current + 1;
       } while (!visitTimes.compareAndSet(current, next));
       System.out.printf("*********这是第%d次访问\r\n", next);
       return next;
   }
   ```



2. 实现LodaBalancer接口

   ```java
   @Override
   public ServiceInstance instance(List<ServiceInstance> instances) {
       int nextIndex = getAndIncrement() % instances.size();
       return instances.get(nextIndex);
   }
   ```

3. 接口实现

   ```java
   @GetMapping("/consumer/lb")
   public String lb() {
       List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
       if (CollectionUtils.isEmpty(instances)) {
           return null;
       }
       ServiceInstance instance = myLoadBalancer.instance(instances);
       URI uri = instance.getUri();
       System.out.println(uri);
       return restTemplate.getForObject(uri.toString() + "/payment/lb", String.class);
   }
   ```


**url服务名不区分大小写**