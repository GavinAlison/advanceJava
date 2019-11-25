## elasticsearch更改mapping(不停服务重建索引)
>Elasticsearch的mapping一旦创建，只能增加字段，而不能修改已经mapping的字段。

如何在生产环境下修改mapping的类型，保证数据完整性？

使用alias平滑迁移，具体步骤为:
1. 创建一个索引，这个索引的名称最好带上版本号，比如my_index_v1,my_index_v2等
2. 创建一个指向本索引的别名alias
```
curl -XPOST localhost:9200/_aliases -d '
{
    "actions":[
        {
            "add":{
                "alias":"my_index",
                "index":"my_index_v1"
            }
        }
    ]
}'
```
3. 需要创建一个新的索引，比如名称叫my_index_v2（版本升级），在这个索引里面创建你新的mapping结构。
然后，将新的数据刷入新的index里面。在刷数据的过程中，你可能想到直接从老的index中取出数据，然后更改一下格式即可。

4. 修改alias。将指向v1的alias，修改为指向v2
```
curl -XPOST localhost:9200/_aliases -d '
{
    "actions":[
        {
            "remove":{
                "alias":"my_index",
                "index":"my_index_v1"
            }
        },
        {
            "add":{
                "alias":"my_index",
                "index":"my_index_v2"
            }
        }
    ]
}'
```

5. 	删除老的索引
`curl -XDELETE localhost:9200/my_index_v1`



>https://www.iteye.com/blog/donlianli-1924721