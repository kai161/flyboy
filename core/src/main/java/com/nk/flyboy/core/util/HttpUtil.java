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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        HttpResponse httpResponse=httpClient.execute(httpGet);

        if(httpResponse==null||200!=httpResponse.getStatusLine().getStatusCode()){
            throw new IOException("connect fail...");
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

    public static String htmlParser(String url) throws IOException {
        String parserContent=null;

        Document document=Jsoup.connect(url).get();


        return parserContent;
    }
}
