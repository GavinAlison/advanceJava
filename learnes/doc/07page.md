## from,size与scroll的区别

from-size"浅"分页
就是查询前20010条数据，然后截断前10条，返回`20010*4`的数据的前10条数据，这样其实性能很差。

scroll“深”分页

使用scroll可以模拟一个传统数据的游标，记录当前读取的文档信息位置。
因为这个scroll相当于维护了一份当前索引段的快照信息，这个快照信息是你执行这个scroll查询时的快照。
在这个查询后的任何新索引进来的数据，都不会在这个快照中查询到。




