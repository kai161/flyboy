package com.nk.flyboy.core.design_pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/23.
 */
public class Employee {

    private String name;
    private String dept;
    private int salary;
    List<Employee> subordiantes;

    public Employee(String name,String dept,int salary){
        this.name=name;
        this.dept=dept;
        this.salary=salary;
        subordiantes=new ArrayList<>();
    }

    public void add(Employee employee){
        subordiantes.add(employee);
    }

    public void remove(Employee employee){
        subordiantes.add(employee);
    }

    public List<Employee> getSubordiantes(){
        return subordiantes;
    }

    public String toString(){
        return "Employee: name:"+name+",dept:"+dept+",salary:"+salary;
    }
}
