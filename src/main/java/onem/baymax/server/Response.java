package onem.baymax.server;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * 响应返回对象
 *
 * @author hujiabin wrote in 2024/5/30 17:10
 */
public class Response implements ServletResponse {

    private Request request;

    private final OutputStream output;

    private PrintWriter writer;

    private String contentType;

    private final long contentLength = -1;

    private String charset;

    private String characterEncoding;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public OutputStream getOutput() {
        return this.output;
    }

    @Override public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override public String getContentType() {
        return null;
    }

    @Override public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override public PrintWriter getWriter() throws IOException {
        return new PrintWriter(new OutputStreamWriter(output, characterEncoding), true);
    }

    @Override public void setCharacterEncoding(String charset) {
        this.characterEncoding = charset;
    }

    @Override public void setContentLength(int len) {

    }

    @Override public void setContentLengthLong(long len) {

    }

    @Override public void setContentType(String type) {

    }

    @Override public void setBufferSize(int size) {

    }

    @Override public int getBufferSize() {
        return 0;
    }

    @Override public void flushBuffer() throws IOException {

    }

    @Override public void resetBuffer() {

    }

    @Override public boolean isCommitted() {
        return false;
    }

    @Override public void reset() {

    }

    @Override public void setLocale(Locale loc) {

    }

    @Override public Locale getLocale() {
        return null;
    }

}
