## menu
-   CRUD
-   query
    -   term
    -   dateage
    -   queryString
    -   wildcard
    -   prefix
    -   regex
    -   should/must/mustnot
-   filter
-   aggregation
-   search
-   poi


## todo
-   实现文档的搜索，基本需求，给一个文档，可对其中的文档内容搜索定位
-   解决es分词问题， 能搜索出“大夏”，“大”，“夏”， 搜索出正常的数据
-   实现滚动查询
-   实现total总计计数，不在限制10000个

-   {indices}/_search  PaginationVO<Map<String, Object>>
-   {indices}/_filter  DSL  ESPaginationVO<Map<String, Object>>
-   {indices}/_filterAll   List<Map<String, Object>>
-   {indices}/_aggr   Map<String, String>
-   {index}/{id}   Map<String, String>
-   _scroll        ESPaginationVO<Map<String, Object>>
-   poi/nearPoint        List<PoiVO>
-   poi/searchAddress        List<PoiVO>
-   manager/esDate/{collectTagId}       String
-   manager/job/{jobKey}/_status        JobStatusEnum