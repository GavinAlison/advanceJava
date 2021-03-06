> 这两天在查看nacos， 从官方文档https://nacos.io/en-us/docs/what-is-nacos.html 可以查看，
“Nacos 致力于帮助您发现、配置和管理微服务。Nacos 供了一组简单易用的特性集，帮助您快速实现动态服务发现、
服务配置、服务元数据及流量管理。”
下面是在查看文档中思考的几个问题。

> 发现我想考的问题都在 https://nacos.io/zh-cn/docs/concepts.html 上能找到答案。


## 1.  dataId --配置集 ID 有什么作用？
```
Nacos 中的某个配置集的 ID。配置集 ID 是组织划分配置的维度之一。Data ID 通常用于组织划分系统的配置集。一个系统或者应用可以包含多个配置集，每个配置集都可以被一个有意义的名称标识。Data ID 通常采用类 Java 包（如 com.taobao.tc.refund.log.level）的命名规则保证全局唯一性。此命名规则非强制。
```

上面说了，DataID 是标识配置集的Id， 一个系统或者应用存在多个配置文件(配置集)，每个配置文件都是一个单独的文件。
默认的DataID是spring.name-profile.file extend name

## Data group  配置分组
```
Nacos 中的一组配置集，是组织配置的维度之一。通过一个有意义的字符串（如 Buy 或 Trade ）对配置集进行分组，从而区分 Data ID 相同的配置集。当您在 Nacos 上创建一个配置时，如果未填写配置分组的名称，则配置分组的名称默认采用 DEFAULT_GROUP 。配置分组的常见场景：不同的应用或组件使用了相同的配置类型，如 database_url 配置和 MQ_topic 配置。
```
这里的配置分组是为了区分相同的DataID的情况， 就像上面说的那样，不同应用可以使用相同的数据源配置database_url

## 配置快照
Nacos 的客户端 SDK 会在本地生成配置的快照。当客户端无法连接到 Nacos Server 时，可以使用配置快照显示系统的整体容灾能力。配置快照类似于 Git 中的本地 commit，也类似于缓存，会在适当的时机更新，但是并没有缓存过期（expiration）的概念。

就是一份拷贝的配置文件。

## 服务名
服务提供的标识，通过该标识可以唯一确定其指代的服务。

## 服务注册中心
存储服务实例和服务负载均衡策略的数据库。

## 服务发现
在计算机网络上，（通常使用服务名）对服务下的实例的地址和元数据进行探测，并以预先定义的接口提供给客户端进行查询。

## 元信息
Nacos数据（如配置和服务）描述信息，如服务版本、权重、容灾策略、负载均衡策略、鉴权配置、各种自定义标签 (label)，从作用范围来看，分为服务级别的元信息、集群的元信息及实例的元信息。

## 应用
用于标识服务提供方的服务的属性。

## 服务分组
不同的服务可以归类到同一分组。

## 虚拟集群
同一个服务下的所有服务实例组成一个默认集群, 集群可以被进一步按需求划分，划分的单位可以是虚拟集群。

## 实例
提供一个或多个服务的具有可访问网络地址（IP:Port）的进程。

## 权重
实例级别的配置。权重为浮点数。权重越大，分配给该实例的流量越大。

## 健康检查
以指定方式检查服务下挂载的实例 (Instance) 的健康度，从而确认该实例 (Instance) 是否能提供服务。根据检查结果，实例 (Instance) 会被判断为健康或不健康。对服务发起解析请求时，不健康的实例 (Instance) 不会返回给客户端。

## 健康保护阈值
为了防止因过多实例 (Instance) 不健康导致流量全部流向健康实例 (Instance) ，继而造成流量压力把健康 健康实例 (Instance) 压垮并形成雪崩效应，应将健康保护阈值定义为一个 0 到 1 之间的浮点数。当域名健康实例 (Instance) 占总服务实例 (Instance) 的比例小于该值时，无论实例 (Instance) 是否健康，都会将这个实例 (Instance) 返回给客户端。这样做虽然损失了一部分流量，但是保证了集群的剩余健康实例 (Instance) 能正常工作。

这里需要注意在于保护阈值的定义， 指的是存活的实例占所有实例的比例。
当服务调用其他服务时，会从注册中心找到对应的服务，然后查看注册中心上关于该服务的阈值，与实际的比例相互比较，小于阈值，不进行调用服务，
直接返回，是降级还是熔断，由用户决定。


以下总结上面的知识点：

配置集ID 是一个配置文件，配置集分组有很多配置集ID, 
服务是通过服务名标识，服务可以通过服务名注册到服务注册中心，服务可以发现服务注册中心的服务信息，
这些信息包括服务的地址与元数据，元数据包括一些服务版本、权重、容灾策略、负责均衡策略、鉴权配置、自定义标签(label).
不同的服务可以分配到同一分组，同一服务下有很多实例，
同一服务下多个实例组成一个集群


## 服务发现和服务健康监测
Nacos 支持基于 DNS 和基于 RPC 的服务发现。服务提供者使用 原生SDK、OpenAPI、或一个独立的Agent TODO注册 Service 后，服务消费者可以使用DNS TODO 或HTTP&API查找和发现服务。

Nacos 提供对服务的实时的健康检查，阻止向不健康的主机或服务实例发送请求。Nacos 支持传输层 (PING 或 TCP)和应用层 (如 HTTP、MySQL、用户自定义）的健康检查。 对于复杂的云环境和网络拓扑环境中（如 VPC、边缘网络等）服务的健康检查，Nacos 提供了 agent 上报模式和服务端主动检测2种健康检查模式。Nacos 还提供了统一的健康检查仪表盘，帮助您根据健康状态管理服务的可用性及流量。


这里有个疑问, DNS是如何进行发现的？
查看https://blog.csdn.net/liyanan21/article/details/89176420 文章可以看出， DNS服务器可以发现注册中心的DNS数据格式中IP与域名关系，
服务调用，需要经过DNS服务器发现对应服务的IP, 不直接从注册中心获取



使用ConfigService.addListener进行注册监听事件。

有个问题，配置修改了，如何监听
@NacosConfigListener(dataId = "nacos-springboot")

属性动态变化了，如何实现动态更新 @RefreshScope



