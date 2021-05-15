## RestTemplate 坑点

### 1. Get请求参数问题

getForObject方法，调用get请求，想要在请求中带参数

1. 仅仅把参数封装进Map然后传到getForObject中去是不够的，使用这种方法穿参，请求Url中必须是模板那种类型的，例如

```java
    private static final String URL = "http://localhost:8001/payment?id={id}";
    @GetMapping("/consumer/payment/get")
    public CommonResultDto<Payment> getPayment2(String id) {
        // CommonResultDto是约定好的前后台dto	
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return restTemplate.getForObject(URL, CommonResultDto.class, params);
    }
```



2. 还有一种是直接把参数拼接进URL中去

```java
 @GetMapping("/consumer/payment")
    public CommonResultDto<Payment> getPayment(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PAYMENT_URL + "/payment");
        params.forEach(builder::queryParam);
        String url = builder.build().encode(StandardCharsets.UTF_8).toString();
        return restTemplate.getForObject(url, CommonResultDto.class, params);
    }
```

### 2. post请求

1. 通常情况下，我们会将一个对象作为参数带到请求当中去，如果我们的服务端程序中参数前没有加@RequestBody注解，会导致传进去的对象是一个新的刚new过的对象，而不是我们传的对象

