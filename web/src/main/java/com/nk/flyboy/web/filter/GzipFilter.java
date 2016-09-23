package com.nk.flyboy.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

/**
 * Created by cheris on 2016/9/21.
 * 压缩过滤器，将web应用中的文本都经过压缩后输出到浏览器
 *
 */
public class GzipFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        HttpServletResponse httpServletResponse=(HttpServletResponse)response;

        BufferResponse myResponse=new BufferResponse(httpServletResponse);
        chain.doFilter(request, myResponse);

        byte[] out=myResponse.getBuffer();
        System.out.println("size :" +out.length);

        ByteArrayOutputStream bout=new ByteArrayOutputStream();

        //压缩输出流中的数据
        GZIPOutputStream gout=new GZIPOutputStream(bout);
        gout.write(out);
        gout.close();

        byte gzip[]=bout.toByteArray();
        System.out.println("gzip size :"+gzip.length);

        httpServletResponse.setHeader("content-encoding","gzip");
        httpServletResponse.setContentLength(gzip.length);
        httpServletResponse.getOutputStream().write(gzip);

    }

    public void destroy() {

    }

    class BufferResponse extends HttpServletResponseWrapper{

        private ByteArrayOutputStream bout=new ByteArrayOutputStream();

        private PrintWriter printWriter;

        private HttpServletResponse response;


        /**
         * Constructs a response adaptor wrapping the given response.
         *
         * @param response
         * @throws IllegalArgumentException if the response is null
         */
        public BufferResponse(HttpServletResponse response) {
            super(response);
            this.response=response;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException{
            return new MyServletOutputStream(bout);
        }

        @Override
        public PrintWriter getWriter() throws IOException{
            printWriter=new PrintWriter(new OutputStreamWriter(bout,this.response.getCharacterEncoding()));
            return printWriter;
        }

        public byte[] getBuffer(){
            try{
                if(printWriter!=null){
                    printWriter.close();
                }

                if(bout!=null){
                    bout.flush();
                    return bout.toByteArray();
                }
                return null;
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }

    }

    class MyServletOutputStream extends ServletOutputStream{

        private ByteArrayOutputStream bout;

        public MyServletOutputStream(ByteArrayOutputStream bout){
            this.bout=bout;
        }

        @Override
        public void write(int b) throws IOException {
            this.bout.write(b);
        }
    }
}
