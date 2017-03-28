package com.nk.flyboy.core.design_pattern.flyweight;

/**
 * Created on 2017/3/28.
 *
 * 应用实例： 1、JAVA 中的 String，如果有则返回，如果没有则创建一个字符串保存在字符串缓存池里面。 2、数据库的数据池。
 * 优点：
 *  大大减少对象的创建，降低系统的内存，使效率提高。
 * 缺点：
 *  提高了系统的复杂度，需要分离出外部状态和内部状态，而且外部状态具有固有化的性质，不应该随着内部状态的变化而变化，否则会造成系统的混乱。
 */
public class Test {

    private static String colors[]={"red","green","blue","orange"};

    public static void main(String[] args) {
        for (int i=0;i<20;i++){
            Circle circle=(Circle) ShapeFactory.getCircle(getRandomColor());

            circle.setX(getRandomX());
            circle.setY(getRandomY());
            circle.setRadius(10);
            circle.draw();
        }
    }

    private static String getRandomColor(){
       return colors[(int)Math.random()*colors.length];
    }

    private static int getRandomX(){
        return (int)Math.random()*100;
    }

    private static int getRandomY(){
        return (int)Math.random()*100;
    }
}
