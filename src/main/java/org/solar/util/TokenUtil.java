package org.solar.util;

import org.solar.bean.Token;
import org.solar.coder.RsaCoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
    private static  String rsaPrivateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCbcSi5pCb+6R8BaCyZQ1Y/yk7Gn5iXs4DEA/UVmQOIE0T9TeTTuLZ+sbK5aURN7m6B12tp80xA1u8X9/sxeqwrFvzuzbBmg7VsK33s4ZMD8fVDC/B0rV77lxF/6nFX9GIAqJBffrSU5V6FZGpOL3CrCmUmledGv/fTjBAEMyqjeM63M+VfKEPDJPihOwX6aXADGJ7QdHYVKSoR0c+9HnW6QXDFTPy39msZiU3gWhKpW/isJM7Uv8aJOmKTUpV+SsP0yLP8dyaF91p8lxYR2GTR5sV1/ETYh3bbVNtU8LddIbtZfKYjoK6P4sOFiaXjoasMbuSfR/FZ+G9+qrxEmT67AgMBAAECggEBAJlzH2oMU/bHujUQWx6969IzbIsGGISBzvcylOrKUEMwCqYpFGGycm4mDDKfaa+pVmxVdhN0/GzyAbxesRqKaJZtLiqs4ZyCH0J2uOx1T0XQwXd7vNfiIucpnjvx/AyBE5Jk1YmAMtLej+4NUCh5Cre/HlX2HAerEQNEvLb4r/oJhEFxR35021xK00u0HptLSXH9I0rQQAqI4F+qjeD7ZSEViWCqKnYJxjxd4eEBFJywMlaPAB9GRs1x6NbNxJkcltimG/rL38Jvi7siMaTHA1pemF5VsZIeli6yd9xh/xa1IzrPsYBUBvYtgOjGTbGXdClvqO9OMJQKoNNYPlffbQkCgYEA7DZlcu3U8w8QC2x34FnN4yFjwoGFf6/q47+dm/f0pVI7BCkND/z4cWSrUKAOgLSBNkBiXcFsFZi04ZnO8XqflN8Mp/3TSdAXm+PGiVm1xsReuiv+gwDxh0ylj0JMhpHWTcieWwr9Thv6nTD5bIBNn9Yt9t9uvjzen8KQ1ec1zCcCgYEAqHahkGjNBsallbZ84wz/5QE9oEEqTAADmBiqtJnZll1SrxPid070qu2KnMAknKkq5gm71sFKboQbezfvnHgPc72A7Z0qjZU8eVYlICBEPBkeETqHA8bc+RGSnIBh/l9XZt76XMaO194QmdDFFCRdi5khKECeGIa7zMJgZbip0U0CgYBBJ3vyKJHtfUEitYeXVY4J0WLv+sP1BXQX3/m8VLehhj4LFruoplFtDaSHd66oZ6Ggcj/vtnN2vYlfFQrU5srvyFp4V/YYzRJGCJzxs9IFApNSbOImBnOYq1abXmE3WTCwgd7UJALcsbD9/M0/1WAT74L8LoMgbC/IB2Xl6oGXTwKBgDcpGP/jAPcuBjv+U+g4cnW2sUoO7F2UR5j+wq5+DdwJVLCVDztHtB9fxlpLI/HUBoLqPwIzqj6DrUfsad3PBig8rcGgC7XuXq3QQjnBtvYCt3CmvFzFOceoNFxgQTX8W+8IJexI667NPJxQ2qccvTCfhFbcyad4PnfuUYgdLiWlAoGBANQ5ucvkV8BZ6cltMNNJI/Pt/doZ7nlFcohPGbIgK3pnTyXygxEPC8NDJch2gv1cPtmHRUwmdTYDlWLCOt4jWphmzP649reaAosSiJT2K1RBPI1Yg+hRK7kCJAV1SZG4PJRmuSiJ5+xcP84JX7W5huXItWCieJDQk3E50pfuTiX6";
    private static  String rsaPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm3EouaQm/ukfAWgsmUNWP8pOxp+Yl7OAxAP1FZkDiBNE/U3k07i2frGyuWlETe5ugddrafNMQNbvF/f7MXqsKxb87s2wZoO1bCt97OGTA/H1QwvwdK1e+5cRf+pxV/RiAKiQX360lOVehWRqTi9wqwplJpXnRr/304wQBDMqo3jOtzPlXyhDwyT4oTsF+mlwAxie0HR2FSkqEdHPvR51ukFwxUz8t/ZrGYlN4FoSqVv4rCTO1L/GiTpik1KVfkrD9Miz/HcmhfdafJcWEdhk0ebFdfxE2Id221TbVPC3XSG7WXymI6Cuj+LDhYml46GrDG7kn0fxWfhvfqq8RJk+uwIDAQAB";
    private static RsaCoder rsaCoder = new RsaCoder(rsaPrivateKey,rsaPublicKey);



    public static void setRsaPrivateKeyAndRsaPublicKey(String rsaPrivateKey,String rsaPublicKey) {
        TokenUtil.rsaPrivateKey = rsaPrivateKey;
        TokenUtil.rsaPublicKey = rsaPublicKey;
        rsaCoder = new RsaCoder(rsaPrivateKey,rsaPublicKey);
    }
    public static void setRsaPrivateKey(String rsaPrivateKey) {
        TokenUtil.rsaPrivateKey = rsaPrivateKey;
        rsaCoder = new RsaCoder(rsaPrivateKey,rsaPublicKey);
    }

    public static void setRsaPublicKey(String rsaPublicKey) {
        TokenUtil.rsaPublicKey = rsaPublicKey;
        rsaCoder = new RsaCoder(rsaPrivateKey,rsaPublicKey);
    }


    public static String getTokenId(HttpServletRequest hreq) {
        Token token=getToken(hreq);
        if (token!=null){
            return token.getId();
        }
        return null;
    }
    public static Token getToken(HttpServletRequest hreq) {
        String tokenStr = hreq.getHeader("token");
        if (StringUtil.isEmpty(tokenStr)){
            Cookie[] cookies = hreq.getCookies();
            if (cookies==null){
                return null;
            }
            for (int i = 0; i < cookies.length; i++) {
                if ("token".equalsIgnoreCase(cookies[i].getName())){
                    tokenStr=cookies[i].getValue();
                    break;
                }
            }
        }

        return getToken(tokenStr);
    }
    public static Token getToken(String tokenStr) {
        if (tokenStr==null||"".equals(tokenStr)){
            return null;
        }
        String json = rsaCoder.decode(tokenStr);
        if (json==null||"".equals(json)){
            return null;
        }
        Token token = JsonUtil.parseObject(json,Token.class);
        if (token!=null&&token.getExpireTime()<=0){
            return token;
        }
        long createTime = token.getCreateTime();
        long expireTime = token.getExpireTime();//过期时间秒单位
        long pastTime = (System.currentTimeMillis()/1000) - createTime;
        if (pastTime > expireTime) {
            return null;
        }
        return token;
    }

    public static String toString(Token token) {
        String tokenStr = rsaCoder.encode(JsonUtil.toJSONString(token));
        return tokenStr;
    }
}
