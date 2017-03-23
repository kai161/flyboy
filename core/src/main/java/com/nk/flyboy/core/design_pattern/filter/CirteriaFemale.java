package com.nk.flyboy.core.design_pattern.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/23.
 */
public class CirteriaFemale implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> femalePersons=new ArrayList<>();
        for(Person person:persons){
            if(person.getGender().equalsIgnoreCase("female")){
                femalePersons.add(person);
            }
        }
        return femalePersons;
    }
}
