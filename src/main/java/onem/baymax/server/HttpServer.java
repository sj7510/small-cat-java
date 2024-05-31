package onem.baymax.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器
 *
 * @author hujiabin wrote in 2024/5/30 11:55
 */
public class HttpServer {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    /**
     * 服务器循环等待请求并处理请求
     */
    private void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        for (; ; ) {
            Socket socket;
            InputStream input;
            OutputStream output;
            try {
                // 接收请求连接
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();
                // create Request object and parse
                Request request = new Request(input);
                request.parse();
                // create Response object
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();
                // close th socket
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
