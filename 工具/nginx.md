# nginx

## ECS云服务器安装nginx

前提： 阿里云ECS服务器，Centos8

1. 安装openssl

   ```shell
   yum install openssl
   ```

2. 安装zlib

   ```shell
   yum install zlib
   ```

3. 安装pcre

   ```shell
   yum install pcre
   ```

4. 安装nginx库

   ```shell
   rpm -Uvh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm
   ```

5. 安装nginx

   ```shell
   yum install nginx
   ```

## 启动/停止/重启

```shell
# 设为开机启动
systemctl enable nginx
# 启动nginx服务
systemctl start nginx
# 关闭nginx服务
systemctl stop nginx
# 重启nginx服务
systemctl restart nginx
```

