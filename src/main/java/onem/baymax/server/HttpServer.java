package onem.baymax.server;

import java.io.File;

/**
 * 服务器
 *
 * @author hujiabin wrote in 2024/5/30 11:55
 */
public class HttpServer {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }

}
