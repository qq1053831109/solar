package org.solar.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheImpl implements Cache{
    private  Map<Object, CacheBean> cacheMap = new ConcurrentHashMap<Object, CacheBean>();
    @Override
    public <T> T get(Object key) {
        CacheBean cacheBean=cacheMap.get(key);
        if (cacheBean==null){
            return null;
        }
        if (cacheBean.getExpireTime()>0){
            long old=System.currentTimeMillis()-cacheBean.getCreateTime();
            if (old>cacheBean.getExpireTime()){
                cacheMap.remove(key);
                return null;
            }

        }
        return (T)cacheBean.getValue();
    }

    @Override
    public <T> T put(Object key, Object value) {
        CacheBean cacheBean=cacheMap.put(key,new CacheBean(value));
        if (cacheBean==null){
            return null;
        }
        return (T)cacheBean.getValue();
    }

    @Override
    public <T> T put(Object key, Object value, long expireTime) {
        if (expireTime<=0){
            return null;
        }
        CacheBean cacheBean=cacheMap.put(key,new CacheBean(value,expireTime));
        if (cacheBean==null){
            return null;
        }
        return (T)cacheBean.getValue();
    }
}
