package com.nk.flyboy.core.action;

import com.nk.flyboy.core.util.HttpUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by kai on 2017/1/20.
 */
public class GrapWeb {
    static List<String> usedList=new CopyOnWriteArrayList<>();
    static List<String> waitList=new CopyOnWriteArrayList<String>();
    static String savePath;
    static CountDownLatch downLatch=new CountDownLatch(10);

    public GrapWeb(String url, String savePath){

        System.out.println("----------prepare to work, grap Web source : "+url+", save path : "+savePath+"---------");
        waitList.add(url);
        this.savePath=savePath;
    }

    public void start() throws InterruptedException {
        DealRunnable dealRunnable=new DealRunnable();
        System.out.println("----------begin work---------");
        long startTime= System.currentTimeMillis();
        for(int i=0;i<10;i++){

            new Thread(dealRunnable,"thread-"+i).start();
        }
        downLatch.await();
        long endTime= System.currentTimeMillis();
        long usedTime=(endTime-startTime)/1000;
        System.out.println("----------end work, use time : "+usedTime+"---------");


    }

    public class DealRunnable implements Runnable{

        @Override
        public void run() {
            try {
                //随机等待几秒钟开启线程
                Random random=new Random(10);
                //Thread.sleep(random.nextInt());

                System.out.println(Thread.currentThread().getName()+" begin work---->");

                //连续等待一分钟没有需处理URL则结束该线程
                int flag=0;
                while (flag<=10){
                    try {
                        if(waitList.size()>0){
                            //String url=waitList.get(0);
                            //waitList.remove(url);

                            //防重复读取waitlist里的URL
                            String url=waitList.remove(0);
                            usedList.add(url);
                            System.out.println(Thread.currentThread().getName()+" start deal url:"+url);
                            pareseHtml(url);
                            flag=0;
                        }else {
                            Thread.sleep(6000);
                            flag++;
                        }
                    }catch (Exception ex){
                        System.out.println(Thread.currentThread().getName()+" exception :"+ ex.getMessage());
                    }
                }

                System.out.println(Thread.currentThread().getName()+" end  work<----");
            }catch (Exception ex){
                System.out.println(Thread.currentThread().getName()+" exception :"+ ex.getMessage());
            }finally {
                downLatch.countDown();
            }
        }
    }

    /**
     * 解析URL对应的页面进行保存，并提取出页面中所有的链接
     */
    public void pareseHtml(String url) throws IOException {
        Document document= HttpUtil.htmlParser(url);
        if(document==null) return;
        Elements elements=document.getElementsByTag("a");
        if(elements!=null){
            for(Element item:elements){
                String href= item.attr("href");
                recordEffectiveURL(url, href);
            }
        }

        String saveFileName=savePath+url.replace("http://","").replace("https://","");
        String dir=saveFileName.substring(0,saveFileName.lastIndexOf("/"));

        if(!saveFileName.endsWith(".html")&&!saveFileName.endsWith(".htm")){
            saveFileName=saveFileName+".html";
        }
        writeDocumentToFile(document, saveFileName, dir);


    }

    /**
     * 保存获取的文件
     */
    private void writeDocumentToFile(Document document, String saveFileName, String dir) throws IOException {
        //判断文件目录是否存在
        File dirFile=new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }

        //创建并保存文件
        File file=new File(saveFileName);
        if(!file.exists()){
            if(file.createNewFile()){
                try(FileOutputStream fileOutputStream=new FileOutputStream(file)) {
                    fileOutputStream.write(document.html().getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            };
        }
    }

    /**
     * 识别页面中有效的URL地址并保存到需访问的waitlist中（外网地址不抓取）
     */
    private void recordEffectiveURL(String url, String href) {
        if(StringUtils.isEmpty(href)) return;
        if(href.startsWith("http")&&!href.contains("souyidai")) return;
        if(href.startsWith("javascript")||href.startsWith("#")) return;

        if(href.startsWith("/")){
            try {
                URL netUrl=new URL(url);
                String demoinURL=netUrl.getHost();
                String protocol=netUrl.getProtocol();
                href=protocol+"://"+demoinURL+href;
            } catch (MalformedURLException e) {
                System.out.println("recordURL fail :"+ e.getMessage());
                return;
            }

        }

        if(!usedList.contains(href)&&!waitList.contains(href)){
            waitList.add(href);
        }

    }

    public static void main(String[] args) {
        try {
            String savePath="/Users/kai/work/data/";
            String webSite="https://www.souyidai.com/";
            GrapWeb grapWeb=new GrapWeb(webSite,savePath);
            grapWeb.start();

        }catch (Exception ex){

        }
    }
}
