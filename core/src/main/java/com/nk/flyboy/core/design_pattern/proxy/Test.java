package com.nk.flyboy.core.design_pattern.proxy;

/**
 * Created on 2017/3/29.
 *
 * 优点：
 *  1、职责清晰。 2、高扩展性。 3、智能化。
 * 缺点：
 *  1、由于在客户端和真实主题之间增加了代理对象，因此有些类型的代理模式可能会造成请求的处理速度变慢。 2、实现代理模式需要额外的工作，有些代理模式的实现非常复杂。

 */
public class Test {

    public static void main(String[] args) {
        Image image=new ProxyImage("flower.jpg");

        image.display();

        image.display();
    }
}
