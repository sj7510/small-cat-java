package onem.baymax.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收http请求
 *
 * @author hujiabin wrote in 2024/6/11 21:22
 */
public class HttpConnector implements Runnable {

    @Override public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // 循环接收请求
        for (; ; ) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                HttpProcessor processor = new HttpProcessor();
                processor.process(socket);

                // close th socket
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动
     */
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

}
