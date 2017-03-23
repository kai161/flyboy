package com.nk.flyboy.core.design_pattern.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/23.
 */
public class CirteriaSingle implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> singlePersons=new ArrayList<>();
        for(Person person:persons){
            if(person.getMaritalStatus().equalsIgnoreCase("single")){
                singlePersons.add(person);
            }
        }
        return singlePersons;
    }
}
