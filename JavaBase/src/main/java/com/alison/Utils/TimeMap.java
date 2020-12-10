package com.alison.Utils;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author peidong.meng
 * @date 2018/11/22
 */
public class TimeMap<K,V> extends HashMap<K,V> {

    private static final long serialVersionUID = 1L;

    /**
     *  默认期限 1小时
     */
    private long TIMEOUT = 1000 * 60 * 60;

    private HashMap<K, Long> timeMap = new HashMap<>();

    public TimeMap(){

        super();

        /**
         * 每timeout时间定时清理一次过期数据
         */
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("clear-map-schedule-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                clearTimeout();
            }
        },TIMEOUT,TIMEOUT, TimeUnit.MILLISECONDS);
    }

    @Override
    public V put (K key,V value){
        timeMap.put(key, System.currentTimeMillis() + TIMEOUT);
        return super.put(key,value);
    }

    /**
     * 设置超时时间
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public V put(K key, V value, long timeout) {
        timeMap.put(key, System.currentTimeMillis() + timeout);
        return super.put(key, value);
    }

    @Override
    public V remove(Object key){

        timeMap.remove(key);
        return super.remove(key);
    }

    /**
     * 清除过期key
     * @return
     */
    public void clearTimeout(){

        if(timeMap.size() > 0){
            for(Entry<K,Long> k : timeMap.entrySet()){
                if(System.currentTimeMillis() > k.getValue()){
                    timeMap.remove(k.getKey());
                    super.remove(k.getKey());
                }
            }
        }
    }

}
