## filter查询
和一般查询比较，filter查询：能够缓存数据在内存中，应该尽可能使用

### filtered查询
```
GET /store/products/_search
{
      "query":{
           "filtered":{
               "query": {
                      "match_all":{}               
               },
               filter:{
                      "terms":{
                            "price":[10,20] 
                      }
               }
          }
      }
}
```
调用没有得到结果？ 因为mapping没有指定not_analyzed
```
GET /store/products/_search
{
      "query":{
           "filtered":{
               "query": {
                      "match_all":{}               
               },
               filter:{
                      "term":{
                            "productID":"QW123"
                      }
               } 
          }
      }
}
```

### bool过滤查询，可以实现组合过滤查询 
```
"bool":{
     "must":[],
     "should":[], 可以满足，也可以不满足
     "must_not":[]
}
```

### 嵌套查询

### and or not查询
```
and  并且，类似于must
or    或者，类似于should
not  不是，类似于must_not
```
### range过滤查询
```
gt:>
lt:<
gte: >=
lte : <=
```
```
GET /store/products/_search
{
  "query":{
	   "filtered":{
	       "filter": {
	          "range":{
	              "price":{
	                    "gte":20,
	                    "lt":50
	              }
	          }                              
	       } 
	  }
  }
}
```

### 过滤空和非空
```
exists、missing
```
### cache缓存


## 组合查询

### bool 查询
```
must： 必须
should:  可以满足，也可以不满足。
must_not: 
minimum_should_match: 至少要x个匹配才算匹配成功
disable_coord: 开启和关闭得分计算
```	

### boosting 
封装两个查询，降低其中一个返回的分值
```
positive:分值不变
negative:降低分值
negative_boost:设置要降低的分值
```

### constant_score 查询
查询结果保持一个恒定的分值
```
GET /library/books/_search
{
 "query":{
    constant_score:{
     "query":{
       "term":{
            "title":"elasticsearch"
       }
     }
    }
 }
} 
```
### indicies 查询
在多个索引上进行查询


