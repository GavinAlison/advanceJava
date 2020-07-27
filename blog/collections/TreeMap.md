# TreeMap
## LinkedHashMap与TreeMap的区别
1. LinkedHashMap是按照插入顺序排序，而TreeMap是按照Key的自然顺序或者Comprator的顺序进行排序
2. 在实现原理上LinkedHashMap是双向链表，TreeMap是红黑树

TreeMap的基本操作 containsKey、get、put 和 remove 的时间复杂度是 O(log n)，HashMap的时间复杂度是O(1)，可见没有排序需求的情况下HashMap的性能更好。
