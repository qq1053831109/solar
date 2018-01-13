package org.solar.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Cache {
    <T> T get(Object key);

    <T> T put(Object key, Object value);

    //expireTime 单位为毫秒
    <T> T put(Object key, Object value, long expireTime);

    CacheImpl cacheImpl = new CacheImpl();
    Map<Object, Cache> cacheImplMap = new ConcurrentHashMap<Object, Cache>();

    static Cache getDefaultCache() {
        return cacheImpl;
    }

    static Cache getCache(Object key) {
        Cache cache = cacheImplMap.get(key);
        if (cache == null) {
            cache = new CacheImpl();
            cacheImplMap.put(key, cache);
        }
        return cache;
    }


}
