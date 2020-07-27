package jrx.basic.component.buried.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http client初始化
 *
 * @author yxy
 * @date 2018/12/14
 */
public class HttpClientManagerBuilder {

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_ENCODING = "UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(HttpClientManagerBuilder.class);

    private static CloseableHttpClient httpClient = HttpClients.custom()
      .setConnectionManager(httpClientConnectionManager(300))
      .setDefaultRequestConfig(requestConfig(30000))
      .build();

    public static void ResetRequestConfig(int maxTimeOut){
        httpClient = HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager(300))
                .setDefaultRequestConfig(requestConfig(maxTimeOut))
                .build();
    }

    public static PoolingHttpClientConnectionManager httpClientConnectionManager(int maxTotal) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxTotal);
        return cm;
    }

    public static RequestConfig requestConfig(Integer maxTimeOut) {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时时间，单位毫秒。
        configBuilder.setConnectTimeout(maxTimeOut);
        // 请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        configBuilder.setSocketTimeout(maxTimeOut);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(maxTimeOut);
        return configBuilder.build();
    }


    /**
     * Get请求
     *
     * @param url    请求地址
     * @param params 参数数据
     * @return
     */
    public static String doGet(String url, Map<String, Object> params, Map<String, String> headers) {
        StringBuffer param = new StringBuffer();

        if (params != null && params.size() > 0) {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=");
                toStringParams(param,params.get(key));
                i++;
            }
            url += param;
        }

        String result = null;
        HttpEntity entity = null;

        try {
            HttpGet httpGet = new HttpGet(url);
            if (!CollectionUtils.isEmpty(headers)) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }

            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            logger.debug("执行状态码 : {}", statusCode);

            entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, CONTENT_ENCODING);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    private static void toStringParams(StringBuffer buffer, Object v){
        //字符串需要处理
        if(v instanceof String){
            if(((String)v).contains("\"")){
                try {
                    buffer.append(URLEncoder.encode(v.toString(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else {
                buffer.append(v.toString());
            }
        }else {
            buffer.append(v.toString());
        }
    }

    /**
     * Post请求
     *
     * @param url    请求地址
     * @param params 参数数据
     * @return
     */
    public static String doPost( String url, Map<String, Object> params) {
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);

        HttpEntity httpEntity = null;

        try {
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(CONTENT_ENCODING)));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            httpEntity = response.getEntity();
            httpStr = EntityUtils.toString(httpEntity, CONTENT_ENCODING);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (httpEntity != null) {
                try {
                    EntityUtils.consume(httpEntity);
                } catch (IOException e) {
                }
            }
        }
        return httpStr;
    }

    /**
     * Post 请求
     *
     * @param url  请求地址
     * @param json 参数数据
     * @return
     */
    public static String doPost(String url, String json) {
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);


        HttpEntity httpEntity = null;

        try {
            //解决中文乱码问题
            StringEntity stringEntity = new StringEntity(json, CONTENT_ENCODING);
            stringEntity.setContentEncoding(CONTENT_ENCODING);
            stringEntity.setContentType(APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            httpEntity = response.getEntity();
            httpStr = EntityUtils.toString(httpEntity, CONTENT_ENCODING);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (httpEntity != null) {
                try {
                    EntityUtils.consume(httpEntity);
                } catch (IOException e) {
                }
            }
        }
        return httpStr;
    }

}
