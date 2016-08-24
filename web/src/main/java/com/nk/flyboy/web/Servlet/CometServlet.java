package com.nk.flyboy.web.servlet;


import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by cheris on 2016/7/27.
 */
@WebServlet(value = "/comet/longpolling",asyncSupported = true)
public class CometServlet extends HttpServlet {

    private static final Queue<AsyncContext>  CONNECTIONS=new ConcurrentLinkedQueue<AsyncContext>();

    public void init(){
        System.out.println("cometServlet init");
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response){
        String method=request.getParameter("method");
        if(method.equals("onOpen")){
            onOpen(request,response);
        }else if(method.equals("onMessage")){
            onMessage(request,response);
        }
    }

    private void onOpen(HttpServletRequest request,HttpServletResponse response){
        AsyncContext context= request.startAsync();
        context.setTimeout(0);
        CONNECTIONS.offer(context);
    }

    private void onMessage(HttpServletRequest request,HttpServletResponse response){
        String msg=request.getParameter("msg");
        broadcast(msg);

    }

    private synchronized void broadcast(String msg){
        for(AsyncContext context:CONNECTIONS){
            HttpServletResponse response=(HttpServletResponse)context.getResponse();
            try{
                PrintWriter printWriter=response.getWriter();
                printWriter.print(msg);
                printWriter.flush();
                printWriter.close();
                context.complete();
                CONNECTIONS.remove(context);

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
