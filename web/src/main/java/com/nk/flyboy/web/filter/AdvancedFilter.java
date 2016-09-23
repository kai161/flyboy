package com.nk.flyboy.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheris on 2016/9/21.
 *
 * 解决中文乱码，转义html标签，过滤敏感字符
 * 只需在web.xml中配置该过滤器，并给定初始值：charset，dirtyWordPath 给filterConfig调用
 */
public class AdvancedFilter implements Filter {

    private FilterConfig filterConfig=null;

    private String defaultCharset="UTF-8";

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;

        String charset=filterConfig.getInitParameter("charset");
        if(charset==null){
            charset=defaultCharset;
        }

        httpServletRequest.setCharacterEncoding(charset);
        httpServletResponse.setCharacterEncoding(charset);
        httpServletResponse.setContentType("text/html;charset=" + charset);

        AdvancedRequest advancedRequest=new AdvancedRequest(httpServletRequest);
        chain.doFilter(advancedRequest,response);

    }

    public void destroy() {

    }

    class AdvancedRequest extends HttpServletRequestWrapper{


        private List<String> dirtyWords=getDirtyWords();

        private HttpServletRequest request=null;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request
         * @throws IllegalArgumentException if the request is null
         */
        public AdvancedRequest(HttpServletRequest request) {
            super(request);
            this.request=request;
        }

        @Override
        public String getParameter(String name){
            try{
                String value=this.request.getParameter(name);
                if(value==null){
                    return null;
                }

                //如果以get方式提交数据，就对获取到的值进行转码处理，防止乱码
                if(this.request.getMethod().equalsIgnoreCase("get")){
                    value=new String(value.getBytes("ISO8859-1"),this.request.getCharacterEncoding());
                }

                //替换html标签字符，防xss
                filterHtml(value);

                //过滤敏感词
                if(dirtyWords.size()>0){
                    for(String dirtyWord:dirtyWords){
                        if(value.contains(dirtyWord)){
                            System.out.println("contains dirtyWord :" +dirtyWord+", replace with ****");
                            value=value.replace(dirtyWord,"****");
                        }
                    }
                }

                return value;
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }

    }

    /*
    * 过滤特殊字符
    */
    private String filterHtml(String value){
        if(value==null){
            return null;
        }

        char content[] =new char[value.length()];
        value.getChars(0,value.length(),content,0);

        StringBuffer result=new StringBuffer(content.length+50);

        for (int i=0;i<content.length;i++){
            switch (content[i]){
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                default:
                    result.append(content[i]);
            }

        }
        return result.toString();
    }

    /*
    *获取敏感字符
    */
    private List<String> getDirtyWords() {

        List<String> dirtyWords=new ArrayList<String>();
        String dirtyWordPath=filterConfig.getInitParameter("dirtyWordPath");
        if(dirtyWordPath==null){
            return dirtyWords;
        }

        InputStream inputStream=filterConfig.getServletContext().getResourceAsStream(dirtyWordPath);
        InputStreamReader is=null;
        try{
            is=new InputStreamReader(inputStream);
        }catch (Exception ex){

        }

        BufferedReader reader=new BufferedReader(is);
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                dirtyWords.add(line);
            }

        }catch (Exception ex){

        }

        return dirtyWords;
    }
}
