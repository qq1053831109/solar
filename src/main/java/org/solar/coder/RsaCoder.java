package org.solar.coder;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
 
/**
 * 
 * @author xianchuanwu
 *
 */
public class RsaCoder {
	private static final String KEY_ALGORITHM = "RSA";
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final  int KEY_SIZE = 2048;

     public static final String PLAIN_TEXT = "{\"id\":\"12345678911131517192123252729312\",\"loginTime\":123456789111315,\"expireTime\":\"123456789111315\",\"ip\":\"225.225.225.255\"}";
      private PrivateKey privateKey ;
      private PublicKey publicKey  ;

    public RsaCoder(String privateKey , String publicKey) {
        this.privateKey = restorePrivateKey(Base64.getDecoder().decode(privateKey));
        this.publicKey = restorePublicKey(Base64.getDecoder().decode(publicKey));
    }

    public static void main(String[] args) {
        //generateKey();
        RsaCoder rSACoder=new RsaCoder(null,null);
       long nowTime=System.currentTimeMillis();
        //用私钥加密
        String str = rSACoder.encode(  PLAIN_TEXT);
        System.out.println("RSA encoded的长度="+str.length()+"|RSA encoded: " +str );
        System.out.println("加密时间:"+(System.currentTimeMillis()-nowTime)+"ms");
        nowTime=System.currentTimeMillis();
        //用公钥解密
        String end=new String(rSACoder.decode(str));
        System.out.println("解密时间:"+(System.currentTimeMillis()-nowTime)+"ms");


        System.out.println("RSA decoded: "
                + end);
    }
    public static void generateKey() {
        Map<String, byte[]> keyMap = generateKeyBytes();
        byte[] ppk= keyMap.get(PUBLIC_KEY);
        System.out.println("公钥长度="+ppk.length+"|公钥=");
        System.out.println(Base64.getEncoder().encodeToString(ppk));
        byte[] prk =keyMap.get(PRIVATE_KEY);
        System.out.println();
        System.out.println();
        System.out.println("私钥长度="+prk.length+"|私钥=");
        System.out.println(Base64.getEncoder().encodeToString(prk));



    }

    /**MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyO5gIsMpW/m255XmRyQgSJ/3DGF+p9Kl7ymf7181E7XjjeRiAdlNeJ6tBSiQnQaIHPyVI5ekFsEGY0EA4ovxA5XCIEwk5Y+4RmjaVW17lVKGuPgORLc1T2Z7nF/7pqvbp2EpAYjeHoXiBGamh5aFAXPZ0vU45PejgHWIyhNfPYi3mH7NstZ782W3TVLKXBFL7CeFWGrQcCCOJVAeXyouIKX3m2zuqzF3WfS/YXt3S33tqjdEY4qHES16fPjs3mwHvJCU5Wl/+3xr1JTOqNr2yT6KH1rGEyH1T712Q8q0dQOzJi6s4UTWJt1+iopccB8l2iAdrEccD9L0KN3M1toH1wIDAQAB

     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     * 
     * @return
     */
    public static Map<String, byte[]> generateKeyBytes() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     * 
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     * 
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            
            e.printStackTrace();
        }
        return null;
    }
    public String encode(String str){
        try {
            byte[] b=RSAEncode(privateKey,str.getBytes("utf-8"));
            return Base64.getUrlEncoder().encodeToString(b);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 加密，三步走。
     * 
     * @param key
     * @param plainText
     * @return
     */
    public byte[] RSAEncode(Key key, byte[] plainText) {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {

            e.printStackTrace();
        }
        return null;

    }
    public String decode(String str) {
        try {
            return new String(RSADecode(publicKey,Base64.getUrlDecoder().decode(str)),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密，三步走。
     * 
     * @param key
     * @param encodedText
     * @return
     */
    public byte[] RSADecode(Key key, byte[] encodedText) {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encodedText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) { 
            e.printStackTrace();
        }
        return null;

    }
}