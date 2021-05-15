# Linux

## 文件目录结构

**在linux中，一切皆文件**

**重要的文件**：

```shell
/bin: 存放常用命令
/sbin: 超级用户用来存放一些命令
/root: root用户的home
/home: 每个用户自己的个人文件夹
/media: u盘DVD等媒体
/mnt: 挂载别的文件系统的目录
/usr/local: 软件安装目录（尤其是源代码编译）
/var: 日志等会不停增加的文件
/etc: 软件的配置文件
```

**其他文件**：

```shell
/proc: 内核文件，平时不要碰
/srv: 内核文件，平时不要碰
/sys: 内核文件，平时不要碰
/tmp: 临时文件
/dev: 设备数字化后的文件存放点
/opt: 安装包
/selinux: security enhanced linux 安全
```

**总结**：

​	1)  linux 的目录中有且只要一个根目录 /

​	2)  linux 的各个目录存放的内容是规划好，不用乱放文件。

​	3)  linux 是以文件的形式管理我们的设备，因此 linux 系统，一切皆为文件。

​	4)  linux 的各个文件目录下存放什么内容，大家必须有一个认识。

​	5)  学习后，你脑海中应该有一颗 linux 目录树

## Vim/Vi

vi：文本编辑器

Vim：增强版Vi，可以用来编辑程序

### 三种模式

1. 正常模式
2. 编辑模式
3. 命令行模式

### 常见指令

```shell
1. 正常模式下 5yy 复制光标所在行下面5行（包括光标所在行）
2. 正常模式下 5dd 删除光标所在行下面5行（包括光标所在行）
3. 命令行模式下 :set nu 设置行号 :set nonu 取消设置行号
4. 正常模式下 G 跳转最后一行 gg 跳转第一行

```



## 开机、关机和用户登录注销

在删除用户时，我们一般不会将家目录删除。

### 用户管理

```shell
useradd [username]
useradd -d [homeDirectory] [username]
userdel [username]
userdel -r [username]
passwd [username] [password]
id [username]
su -[username]

```

