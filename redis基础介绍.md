## Redis
`redis是开源、高级的键值存储的缓存数据库，Key-Value型，redis支持的2类型非常多，包括String(字符串)，list(列表)，set(集合)，zset(有序集合)，hash(哈希)等，可以取交集，
并集，差集等相关操作。这些操作都是原子性命令，redis是内存型数据库，redis会周期性的将数据保存在磁盘上或者追加到记录文件中，实现主从同步`

### 1. redis特点：
```bash
（1）支持内存存储

（2）支持持久化存储

（3）数据类型更加丰富

（4）支持主从集群，分布式

（5）支持队列等特殊功能
```
### 2.redis和Memecache对比
```bash
1.存储方式
  memecache把数据全部存储在内存中，断电即失，数据不能超过内存大小，redis会把部分数据持久化在磁盘上(RDB，AOF)

2.数据支持类型
  redis在数据支持上比memecache支持更多

3.使用底层模型不同
  redis构建自己VM机制，因为一般系统调用系统函数的话会浪费一定的时间去请求和移动
  
4.运行环境不同
  redis现在只支持linux环境，windows环境不在进行维护
```

### 3.安装redis
```bash
wget https://download.redis.io/releases/redis-6.2.1.tar.gz
tar xzf redis-6.2.1.tar.gz
cd redis-6.2.1
make
过程中可make编译失败，需要在linux终端执行以下命令
最新的redis6.0 需要gcc 5以上，先升级gcc 
sudo yum install centos-release-scl
sudo yum install devtoolset-7-gcc*
scl enable devtoolset-7 bash
make distclean清理以下然后再执行make
make 
```

### 4.redis启动
```bash
//启动服务
redis-server redis.conf 
//开启客户端
redis-cli -p 6380 
//测试连接成功
ping
    
```

### 5.redis配置
```bash
//查看配置
config get config_name
//设置配置属性
config set config_name target
//获取所有属性
config get *
//常用配置说明
    
```

