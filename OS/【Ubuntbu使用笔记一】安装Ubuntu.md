### 【Ubuntbu使用笔记一】安装Ubuntu双系统

请确保在安装Ubuntu之前已经装好了windows

#### 1. 下载镜像文件

官网：https://ubuntu.com/download/desktop

下载稳定版，当前是18.04

#### 2. 下载启动盘制作工具和EasyBcd工具

UltraISO： https://cn.ultraiso.net/xiazai.html

EasyBcd：https://easybcd.en.softonic.com/

#### 3. 用UltraISO制作u盘启动盘

格式化硬盘，点击写入硬盘镜像，格式化U盘

#### 4. U盘启动

1. 按F2进入BIOS，将U盘作为启动项，安F10保存并推出
2. 重启

#### 5. 安装

1. 除了分区配置其他的都挺简单的，跟着自己的直觉选就行
2. 分区设置，不要选默认安装，要选最小安装
3. 分区配置：
   1. /：根目录  ext4文件系统 主分区 	20480MB
   2. /boot：启动目录 ext4文件系统 主分区 2048MB
   3. /home：ext4文件系统 逻辑分区 102400MB
   4. /usr: ext4文件系统 逻辑分区 51200MB
   5. /swap：交换分区 8192MB

4. 切记！：安装引导的分区是/boot分区
5. 剩下来就是下一步下一步一直到安装

#### 6. 多重引导

1. 安装完成后，拔掉U盘，重启计算机
2. 此时不会进入Ubuntu，会默认进入windows
3. 打开Easybcd，设置多重引导