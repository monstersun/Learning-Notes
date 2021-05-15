

## Consul

### 1. 下载安装

1. 下载
   1. 进入下载页https://www.consul.io/downloads
   2. 选择windows版本进行下载<img src="C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200921112636418.png" alt="image-20200921112636418" style="zoom:67%;" />
2. 安装
   1. 下载完成后解压到指定文件夹
   2. 然后把文件夹的路径添加到环境变量PATH中去

### 2. 基本指令

```shell
# 以开发模式打开consul
$ consul agent -dev
# 查找consul节点
$ consul members
# graceful关闭consul
$ consul leave
```

### 3. 注册服务到consul

![image-20200921144441203](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200921144441203.png)

1. 增加consul client pom

```xml
<!--SpringCloud consul-server-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

2. application.yml 增加注册中心配置

```yml
spring:
  application:
    name: cloud-consumer-order

  # 云服务客户端配置
  cloud:
    consul:
      host: localhost
      port: 8500
      service-name: ${spring.applicaton.name}
```

3. Application增加@EnableDiscoveryClient

```java
@SpringBootApplication
@EnableDiscoveryClient
public class Order80 {
    public static void main(String[] args) {
        SpringApplication.run(Order80.class, args);
    }
}
```

### 4. CAP架构

![image-20200921145110749](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200921145110749.png)