package com.alison.spring.cache;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ExpiringLRUCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(ExpiringLRUCache.class);

    private final Map<Object, Object> store;

    public ExpiringLRUCache(int maxSize, int liveTimeSeconds, int intervalSeconds) {
        if (logger.isDebugEnabled()) {
            logger.debug("expiring lru cache init");
        }
        ExpiringMap<Object, Object> expiringMap = new ExpiringMap<>(maxSize, liveTimeSeconds, intervalSeconds);
        expiringMap.getExpireThread().startExpiryIfNotStarted();
        this.store = expiringMap;
    }

    @Override
    public void put(Object key, Object value) {
        if (logger.isDebugEnabled()) {
            logger.debug("put cache key: {} value: {}", key, value);
        }
        store.put(key, value);
    }

    @Override
    public void put(Object key, Object value, int expire) {
        this.put(key, value);
    }

    @Override
    public Object get(Object key) {
        if (logger.isDebugEnabled()) {
            logger.debug("get cache key: {}", key);
        }
        return store.get(key);
    }

    @Override
    public List<DataWrapper> get(Object[] keys) {
        List<DataWrapper> result = Lists.newArrayList();
        for (Object key : keys) {
            Object singleResult = this.get(key);
            if (singleResult != null) {
                DataWrapper dataWrapper = new DataWrapper(key, singleResult);
                result.add(dataWrapper);
            }
        }
        return result;
    }

    @Override
    public void delete(Object key) {
        if (logger.isDebugEnabled()) {
            logger.debug("remove cache key: {}", key);
        }
        store.remove(key);
    }

    @Override
    public void put(List list) {
        for (Object object : list) {
            DataWrapper dataWrapper = (DataWrapper) object;
            this.put(dataWrapper.getKey(), dataWrapper.getValue());
        }
    }

//    @Override
//    public void put(List<DataWrapper> dataWrappers) {
//        for(DataWrapper dataWrapper: dataWrappers ) {
//            this.store.put(dataWrapper.getKey(),dataWrapper.getValue());
//        }
//
//    }

}
