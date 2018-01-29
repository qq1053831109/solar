package org.solar.bean;

public class Token {
    private String id;
    private String type;
    private String ip;
    public Token( ) {
    }
    public Token(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public Token(String id, String type, String ip) {
        this.id = id;
        this.type = type;
        this.ip = ip;
    }

    public Token(String id, String type, String ip, long expireTime) {
        this.id = id;
        this.type = type;
        this.ip = ip;
        this.expireTime = expireTime;
    }

    /**
     * 秒单位
     */
    private long expireTime=0;
    private long createTime=System.currentTimeMillis()/1000;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
