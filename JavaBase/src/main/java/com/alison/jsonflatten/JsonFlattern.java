package com.alison.jsonflatten;

import com.alibaba.fastjson.JSONObject;
import com.github.wnameless.json.flattener.JsonFlattener;

import java.util.Map;

/**
 * @Author alison
 * @Date 2020/4/16
 * @Version 1.0
 * @Description
 */
public class JsonFlattern {

    public static void main(String[] args) {

        test();
    }

    /*
    <dependency>
            <groupId>com.github.wnameless.json</groupId>
            <artifactId>json-flattener</artifactId>
            <version>0.8.0</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.5</version>
        </dependency>
     */
    public static void test() {
        String jsonStr = "{\"type\":10,\"data\":[{\"text\":\"献给爱我们的女神\",\"is_liked\":false,\"index_cover\":\"http://photos.breadtrip.com/photo_d_2016_06_19_01_21_20_989_123986672_17737936936133063098.jpg?imageView/2/w/960/q/85\",\"poi\":\"\",\"cover_image_height\":816,\"trip_id\":2387282916,\"index_title\":\"\",\"center_point\":{},\"view_count\":36207,\"location_alias\":\"\",\"cover_image_1600\":\"http://photos.breadtrip.com/photo_d_2016_06_19_01_21_20_926_123986672_17737936923172662193.jpg?imageView/2/w/1384/h/1384/q/85\",\"cover_image_s\":\"http://photos.breadtrip.com/photo_d_2016_06_19_01_21_20_926_123986672_17737936923172662193.jpg?imageView/1/w/280/h/280/q/75\",\"share_url\":\"btrip/spot/2387842143/\",\"timezone\":\"Asia/Shanghai\",\"date_tour\":\"2016-06-19T01:19:07+08:00\",\"is_hiding_location\":false,\"user\":{\"location_name\":\"\",\"name\":\"丑到没墙角\",\"resident_city_id\":\"\",\"mobile\":\"\",\"gender\":2,\"avatar_m\":\"http://photos.breadtrip.com/avatar_41_b8_aedfd71640e3ec09d0c30edc47df04dc56dbf38a.jpg-avatar.m\",\"cover\":\"http://photos.breadtrip.com/default_user_cover_10.jpg-usercover.display\",\"custom_url\":\"\",\"experience\":{\"value\":59,\"level_info\":{\"name\":\"\",\"value\":1}},\"id\":2384288641,\"birthday\":\"\",\"country_num\":null,\"avatar_s\":\"http://photos.breadtrip.com/avatar_41_b8_aedfd71640e3ec09d0c30edc47df04dc56dbf38a.jpg-avatar.s\",\"country_code\":null,\"email_verified\":false,\"is_hunter\":false,\"cdc2\":false,\"avatar_l\":\"http://photos.breadtrip.com/avatar_41_b8_aedfd71640e3ec09d0c30edc47df04dc56dbf38a.jpg-avatar.l\",\"email\":\"\",\"user_desc\":\"\",\"points\":2},\"spot_id\":2387842143,\"is_author\":false,\"cover_image_w640\":\"http://photos.breadtrip.com/photo_d_2016_06_19_01_21_20_926_123986672_17737936923172662193.jpg?imageView/1/w/640/h/480/q/85\",\"region\":{\"primary\":\"\",\"secondary\":\"\"},\"comments_count\":2,\"cover_image\":\"http://photos.breadtrip.com/photo_d_2016_06_19_01_21_20_926_123986672_17737936923172662193.jpg?imageView/2/w/960/q/85\",\"cover_image_width\":1088,\"recommendations_count\":21}],\"desc\":\"\"}";
        JSONObject jsonObj = JSONObject.parseObject(jsonStr);
        Map<String, Object> flatMap = JsonFlattener.flattenAsMap(jsonObj.toString());
        for (Map.Entry<String, Object> entry : flatMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
