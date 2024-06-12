package onem.baymax.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 接收http请求
 *
 * @author hujiabin wrote in 2024/6/11 21:22
 */
public class HttpConnector implements Runnable {

    int minProcessors = 3;

    int maxProcessors = 10;

    int curProcessors = 0;

    private final Deque<HttpProcessor> processors = new ArrayDeque<>();

    @Override

    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // initialize processors pool
        for (int i = 0; i < minProcessors; i++) {
            HttpProcessor initialProcessor = new HttpProcessor(this);
            initialProcessor.start();
            processors.push(initialProcessor);
        }
        curProcessors = minProcessors;

        // 循环接收请求
        for (; ; ) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                HttpProcessor processor = createProcessor();
                if (processor == null) {
                    socket.close();
                    continue;
                }
                processor.assign(socket);

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

    private HttpProcessor createProcessor() {
        synchronized (processors) {
            if (!processors.isEmpty()) {
                return processors.pop();
            }
            if (curProcessors < maxProcessors) {
                return newProcessor();
            }
            return null;
        }

    }

    private HttpProcessor newProcessor() {
        HttpProcessor initialProcessor = new HttpProcessor(this);
        initialProcessor.start();
        processors.push(initialProcessor);
        curProcessors++;
        return processors.pop();
    }

    /**
     * 回收
     *
     * @param httpProcessor httpProcessor
     */
    public void recycle(HttpProcessor httpProcessor) {
        processors.push(httpProcessor);
    }

}
