package com.nk.flyboy.core.design_pattern.filter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/23.
 *
 * 过滤器模式（Filter Pattern）或标准模式（Criteria Pattern）是一种设计模式，这种模式允许开发人员使用不同的标准来过滤一组对象，
 * 通过逻辑运算以解耦的方式把它们连接起来。这种类型的设计模式属于结构型模式，它结合多个标准来获得单一标准。
 */
public class Test {

    public static void main(String[] args) {

        List<Person> persons=new ArrayList<>();

        persons.add(new Person("robert","male","single"));
        persons.add(new Person("john","male","married"));
        persons.add(new Person("Laura","female","married"));
        persons.add(new Person("diana","female","single"));
        persons.add(new Person("mike","male","single"));
        persons.add(new Person("bobby","female","single"));

        Criteria male=new CriteriaMale();
        Criteria female=new CirteriaFemale();
        Criteria single=new CirteriaSingle();
        Criteria singleMale=new AndCriteria(male,single);
        Criteria singleOrFemale=new OrCriteria(single,female);

        System.out.println("males:");
        printPersons(male.meetCriteria(persons));

        System.out.println("single males");
        printPersons(singleMale.meetCriteria(persons));

        System.out.println("single or females");
        printPersons(singleOrFemale.meetCriteria(persons));

    }

    public static void printPersons(List<Person> persons){
        for(Person person:persons){
            System.out.println("person name:"+person.getName()
                +",gender:"+person.getGender()+",Marital Status:"
                    +person.getMaritalStatus()
            );
        }
    }
}
