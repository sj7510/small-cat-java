package test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import onem.baymax.server.Request;
import onem.baymax.server.Response;
import onem.baymax.server.Servlet;

/**
 * 测试模拟用户的servlet
 *
 * @author hujiabin wrote in 2024/6/5 19:09
 */
public class HelloServlet implements Servlet {

    @Override
    public void service(Request req, Response res) throws IOException {
        String doc = "<!DOCTYPE html> \n" +
                "<html>\n" +
                "<head><meta charset=\"utf-8\"><title>Test</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + "Hello World 你好" + "</h1>\n";

        res.getOutput().write(doc.getBytes(StandardCharsets.UTF_8));
    }

}
