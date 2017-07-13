package com.nk.flyboy.core.service.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created on 2017/7/12.
 */
public class QuartzTest {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
        Scheduler scheduler=schedulerFactory.getScheduler();

        JobDetail jobDetail= JobBuilder.newJob().ofType(MyJob.class).withIdentity("myJob","jobGroup").build();

        Date triggerDate=new Date();
        SimpleScheduleBuilder simpleScheduleBuilder=SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever();

        TriggerBuilder<Trigger> triggerBuilder=TriggerBuilder.newTrigger();

        Trigger trigger=triggerBuilder.withIdentity("myTrigger","triggerGroup").startAt(triggerDate).withSchedule(simpleScheduleBuilder).build();

        scheduler.scheduleJob(jobDetail,trigger);

        JobDetail jobDetail1= JobBuilder.newJob().ofType(MyJob1.class).withIdentity("myJob1","jobGroup").build();

        Trigger trigger1=triggerBuilder.withIdentity("myTrigger1","triggerGroup").startAt(triggerDate).withSchedule(simpleScheduleBuilder).build();

        scheduler.scheduleJob(jobDetail1,trigger1);

        scheduler.start();

    }
}
