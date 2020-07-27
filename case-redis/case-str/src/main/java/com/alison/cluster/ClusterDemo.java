package com.alison.cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class ClusterDemo {
    public static void main(String[] args) {
        Set<HostAndPort> jedisCLusterNodes = new HashSet<>();
        jedisCLusterNodes.add(new HostAndPort("192.168.56.103", 7000));
        jedisCLusterNodes.add(new HostAndPort("192.168.56.103", 7001));
        jedisCLusterNodes.add(new HostAndPort("192.168.56.103", 7002));
        jedisCLusterNodes.add(new HostAndPort("192.168.56.103", 7003));
        jedisCLusterNodes.add(new HostAndPort("192.168.56.103", 7004));
        jedisCLusterNodes.add(new HostAndPort("192.168.56.103", 7005));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(10);
        config.setMinIdle(10);

        JedisCluster cluster = new JedisCluster(jedisCLusterNodes, 5000, 5000, 2, null, config);
        cluster.set("101", "101");
        System.out.println(cluster.get("101"));
        System.exit(0);
    }
}
