## 查询

基本查询：内置条件

组合查询：组合基本查询

过滤：查询同时，通过filter筛选数据

### 简单的查询
`GET /library/books/_search?q=title:elasticsearch`
`GET /library/_search?q=title:mongodb`
`GET /_search?q=title:blacksmith`

### term、terms查询：查询包含关键词
功能描述：term查询，查询某个字段例有某个关键词的文档
```
GET /library/books/_search
{
    "query":{
          "term":{
               "preview":"elasticsearch"
          }
    }
} 
```
功能描述：terms查询，查询某个字段例有多个关键词的文档，minimum_match(1,至少有一个关键词存在)
```
GET /library/books/_search
{
    "query":{
          "terms":{
               "preview":["elasticsearch","book"],
               "minimun_match":1
          }
    }
} 
```
### from、size: 控制查询返回的数量
`GET /library/books/_search?q=title:elasticsearch`
```
GET /library/books/_search
{
     "from":1,
     "size":2,
     "query":{
           "term":{
                "title":"elasticsearch"
           }
     }
} 
```
### 返回版本号_version
```
GET /library/books/_search
{
     "version":true,
     "query":{
           "term":{
                 "preview":"elasticsearch"
           }
     }
} 
```
### match查询: 和term查询区别，会使用分析器 
```
GET /library/books/_search
{
     "from":1,
     "size":2,
     "query":{
           "match":{
                "title":"elasticsearch"
           }
     }
} 
```
### match_all 查询指定索引下的所有文档？
```
GET /library/books/_search
{
     "query":{
           "match_all":{}
     }
} 
```
### match_phrase: 
短语查询，slop定义关键词之间间隔多少位置单词
```
GET /library/books/_search
{
     "query":{
           "match_phrase":{
                 "preview":{
                       "query":"elasticsearch,distributed",
                       "slop":2
                 }
           }
     }
} 
```
### multi_match
可以查询指定多个字段都包含某个关键字的文档
```
GET /library/books/_search
{
 "query":{
   "multi_match":{
       "query":"elasticsearch",
       "fields":["title","preview"]
   }
 }
} 
```

### 排序查询: sort、asc/desc
```
GET /library/books/_search
{
    "query":{
        "match_all":{}
    },
    "sort":[
        {
            "price":{
                "order":"desc"
            }
        }
    ]
}
```

### prefix前缀匹配查询
```
GET /library/books/_search
{
    "query":{
        "prefix":{
            "title":{
                "value":"ret"
            }
        }
    }
}
```

### range范围查询
（range，from，to，include_lower,include_upper,boost）
include_lower:包含范围左边界，默认是true
include_upper:包含范围右边界，默认是true
```
GET /library/books/_search
{
     "query":{
          "range":{
               "price":{
                    "from":"10.00",
                    "to":"20.00",
                    "include_lower":true,
                    "include_upper":true
               }
          }
     }
} 
```
### wildcard查询--很影响性能
```
*  代表1-n个字符
? 代表一个字符
```
```
GET /library/books/_search
{
     "query":{
          "wildcard":{
               "preview":“luc?ne”
          }
     }
} 
```
### fuzzy模糊查询--很耗性能
value,boost,min_similarity,prefix_length,max_expansions
```
GET /library/books/_search
{
     "query":{
          "fuzzy":{
               "preview":“rabbit”,
               "min_similarity":0.5
          }
     }
}
```


