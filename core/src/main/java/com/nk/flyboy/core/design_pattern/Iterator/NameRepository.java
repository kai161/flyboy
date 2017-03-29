package com.nk.flyboy.core.design_pattern.Iterator;

/**
 * Created on 2017/3/29.
 * 迭代器模式
 * 优点：
 *  1、它支持以不同的方式遍历一个聚合对象。 2、迭代器简化了聚合类。 3、在同一个聚合上可以有多个遍历。 4、在迭代器模式中，增加新的聚合类和迭代器类都很方便，无须修改原有代码。
 * 缺点：
 *  由于迭代器模式将存储数据和遍历数据的职责分离，增加新的聚合类需要对应增加新的迭代器类，类的个数成对增加，这在一定程度上增加了系统的复杂性。
 */
public class NameRepository implements Container {

    private String[] names={"john","robot","julie","tom"};
    @Override
    public Iterator getIterator() {
        return new NameIterator();
    }

    private class NameIterator implements Iterator{

        int index;

        @Override
        public boolean hasNext() {
            if(index<names.length){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if(this.hasNext()){
                return names[index++];
            }
            return null;
        }
    }
}
