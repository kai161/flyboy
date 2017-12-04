package com.nk.flyboy.core.service.loghelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2017/4/18.
 */
public class Test {

    public static void main(String[] args) {
//        Logger logger=Logger.getLogger();
//
//        long startTime=System.currentTimeMillis();
//        for(int i=0;i<10000;i++){
//            String msg="Returns a composed BiConsumer that performs, in sequence, this operation followed by the after operation. If performing either operation throws an exception, it is relayed to the caller of the composed operation. If performing this operation throws an exception, the after operation will not be performed";
//            logger.debug(msg);
//        }
//
//        long endTime=System.currentTimeMillis();
//
//        System.out.println("cost time:"+(endTime-startTime));

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(1511193600);
        String time=simpleDateFormat.format(date);
        System.out.println(time);
    }
}
