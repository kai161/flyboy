package com.nk.flyboy.core.design_pattern.composite;

/**
 * Created on 2017/3/23.
 *
 * 优点： 1、高层模块调用简单。 2、节点自由增加。
 * 缺点：在使用组合模式时，其叶子和树枝的声明都是实现类，而不是接口，违反了依赖倒置原则。
 * 使用场景：部分、整体场景，如树形菜单，文件、文件夹的管理。
 */
public class Test {
    public static void main(String[] args) {

        Employee CEO=new Employee("john","CEO",30000);
        Employee headSales=new Employee("robert","head sale",20000);
        Employee headMarketing=new Employee("michel","head market",20000);
        Employee clerk1=new Employee("laura","market",5000);
        Employee clerk2=new Employee("bob","market",6000);

        Employee sales1=new Employee("richa","sale",6000);
        Employee sales2=new Employee("rob","sale",7000);

        CEO.add(headSales);
        CEO.add(headMarketing);

        headSales.add(sales1);
        headSales.add(sales2);

        headMarketing.add(clerk1);
        headMarketing.add(clerk2);

        System.out.println(CEO);

        for(Employee headEmployee:CEO.getSubordiantes()){
            System.out.println(headEmployee);
            for (Employee employee:headEmployee.getSubordiantes()){
                System.out.println(employee);
            }
        }
    }
}
