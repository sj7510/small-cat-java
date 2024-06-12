package onem.baymax.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 处理客户端发来的请求
 *
 * @author hujiabin wrote in 2024/6/11 21:08
 */
public class HttpProcessor implements Runnable {

    Socket socket;

    boolean available = true;

    HttpConnector connector;

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    public HttpProcessor() {

    }

    /**
     * 处理客户端发来的请求
     *
     * @param socket 客户端socket
     */
    public void process(Socket socket) {
        InputStream input;
        OutputStream output;
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
            // create Request object and parse
            Request request = new Request(input);
            request.parse();
            // create Response object
            Response response = new Response(output);
            response.setRequest(request);

            // servlet
            if (request.getUri().startsWith("/servlet/")) {
                // dynamic resource
                ServletProcessor processor = new ServletProcessor();
                processor.process(request, response);
            } else {
                // static resource
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public void run() {
        for (; ; ) {
            // Wait for the next socket to be assigned
            Socket socket = await();

            if (socket == null) {
                continue;
            }

            // Process the request from this socket
            process(socket);

            // Finish up this request
            connector.recycle(this);

        }

    }

    /**
     * 启动线程
     */
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public synchronized void assign(Socket socket) {
        // Wait for the connector to provide a new socket
        while (available) {
            await();
        }
        this.socket = socket;
        available = true;
        notifyAll();
    }

    private synchronized Socket await() {
        // Wait for the connector to provide a new socket
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Notify the connector that we have received this socket
        Socket socket = this.socket;
        available = false;
        notifyAll();

        return socket;
    }

}
