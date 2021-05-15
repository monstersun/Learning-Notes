# Docker

## 简介

容器虚拟化技术

为了解决开发运维之间环境不一致导致的运维部署问题。

开发人员将开发好的程序带上环境组成镜像，打包给运维人员，运维人员可以直接使用

文档： https://docs.docker.com/

作用： 应用的更快速的交付和部署

## 基本概念

![image-20210331075039290](img/image-20210331075039290.png)

1. 镜像 模板，容器根据这个模板创建
2. 容器 构建一个沙箱，开发人员提供的服务在沙箱内运行，该沙箱根据容器运行
3. 仓库 放镜像的地方

## 安装

```shell
# 卸载旧版本
yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
                  
# 安装yum-util工具（为了使用yum-config)
yum install -y yum-utils

# 配置yum源国内镜像
yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

yum-config-manager \
    --add-repo \
    https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    
# 安装docker
yum install docker-ce docker-ce-cli containerd.io

# 查看docker版本
docker version

```

![image-20210331081521749](img/image-20210331081521749.png)



```shell
# 启动docker
systemctl start docker

# hello-world 验证启动成功否
docker run hello-world
```

![image-20210331081654824](img/image-20210331081654824.png)

### 配置镜像加速器

![image-20210402075657094](img/image-20210402075657094.png)

```shell
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://dfk1ed0b.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### docker run 流程

![image-20210402080045515](img/image-20210402080045515.png)

## 底层原理

![image-20210402080909305](img/image-20210402080909305.png)

## 命令

帮助命令

```shelll
docker version
docker info
docker --help
```



### 镜像命令

```shell
docker images

```



### 容器命令

### 操作命令

## 镜像

## 容器数据卷

## Docker File

## 网络原理

## IDEA 整合docker

## Docker Compose

## Docker Swarm

## CI/CD Jekins



