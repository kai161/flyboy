package com.nk.flyboy.core.schedul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by cheris on 2016/5/26.
 */
//@Component
public class SchedulTestJob {

    private Logger logger= LoggerFactory.getLogger(SchedulTestJob.class);


    @Scheduled(cron = "5 * 17 * * ?")
    public void tempUserInfoStatisticJob() throws InterruptedException {

        System.out.println("定时任务");

        Thread.sleep(2000);

        logger.info("定时任务：{},线程名：{}", LocalDateTime.now().toString(),Thread.currentThread().getId());

    }

    @Scheduled(cron = "5 * 17 * * ?")
    public void tempUserInfoStatisticJob1(){

        System.out.println("定时任务1");

        logger.info("定时任务1：{},线程名1：{}", LocalDateTime.now().toString(),Thread.currentThread().getId());

    }

    @Scheduled(cron = "5 * 17 * * ?")
    public void tempUserInfoStatisticJob2(){

        System.out.println("定时任务2");

        logger.info("定时任务2：{},线程名2：{}", LocalDateTime.now().toString(),Thread.currentThread().getId());

    }

    @Scheduled(cron = "5 * 17 * * ?")
    public void tempUserInfoStatisticJob3(){

        System.out.println("定时任务3");

        logger.info("定时任务3：{},线程名3：{}", LocalDateTime.now().toString(),Thread.currentThread().getId());

    }
}
