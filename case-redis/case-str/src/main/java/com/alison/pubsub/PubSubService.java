package com.alison.pubsub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

@Slf4j
public class PubSubService extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("onMessage: channel[%s], message[%s]", channel, message));
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        log.info(String.format("onMessage: pattern[%s], channel%s], message[%s]", pattern, channel, message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        log.info(String.format("onSubscribe: channel[%s], subscribedChannels[%s]", channel, subscribedChannels));
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        log.info(String.format("onUnsubscribe: channel[%s], subscribedChannels[%s]", channel, subscribedChannels));
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        log.info(String.format("onPUnsubscribe: pattern[%s], " + "subscribedChannels[%s]", pattern, subscribedChannels));
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        log.info(String.format("onPSubscribe: pattern[%s], " + "subscribedChannels[%s]", pattern, subscribedChannels));
    }

    public static void main(String[] args) {
        Jedis jedis = null;
        try {
            // happend  Connection time out : connect
//            jedis = new Jedis("192.168.56.103", 6379, 50000);
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(20);
            config.setMaxIdle(10);
            config.setMinIdle(10);
            config.setMaxWaitMillis(5000);
            JedisPool pool = new JedisPool(config, "192.168.56.103", 6379, 5000, "redis");
            jedis = pool.getResource();
            PubSubService pubSubService = new PubSubService();
            // send two message
            jedis.subscribe(pubSubService, "news.share", "news.blog");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.disconnect();
        }
    }

}
