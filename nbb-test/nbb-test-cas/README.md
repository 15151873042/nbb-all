### host配置

```properties
# cas-server地址
127.0.0.1 login.cas.com
# 服务1地址
127.0.0.1 client1.cas.com
# 服务2地址
127.0.0.1 client2.cas.com
```


### 启动cas-sever

```bash
# 在终端中使用build.cmd run运行cas-server
E:\IDE\IdeaProjects\myDemo\cas-demo-all\cas-server>build.cmd run
```

### 启动cas-client

```bash
cas-client项目启动一次即可，通过client1.cas.com:8080和client2.cas.com:8080模拟连个域名
```


### 配置信息
```bash
cas-server项目的application.properties可以项目端口，用户数据库等信息
```

### 地址信息
```bash
# cas server登录页面
login.cas.com:8443/cas/login
# cas server注销接口
login.cas.com:8443/cas/logout
# cas client需要登录才能访问的接口
client1.cas.com:8080/detail
```
### 参考

https://blog.csdn.net/oumuv/article/details/83377945

