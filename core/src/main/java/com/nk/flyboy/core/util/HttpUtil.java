package com.nk.flyboy.core.util;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kai on 2017/1/19.
 */
public class HttpUtil {

    /*
    *通过get方式获取响应内容
    *
    */
    public static String get(String url) throws IOException {
        String content=null;

        CloseableHttpClient httpClient=HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
        HttpResponse httpResponse=httpClient.execute(httpGet);

        if(httpResponse==null||200!=httpResponse.getStatusLine().getStatusCode()){
            throw new IOException("connect fail..."+httpResponse.getStatusLine().getReasonPhrase());
        }

        InputStream inputStream= httpResponse.getEntity().getContent();
        byte[] bytes= IOUtils.toByteArray(inputStream);
        content=new String(bytes);

        httpClient.close();

        return content;
    }

    /**
     * 通过post方式获取响应内容
     */
    public static String post(String url, Map<String,String> requstMap) throws IOException{
        String content=null;

        CloseableHttpClient httpClient=HttpClientBuilder.create().build();

        HttpPost httpPost=new HttpPost(url);

        List<NameValuePair> list=new ArrayList<NameValuePair>();
        if(!requstMap.isEmpty()){
            for(String key:requstMap.keySet()){
                list.add(new BasicNameValuePair(key,requstMap.get(key)));
            }
        }
        HttpEntity entity=new UrlEncodedFormEntity(list);
        httpPost.setEntity(entity);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

        HttpResponse response=httpClient.execute(httpPost);
        if(response==null||200!=response.getStatusLine().getStatusCode()){
            throw new IOException("connect fail...");
        }

        InputStream inputStream= response.getEntity().getContent();
        byte[] bytes= IOUtils.toByteArray(inputStream);
        content=new String(bytes);

        httpClient.close();

        return content;
    }

    /**
     * 用jsoup解析HTML页面，添加请求头防403
     */
    public static Document htmlParser(String url) throws IOException {

        Connection connection=Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
        connection.get();
        Connection.Response response= connection.response();
        int statusCode=response.statusCode();
        String contentType= response.contentType();
        if(200!=statusCode||StringUtils.isEmpty(contentType)||!contentType.contains("text/html")){
            return  null;
        }
        Document document=response.parse();

        return document;
    }
}
