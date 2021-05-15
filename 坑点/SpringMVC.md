## SpringMVC 踩过的坑

### 1. RESTFUL接口一定要用@RestController

如果类的注解使用的是Controller而不是RestController，那么你写的RESTFUL接口就不能用了，请求进来就报错

### 2. POST请求接口，传参为json对象的时候，一定要加@RequestBody

前台传参传对象的话，一定要用@RequestBody注解加上，不加的话，controller方法拿到的参数就是一个“空”的对象（未赋任何值）

### 3. @PathVariable接口的使用方式

```java
    @GetMapping("/payment/{id}")
    public CommonResultDto<Payment> getPaymentById2(@PathVariable("id") String id) {
        // 只有像这样在请求的接口中有{id},才能用@PathVariable, 路由中不加{id}, 加了这个接口，本来有id都会拿不到
        return getPaymentById(id);
    }
```

### 4. OpenFeign访问入参非json接口报415不支持contentType

```java
// openFeign在写接口访问服务接口的时候要注意，get请求使用非json的参数时一定要加上@RequestParam注解（客户端服务端都要加），不然就会报415不支持content type
	@GetMapping(value = "/payment")
    CommonResultDto<Payment> getPaymentById(@RequestParam("id") String id);
```

### 5. 对象传参问题部分属性为null的问题

1. 属性名采用的是小写字母立马接大写字母的方式

   ```java
   public class Example {
       private String tTest;
   }
   ```

   spring无法自动调用其getter和setter，因此要注意起名字的问题

2. 传入json对象的属性个数和类中定义的属性个数不一致

   有些创建对象的接口，因为id是自增的所以不需要前台传入id的值，前台这个时候回忽略id这个属性，此时传递的参数可能存在部分属性为null的情况（不可控， 和springMvc底层的map的hash算法有关）

### 6. 非必须参数

@RequestParam("name", required=false)