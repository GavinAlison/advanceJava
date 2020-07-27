# LinkedHashMap
LinkedHashMap采用的hash算法和HashMap相同，但是它重新定义了数组中保存的元素Entry，该Entry除了保存当前对象的引用外，
还保存了其上一个元素before和下一个元素after的引用，从而在哈希表的基础上又构成了双向链接列表.
这样就能按照插入的顺序遍历原本无序的HashMap






