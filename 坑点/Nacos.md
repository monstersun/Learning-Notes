# Nacos坑点

## 启动报错

1. load jdbc.properties error

   issue: https://github.com/alibaba/nacos/issues/3529

   nacos在启动的时候回去检查数据库配置如果没有就会报错

   解决方案：

   ```shell
   startup.cmd -m standalone
   ```

   



