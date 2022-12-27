# 目录

- spring batch的介绍
- spring batch的框架介绍
- spring batch的流程流转
- spring batch的组件说明
- spring batch的demo
- spring batch的特性说明，使用方式
- spring batch 的参数传递
- spring batch多个task的组成



## 1. spring batch 框架工作流程
![spring batch框架](https://docs.spring.io/spring-batch/docs/current/reference/html/images/spring-batch-reference-model.png)



一个Batch(批处理)过程由一个Job(作业)组成。这个实体封装了整个批处理过程。

一个Job(作业)可以由一个或多个Step(步骤)组成。在大多数情况下，一个步骤将读取数据(通过ItemReader)，处理数据(使用ItemProcessor)，然后写入数据(通过ItemWriter)。

JobLauncher处理启动一个Job(作业)。

最后，JobRepository存储关于配置和执行的Job(作业)的元数据。



## 2. spring batch 多线程处理
1.	single process, multi-thread
2.	multi process
3.	parallel steps
4.	remote chunking of step (multi process)
5.	partitioning a Step (single or multi process)


partitioning step 分区执行
单个文件太大，分区执行
多个文件太大，分区执行
DB 数据太多，分区执行









## 链接

1. [spring batch 入门教程](https://zhuanlan.zhihu.com/p/91691608)



