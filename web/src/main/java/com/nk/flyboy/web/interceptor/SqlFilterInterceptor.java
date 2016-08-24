package com.nk.flyboy.web.interceptor;

import com.nk.flyboy.web.annotation.SqlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Enumeration;

/**
 * Created by cheris on 2016/5/30.
 * 对请求参数做sql注入过滤
 */
public class SqlFilterInterceptor extends HandlerInterceptorAdapter {

    private Logger logger= LoggerFactory.getLogger(SqlFilterInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //静态资源的访问不验证
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Annotation classAnnotation = handlerMethod.getBean().getClass().getAnnotation(SqlFilter.class);
        logger.debug("begin SqlFilterInterceptor.....");

        if (classAnnotation != null) {
            if(!validateRequestParam(request)){
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                PrintWriter printWriter=response.getWriter();
                printWriter.append("request have illegal character");
                printWriter.flush();
                printWriter.close();
                return false;
            }
        }

        Annotation methodAnnotation = handlerMethod.getMethodAnnotation(SqlFilter.class);
        if(methodAnnotation!=null){
            if(!validateRequestParam(request)){
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                PrintWriter printWriter=response.getWriter();
                printWriter.append("request have illegal character");
                printWriter.flush();
                printWriter.close();
                return false;
            }
        }
        logger.debug("end SqlFilterInterceptor.....");
        return true;
    }

    private boolean validateRequestParam(HttpServletRequest request){
        String requsetParamStr= getRequsetParamStr(request);
        if(!StringUtils.isEmpty(requsetParamStr) &&isContainsSqlKey(requsetParamStr)){
            logger.debug("the requstParams {} contains SqlKey ", requsetParamStr);
            return false;
        }

        return true;
    }

    //得到请求参数并拼接成字符串
    private String getRequsetParamStr(HttpServletRequest request) {
        Enumeration parameterNames = request.getParameterNames();
        StringBuilder stringBuilder=new StringBuilder();
        while (parameterNames.hasMoreElements()){
            String paramName=parameterNames.nextElement().toString();
            String[] values=request.getParameterValues(paramName);
            for(int i=0;i<values.length;i++){
                stringBuilder.append(values[i]);
            }
        }
        logger.debug("requestParameter String {}",stringBuilder.toString());

        return stringBuilder.toString();
    }

    private boolean isContainsSqlKey(String reqParamStr){
        boolean reslut=false;
        reqParamStr=reqParamStr.toLowerCase();

        //Sql关键字
        String badStr = "'|and|exec|execute|insert|create|drop|table|from|grant|use|group_concat|column_name|" +
                                 "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                               "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";
        String[] badStrList=badStr.split("\\|");
        for(int i=0;i<badStrList.length;i++){
            if(reqParamStr.contains(badStrList[i])){
                logger.debug("reqParamStr contains SqlKey {}",badStrList[i]);
                return true;
            }
        }

        return reslut;
    }


}
