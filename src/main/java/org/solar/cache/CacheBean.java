package org.solar.cache;

public class CacheBean {
    private Object value;
    private long expireTime = 0;
    private long createTime = System.currentTimeMillis();

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public CacheBean() {
    }

    public CacheBean(Object value) {
        this.value = value;
    }

    public CacheBean(Object value, long expireTime) {
        this.value = value;
        this.expireTime = expireTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
