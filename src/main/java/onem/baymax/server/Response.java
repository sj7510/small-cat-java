package onem.baymax.server;

import java.io.OutputStream;

/**
 * 响应返回对象
 *
 * @author hujiabin wrote in 2024/5/30 17:10
 */
public class Response {

    private Request request;

    private final OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public OutputStream getOutput() {
        return this.output;
    }

}
