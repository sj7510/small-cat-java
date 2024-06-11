package onem.baymax.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 处理客户端发来的请求
 *
 * @author hujiabin wrote in 2024/6/11 21:08
 */
public class HttpProcessor {

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

}
