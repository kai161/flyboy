package com.nk.flyboy.core.design_pattern.chainofresponsibility;

/**
 * Created on 2017/3/29.
 *
 * 应用实例：
 *  1、红楼梦中的"击鼓传花"。 2、JS 中的事件冒泡。 3、JAVA WEB 中 Apache Tomcat 对 Encoding 的处理，Struts2 的拦截器，jsp servlet 的 Filter。
 * 优点：
 *  1、降低耦合度。它将请求的发送者和接收者解耦。 2、简化了对象。使得对象不需要知道链的结构。 3、增强给对象指派职责的灵活性。通过改变链内的成员或者调动它们的次序，允许动态地新增或者删除责任。 4、增加新的请求处理类很方便。
 * 缺点：
 *  1、不能保证请求一定被接收。 2、系统性能将受到一定影响，而且在进行代码调试时不太方便，可能会造成循环调用。 3、可能不容易观察运行时的特征，有碍于除错。
 *
 */
public class Test {

    public static AbstractLogger getChainOfLoggers(){

        AbstractLogger infoLogger=new ConsoleLogger(AbstractLogger.INFO);
        AbstractLogger errorLogger=new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger debugLogger=new FileLogger(AbstractLogger.DEBUG);

        infoLogger.setNextLogger(errorLogger);
        errorLogger.setNextLogger(debugLogger);

        return infoLogger;
    }

    public static void main(String[] args) {
        AbstractLogger abstractLogger=getChainOfLoggers();

        abstractLogger.logMessage(AbstractLogger.DEBUG,"this is debug info");
        abstractLogger.logMessage(AbstractLogger.ERROR,"this is error info");
        abstractLogger.logMessage(AbstractLogger.INFO,"this is info");
    }
}
