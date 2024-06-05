package onem.baymax.server;

import java.io.IOException;

/**
 * servlet接口 <p>
 * 按照 Servlet 的规范应该实现 javax.servlet.Servlet
 *
 * @author hujiabin wrote in 2024/6/4 12:19
 */
public interface Servlet {

    /**
     * 处理请求
     *
     * @param req 请求
     * @param res 响应
     * @throws IOException IO异常
     */
    void service(Request req, Response res) throws IOException;

}
