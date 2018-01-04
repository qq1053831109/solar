package org.solar.server;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 *JDK 内置的轻量级http server
 */
public class Server {
    //可通过下面的命令来启动该服务
    //java -classpath solar-core-1.1.jar org.solar.server.Server
    public static void main(String[] args) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(9999);
        HttpServer server = HttpServer.create(addr, 0);
        //通过简单的http方式运行远程服务器程序！由于不安全，切记不能在生产环境启动该服务！
        //该方法可以做测试环境的自动部署
        server.createContext("/run", new RunHandler());


        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server is listening on port 9999");
    }
}

