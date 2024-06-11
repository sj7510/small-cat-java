package onem.baymax.server;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 用于构造请求参数
 *
 * @author hujiabin wrote in 2024/5/29 20:00
 */
public class Request implements ServletRequest {

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

    @Override public Object getAttribute(String s) {
        return null;
    }

    @Override public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override public String getCharacterEncoding() {
        return null;
    }

    @Override public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override public int getContentLength() {
        return 0;
    }

    @Override public long getContentLengthLong() {
        return 0;
    }

    @Override public String getContentType() {
        return null;
    }

    @Override public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override public String getParameter(String s) {
        return null;
    }

    @Override public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override public String getProtocol() {
        return null;
    }

    @Override public String getScheme() {
        return null;
    }

    @Override public String getServerName() {
        return null;
    }

    @Override public int getServerPort() {
        return 0;
    }

    @Override public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override public String getRemoteAddr() {
        return null;
    }

    @Override public String getRemoteHost() {
        return null;
    }

    @Override public void setAttribute(String s, Object o) {

    }

    @Override public void removeAttribute(String s) {

    }

    @Override public Locale getLocale() {
        return null;
    }

    @Override public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override public boolean isSecure() {
        return false;
    }

    @Override public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override public String getRealPath(String s) {
        return null;
    }

    @Override public int getRemotePort() {
        return 0;
    }

    @Override public String getLocalName() {
        return null;
    }

    @Override public String getLocalAddr() {
        return null;
    }

    @Override public int getLocalPort() {
        return 0;
    }

    @Override public ServletContext getServletContext() {
        return null;
    }

    @Override public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override public boolean isAsyncStarted() {
        return false;
    }

    @Override public boolean isAsyncSupported() {
        return false;
    }

    @Override public AsyncContext getAsyncContext() {
        return null;
    }

    @Override public DispatcherType getDispatcherType() {
        return null;
    }

}
