//package com.alison;
//
//import org.elasticsearch.action.bulk.BulkRequestBuilder;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//
//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
//
///**
// * @Author alison
// * @Date 2019/11/11  9:21
// * @Version 1.0
// * @Description
// */
//public class Appcation {
//
//    public void test(int count) throws Exception {
////        Settings settings = ImmutableSettings.settingsBuilder()
////                .put("client.transport.sniff", true)
////                .put("client.transport.ping_timeout", 100)
////                .put("cluster.name", "my-application").build();
////        TransportClient client = new TransportClient(settings)
////                .addTransportAddress(new InetSocketTransportAddress("192.168.32.228",
////                        9300));
////        BulkRequestBuilder bulkRequest = client.prepareBulk();
////        System.out.println("count ="+count);
////        for(int i=1;i<=count;i++){
////            bulkRequest.add(client.prepareIndex("nq_test2", "java")
////                    .setSource(jsonBuilder()
////                            .startObject()
////                            .field("name", "value"+i)
////                            .endObject()
////                    )
////            );
////        }
////        long beginTime = System.currentTimeMillis();
////        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
////        if (bulkResponse.hasFailures()) {
////            System.out.println("erros");
////        }
////        long endTime = System.currentTimeMillis();
////        System.out.println("took ="+bulkResponse.getTookInMillis());
////        System.out.println("cost = "+(endTime-beginTime)/1000f);
//    }
//}
