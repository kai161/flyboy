package com.nk.flyboy.core.design_pattern.facade;

/**
 * Created on 2017/3/28.
 *
 * 应用实例： 1、去医院看病，可能要去挂号、门诊、划价、取药，让患者或患者家属觉得很复杂，如果有提供接待人员，只让接待人员来处理，就很方便。 2、JAVA 的三层开发模式。
 * 优点： 1、减少系统相互依赖。 2、提高灵活性。 3、提高了安全性。
 * 缺点：不符合开闭原则，如果要改东西很麻烦，继承重写都不合适。
 */
public class Test {

    public static void main(String[] args) {
        ShapeMaker shapeMaker=new ShapeMaker();

        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }
}
