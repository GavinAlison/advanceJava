package com.alison;

import com.github.wnameless.json.flattener.JsonFlattener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author alison
 * @Date 2020/4/19
 * @Version 1.0
 * @Description
 */
@Slf4j
public class BulkInsertApp {
    // 测试插入数据，解决问题有
//    1. 连接超时connectTimeout
//    2. 密码设置
//    3. json的扁平化处理
//    4. 设置index.mapping.total_fields.limit的数量， 默认是1000, 根据json来设置默认的数字，取1000的整数

    @Test
    public void testInsert() throws Exception {
        String path = "C:\\Users\\hy\\Desktop\\ev56000.json";
        // 解决 """ 特定字符 在扁平化 出错 问题
        String json = FileUtils.readFileToString(new File(path), Charset.forName("UTF-8")).replaceAll("\"\"\"", "");
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "NgzufV5HJ9qwsGBSIdwE"));
        HttpHost httpHost44 = new HttpHost("172.16.101.44", 9200, "http");
        HttpHost httpHost22 = new HttpHost("172.16.101.22", 9200, "http");
        HttpHost httpHost12 = new HttpHost("172.16.101.12", 9200, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost44, httpHost12, httpHost22);
        restClientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(30000);
                requestConfigBuilder.setSocketTimeout(30000);
                requestConfigBuilder.setConnectionRequestTimeout(60000);
                return requestConfigBuilder;
            }
        });
        restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });

        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);

        String _index = "db_acfound";
        BulkRequest request = new BulkRequest();
        /////// 创建指定的index
        try {
            int max = JsonFlattener.flattenAsMap(json).size();
            if (max % 1000 != 0) {
                max = (max / 1000 + 1) * 1000;
            }
            log.info("max: {}", max);
            boolean existIndex = client.indices().exists(new GetIndexRequest(_index), RequestOptions.DEFAULT);
            if (!existIndex) {
                String mapping = "{\"index.mapping.total_fields.limit\":  " + max + "}";
                CreateIndexRequest createIndexRequest = new CreateIndexRequest(_index)
                        .source(mapping, XContentType.JSON);
                createIndexRequest.setTimeout(TimeValue.timeValueSeconds(30));
                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.add(new IndexRequest(_index, "doc")
                .source(json, XContentType.JSON));
        request.timeout(TimeValue.timeValueSeconds(30));
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bulkResponse.hasFailures()) {
            log.error("batch save to ES has error:{}" + bulkResponse.buildFailureMessage());
        }

        log.error("--------------- successful");


    }
}
