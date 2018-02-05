package org.solar.cache;

import org.solar.schedule.ScheduleUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CacheImpl implements Cache{
    private  Map<Object, CacheBean> cacheMap = new ConcurrentHashMap<Object, CacheBean>();
    private long cacheVolume=10000*10;//默认最大保存10W条记录 超过则清空缓存

    public CacheImpl() {
        ScheduleUtil.everydayTask(new Runnable() {
            @Override
            public void run() {
                Set<Map.Entry<Object,CacheBean>> entrySet=cacheMap.entrySet();
                long nowTime=System.currentTimeMillis();
                for (Map.Entry<Object,CacheBean> entry:entrySet) {
                    CacheBean cacheBean=entry.getValue();
                    if (cacheBean.getExpireTime()>0){
                        long old=nowTime-cacheBean.getCreateTime();
                        if (old>cacheBean.getExpireTime()){
                            cacheMap.remove(entry.getKey());
                        }

                    }

                }
                
            }
        },"2:01");
    }

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
        return put(key,value,1000*60*60*24*150);//最大缓存150天
    }

    @Override
    public <T> T put(Object key, Object value, long expireTime) {
        if (expireTime<=0){
            return null;
        }
        if (cacheMap.size()>cacheVolume){//如果缓存数量太大则清空缓存，防止内存一直占用不释放
            cacheMap.clear();
        }
        CacheBean cacheBean=cacheMap.put(key,new CacheBean(value,expireTime));
        if (cacheBean==null){
            return null;
        }
        return (T)cacheBean.getValue();
    }
}
