package onem.baymax.server;

import java.io.InputStream;

/**
 * 用于构造请求参数
 *
 * @author hujiabin wrote in 2024/5/29 20:00
 */
public class Request {

    /**
     * 请求输入流
     */
    private final InputStream input;

    /**
     * 请求的uri 从输入流中解析出
     */
    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    /**
     * 解析请求<br>
     * 主要将 I/O 的输入流转换成固定的请求格式（参见前面列出的请求
     * 类）。InputStream 先用 byte 数组接收，执行 input.read(buffer) 后，input 的内容
     * 会转换成 byte 数组，填充 buffer。这个方法的返回值表示写入 buffer 中的总字节数（代码
     * 中的 2048）。随后将 byte 数组的内容通过循环拼接至 StringBuffer 中，转换成我们熟悉的
     * 请求格式
     */
    public void parse() {
        // 解析请求
        StringBuilder request = new StringBuilder(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        uri = parseUri(request.toString());
    }

    /**
     * 解析请求的uri<br>
     * HTTP 协议规定，在请求格式第一行的内容中，包含请求方法、请求路径、使
     * 用的协议以及版本，用一个空格分开。下面代码的功能在于，<b>获取传入参数第一行两个空格中
     * 的一段，作为请求的 URI。</b>如果格式稍微有点出入，这个解析就会失败。从这里也可以看出，
     * 遵守协议的重要性。
     *
     * @param requestString 请求字符串
     * @return uri
     */
    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    /**
     * 获取请求的uri
     *
     * @return uri
     */
    public String getUri() {
        return uri;
    }

}
