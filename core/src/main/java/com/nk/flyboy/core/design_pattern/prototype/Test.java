package com.nk.flyboy.core.design_pattern.prototype;

/**
 * Created on 2017/3/31.
 *
 * 原型模式
 * 应用实例：
 *  1、细胞分裂。 2、JAVA 中的 Object clone() 方法。
 * 优点：
 *  1、性能提高。 2、逃避构造函数的约束。
 * 缺点：
 *  1、配备克隆方法需要对类的功能进行通盘考虑，这对于全新的类不是很难，但对于已有的类不一定很容易，特别当一个类引用不支持串行化的间接对象，或者引用含有循环结构的时候。 2、必须实现 Cloneable 接口。 3、逃避构造函数的约束。
 * 使用场景：
 *  1、资源优化场景。 2、类初始化需要消化非常多的资源，这个资源包括数据、硬件资源等。 3、性能和安全要求的场景。
 *  4、通过 new 产生一个对象需要非常繁琐的数据准备或访问权限，则可以使用原型模式。 5、一个对象多个修改者的场景。
 *  6、一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可以考虑使用原型模式拷贝多个对象供调用者使用。
 *  7、在实际项目中，原型模式很少单独出现，一般是和工厂方法模式一起出现，通过 clone 的方法创建一个对象，然后由工厂方法提供给调用者。原型模式已经与 Java 融为浑然一体，大家可以随手拿来使用。
 */
public class Test {

    public static void main(String[] args) {
        ShapeCache.loadCache();

        Shape cloneShape=ShapeCache.getShape("1");
        System.out.println("shape:"+cloneShape.getType());

        Shape cloneShape1=ShapeCache.getShape("2");
        System.out.println("shape:"+cloneShape1.getType());

        Shape cloneShape2=ShapeCache.getShape("3");
        System.out.println("shape:"+cloneShape2.getType());
    }
}
