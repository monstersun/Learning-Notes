# Docker

## 常用命令

### 镜像命令

```shell
#显示docker版本信息
docker version
#
docker info
docker --help

```



```shell
docker images


-a
-q
```



docker search

```shell
--filter

```



docker pull mysql

```shell
docker pull mysql
docker pull mysql:5.7
# 联合文件系统，分层下载
```



docker rmi

```shell
docker rmi -f [container id...] #删除
docker rmi -f $(docker images -aq) # 删除所有镜像

```

### 容器命令

ps：有镜像才可以创建容器

docker run

```shell
docker run [可选参数] image

-name='name' 区分容器
-d # 后台方式运行jar
-i -t # 使用交互方式运行
-p # 制定容器端口 -p 8080:9090
	-p 主机端口: 容器端口
	-p 容器端口
	-p 
-P # 随机制定容器端口
```

 docker ps

查看正在运行容器

```shell
docker ps
-a # 查看所有容器，包括历史运行过的容器
-n=? # 查看最近的容器
-q # 只显示容器id

```

退出容器

```shell
exit # 停止容器并退出
control + P + Q # 不终止容器退出
```

删除容器

```shell
docker rm -f [container id...]    #删除制定容器
docker rm -f $(docker images -aq) # 删除所有容器
docker ps -a -q| xargs docker rm  # 删除所有容器
```

启动停止

```shell
docker start   [容器id]  # 启动容器
docker restart [容器id]  # 重启容器
docker stop [容器id]     # 停止容器
docker kill [容器id]     # 强制停止容器
```

### 常用的其他命令

后台启动容器

```shell
docker run -d centos # 后台启动centos

# 问题docker ps，发现 centos 停止

# 常见的坑： docker后台启动， 就必须要有一个前台应用， docker发现没有前台应用就会自动停止
# 

```

日志

```shell
docker logs [OPTIONS] CONTAINER

Options:
        --details        显示更多的信息
    -f, --follow         跟踪实时日志
        --since string   显示自某个timestamp之后的日志，或相对时间，如42m（即42分钟）
        --tail string    从日志末尾显示多少行日志， 默认是all
    -t, --timestamps     显示时间戳
        --until string   显示自某个timestamp之前的日志，或相对时间，如42m（即42分钟）
```



查看容器的元数据

```shell
docker inspect [容器id]

[root@Tang-6 ~]# docker container inspect -f {{.NetworkSettings.Networks.bridge.IPAddress}} mybox1
172.17.0.2
[root@Tang-6 ~]# docker container inspect -f {{.NetworkSettings.Networks.bridge.Gateway}} mybox1

```



进入容器

```shell
# 在容器中执行一个命令，相当于重新用run命令如果带it参数执行，则会打开一个交互环境
docker exec -it [容器id] 命令

# 会进入容器当前正在运行的命令，如果之前run的时候带了-ti那么会打开一个交互环境
docker attach [容器]

```



不小心让死循环打印让docker没空间了

报错：

Error response from daemon: mkdir /var/lib/docker/overlay2/d55336f2eb3d878af2bedacad64f63631278cdf2961efe68f4e53c1e0b1a3b31-init: no space left on device.

解决

```shell
docker system prune -a
```



从容器拷贝文件到本地文件系统

```shell
docker cp 83282d781f12:/home/test.java /home/

docker cp [容器id]:[文件地址] [本地文件地址]
```

![image-20210512215144532](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210512215144532.png)





## 练习

### nginx

```shell
docker search nginx
docker pull nginx
docker run -d --name nginx01 -p 3344:80 nginx
```

端口暴露的概念



配置文件在容器内修改很麻烦，怎么才能做个映射，外部配置文件修改能用于容器内 -v数据卷技术

### tomcat

```shell
docker run -ti --rm tomcat:9.0
# -rm用完即删， 一般用于测试
docker run -it -p 3345:8080 tomcat:9.0 /bin/bash
```

部署项目每次都要进入容器，能不能在容器外部放一个webApps，在外部放项目，就自动同步到内部



### ES+kibana

问题

```shell
# es 暴露的端口十分多
# 

docker run -d --name elastic -p 9200:9300 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2


# 直接启动内存不够直接报错
[root@iZuf6dmodzug16xc7usoe8Z ~]# docker run -d --name elastic -p 9200:9300 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2
99f0a4c7f52ce7cc93e38642418823b1df0f5ffe6e763e1d575e8e2874d9721d
[root@iZuf6dmodzug16xc7usoe8Z ~]# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@iZuf6dmodzug16xc7usoe8Z ~]# docker logs 99f0a4c7f52ce7cc93e38642418823b1df0f5ffe6e763e1d575e8e2874d9721d
OpenJDK 64-Bit Server VM warning: Option UseConcMarkSweepGC was deprecated in version 9.0 and will likely be removed in a future release.
OpenJDK 64-Bit Server VM warning: INFO: os::commit_memory(0x00000000c5330000, 986513408, 0) failed; error='Not enough space' (errno=12)
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 986513408 bytes for committing reserved memory.
# An error report file with more information is saved as:
# logs/hs_err_pid1.log


# 限制内存
docker run -d --name elastic -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" elasticsearch:7.6.2

# 查询容器状态
docker stats [容器id]

```

![image-20210513070917644](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210513070917644.png)



![image-20210513071245584](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210513071245584.png)





## 可视化

* portainer

  ```shell
  # 安装
  docker run -d -p 8088:9000 --restart=always -v /var/run/docker.sock:/var/run/docker.sock --privileged=true portainer/portainer
  ```

  

* 



## 联合文件系统



## 镜像分层下载



## commit镜像





















