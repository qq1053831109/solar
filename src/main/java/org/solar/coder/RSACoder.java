package org.solar.coder;

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
public class RSACoder {
	private static final String KEY_ALGORITHM = "RSA";
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 1024;

     public static final String PLAIN_TEXT = "{\"id\":\"12345678911131517192123252729312\",\"loginTime\":123456789111315,\"rememberMe\":\"y\",\"loginIp\":\"225.225.225.255\"}";
    //172 public static final String PLAIN_TEXT = "g9SsjacZtSE8tigFcKYa9UupMPY3awoHf11hYppk4o5MVrIlXaKGNdeeur/AnJ7dTWc1bNExih2J6rk+g7MdoVQM0VjDBhkAi0c01N6SvWKKn15IPxQkSJyPLcw3doaq3r5KaW60iWPWvEGb+B5YRGU2W6B0fiDDUUFQ9q2LNZ0=";

    public static void main(String[] args) {
        Map<String, byte[]> keyMap = generateKeyBytes();
        byte[] ppk= keyMap.get(PUBLIC_KEY);
        System.out.println("PLAIN_TEXT.length()="+PLAIN_TEXT.length());
        System.out.println("公钥长度="+ppk.length+"|公钥="+Base64.getEncoder().encodeToString(ppk));
        byte[] prk =keyMap.get(PRIVATE_KEY);
        System.out.println("私钥长度="+prk.length+"|私钥="+Base64.getEncoder().encodeToString(prk));
        PublicKey publicKey = restorePublicKey(ppk);
        PrivateKey privateKey = restorePrivateKey(prk);
        
        long nowTime=System.currentTimeMillis();
        //用私钥加密
        byte[] encodedText = RSAEncode(privateKey, PLAIN_TEXT.getBytes());
        String str=Base64.getEncoder().encodeToString(encodedText);
        System.out.println("RSA encoded的长度="+str.length()+"|RSA encoded: " +str );
        System.out.println("加密时间:"+(System.currentTimeMillis()-nowTime)+"ms");
        nowTime=System.currentTimeMillis();
        //用公钥解密
        String end=new String(RSADecode(publicKey, Base64.getDecoder().decode(str)));
        System.out.println("解密时间:"+(System.currentTimeMillis()-nowTime)+"ms");
   

        System.out.println("RSA decoded: "
                + end);
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

    /**
     * 加密，三步走。
     * 
     * @param key
     * @param plainText
     * @return
     */
    public static byte[] RSAEncode(Key key, byte[] plainText) {

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

    /**
     * 解密，三步走。
     * 
     * @param key
     * @param encodedText
     * @return
     */
    public static byte[] RSADecode(Key key, byte[] encodedText) {

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