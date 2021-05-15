# Spring Boot

## 快速体验

1. maven镜像配置

   ```xml
   <mirrors>
       <mirror>
           <id>nexus-aliyun</id>
           <mirrorOf>central</mirrorOf>
           <name>Nexus aliyun</name>
           <url>http://maven.aliyun.com/nexus/content/groups/public</url>
       </mirror>
   </mirrors>
   
   <profiles>
       <profile>
           <id>jdk-1.8</id>
           <activation>
               <activeByDefault>true</activeByDefault>
               <jdk>1.8</jdk>
           </activation>
           <properties>
               <maven.compiler.source>1.8</maven.compiler.source>
               <maven.compiler.target>1.8</maven.compiler.target>
               <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
           </properties>
       </profile>
   </profiles>
   ```

2. 引入依赖

   ```xml
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>2.3.4.RELEASE</version>
   </parent>
   
   
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
   
   </dependencies>
   ```

3. 创建主程序

   ```java
   @SpringBootApplication
   public class Application {
       public static void main(String[] args) {
           SpringApplication.run(Application.class);
       }
   }
   ```
   
4. 创建controller

5. 验证

## 注解

@Import： 给容器中自动创建出这两个类型的组件、默认组件的名字就是全类名

@Conditional: 当满足Conditional条件的时候装配









