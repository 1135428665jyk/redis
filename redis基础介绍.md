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
启动服务
redis-server redis.conf 
开启客户端
redis-cli -p 6380 
测试连接成功
ping
    
```

### 5.redis配置
```bash
查看配置
config get config_name
设置配置属性
config set config_name target
覆盖配置文件中的配置
redis-server 6389.conf --loglevel warning 
获取所有属性
config get *
常用配置说明 
#Redis默认不是已守护进程方式运行，可以通过配置修改，修改为yes,已守护进程修改
daemonize no 

pidfile /var/run/redis.pid
#Redis已守护进程的方式运行时，Redis默认会把pid写入/var/run/redis.pid文件，

port 6379
#指定redis端口为6379

bind 127.0.0.1
#绑定ip地址

timeout 300
#当客户端闲置300s关闭，0的话表示不关闭
    
loglevel notice
#指定日志级别 debug,verbose,notice,warning默认notice

logfile stdout
#日志记录方式，默认标准输出，

databases 16
#设置数据库的数量，默认数据库为0，可以使用SELECT 命令连接指定的数据id

save <seconds> <changes>
#指定多长时间，多少次操作将数据同步到数据文件中
save 900 1    900s更新1次
save 300 10   300s更新10次
save 60 10000 60s更新10000次

rdbcompression yes 
#指定存储数据是否压缩，默认为yes，Redis采用LZF压缩，为了节省CPU可以关闭，但是会造成数据过大

dbfilename dump.name 
#指定本地数据名称

dir ./
#指定本地文件存放的目录

slaveof <masterip> <masterport>
#当本机为从节点时，设置主节点的IP地址及端口，在Redis启动slave会从master同步

masterauth <master-password>
#当master设置了密码服务时，slave服务连接master的密码

requirepass foobared
#设置redis连接密码，如果配置了连接密码，客户端在连接Redis时，需要通过AUTH <password>命令提供密码

maxclients 128
#设置同一时间最大的客户端连接数，默认无限制如果maxclient 0表示不限制，当客户端连接数达到限制，
# Redis会关闭新的连接并向客户端返回max number of clients reached 信息

maxmemory <bytest>
#redis最大内存限制，Redis达到最大内存后Redis会尝试清除已经过期的或即将到期的key,此方法处理后仍然达到最大内存设置，
# 将无法再进行写入操作，但是仍然可以进行读操作，Redis新的vm机制，会把Key放到内存，Value会放在swap区

appendonly no
#指定是否在每次更新操作后进行日志操作，Redis在默认情况下是异步将数据存入磁盘，如果不开启，
# 可能在断电时导致导致一段时间内的数据丢失，redis本身同步是按照上面的slave条件来同步的所以数据一段时间内只存储在内存中
#默认为no

appendfilename appendonly.aof 
#指定更新日志文件名

appendfsync everysec 
#更新日志文件，共有三个值可以选择 no 等操作系统数据缓存同步 always：每一次更新后手动调用fsync()将数据写到磁盘
# everysec 表示每一秒同步一次

vm-enable no
#指定是否启用虚拟内存机制，默认为no,VM机制将数据分页存放，

vm-swap-file /tmp/redis.swap 
#虚拟文件路径不可多个redis共享实例

vm-max-memory 0
#将所有大于vm-max-memory的数据存入虚拟内存，无论vm-max-memory 设置多小，所有索引存储都是内存存储的当vm-max-memory为0时，
# 其实所有的value 都存储在磁盘上

vm-page-size 32
#swap文件分成了多个page,一个对象可以保存在多个page上，，但是一个page不能被多个对象共享，

vm-paages 123213123
#设置swap文件中的page数量，由于页表是放在内存中，磁盘上每8个pages将消耗1byte的内存

vm-max-threads 4
#设置访问swap的线程数

```
### 5.redis数据类型
String ,List ,Hash ,Set ,Zset五中数据类型
```bash
列表 集最多可以存储2^32-1个数据约40亿，String key最大为512M,String是二进制安全的，可以存储任何数据
```
| 类型 | 右对齐标题 | 场景 |
| :------| ------: | :------: |
| String | 二进制安全 | 分布式锁 |
| Hash | 键值对集合 | 存储，读取，修改用户属性 |
| List | 双向链表 | 消息队列，最新消息排行 |
| Set | hash实现，不重复 | 共同好友，访问某一网站的独立IP,共同好友推荐 |
| Zset | 有序集合 | 排行榜，带权重的消息队列 |

### 6.Redis key命令

```bash
del key
key存在时删除key

dunp key
序列化给定key,并返回序列化的值

exists key
检查给定的key是否存在

expire key seconds
给key设置过期时间

exporeat key timestamp
设置过期时间，expireat 命令参数是UNIX的时间戳

pexpire key millseconds
设置key的过期时间以毫秒记

pexpireat key millseconds-timestamp
设置key的过期时间时间戳以毫秒记

keys pattern 
查找所有给定模式的key

move key db
将key移动到某一个db中

persist key 
移除key的过期时间，key将永远保持

pttl key 
已毫秒给单位返回key剩余的过期时间

ttl key 
已秒为单位返回key剩余的过期时间

randomkey key 
从当前数据库中随机返回key

rename key newkey
将key的名字设置为newkey

renamenx key newkey
当newkey不存在时将key名字修改为newkey

type key
返回键的类型


```
### 7.Redis String类型

```bash
Set key value 
设定key的值

Get key
获取key的值

GetRange key start end
返回key中字符串值的子字符

GetSet key value
将指定的key设置为value，并返回key的旧值

GetBit key offset 
对key存储的字符串，获取指定偏移量上的位

SetBit key offset
设置或指定key偏移量上的位


```

