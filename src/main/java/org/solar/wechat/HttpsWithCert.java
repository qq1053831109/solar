package org.solar.wechat;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class HttpsWithCert {
//    // 授信证书库
//    String trustStore = "D:\\workspaces\\test\\https-native\\src\\cacerts.jks";
//    String trustStorePass = "changeit";

    // 私钥证书
    private SSLSocketFactory ssf;

    public HttpsWithCert(String keyStore, String keyStorePass) {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            KeyManager[] kms = null;
            if (keyStore != null) {
                kms = getKeyManagers(keyStore, keyStorePass);
            }
            sslContext.init(kms, null, new java.security.SecureRandom());
            ssf = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        String str = new HttpsWithCert(null, null).post("https://www.jd.com ", "");
        System.out.println(str);
    }

    /**
     * post 请求，带双证书验证
     */
    public String post(String urlStr, String params) {


        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            // 服务链接
            URL url = new URL(urlStr);
            // 请求参数

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0");

            // content-type 按具体需要进行设置
//            conn.setRequestProperty("content-type", "application/json");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 加载信任证书库
     *
     * @param trustStore
     * @param trustStorePass
     * @return
     * @throws IOException
     */
    private static TrustManager[] getTrustManagers(String trustStore,
                                                   String trustStorePass) throws IOException {
        try {
            String alg = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory factory = TrustManagerFactory.getInstance(alg);
            InputStream fp = new FileInputStream(trustStore);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(fp, trustStorePass.toCharArray());
            fp.close();
            factory.init(ks);
            TrustManager[] tms = factory.getTrustManagers();
            return tms;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载私钥证书
     *
     * @param keyStore
     * @param keyStorePass
     * @return
     * @throws IOException
     */
    private static KeyManager[] getKeyManagers(String keyStore,
                                               String keyStorePass) throws IOException {
        try {
            String alg = KeyManagerFactory.getDefaultAlgorithm();
            KeyManagerFactory factory = KeyManagerFactory.getInstance(alg);
            InputStream fp = new FileInputStream(keyStore);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fp, keyStorePass.toCharArray());
            fp.close();
            factory.init(ks, keyStorePass.toCharArray());
            KeyManager[] keyms = factory.getKeyManagers();
            return keyms;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
