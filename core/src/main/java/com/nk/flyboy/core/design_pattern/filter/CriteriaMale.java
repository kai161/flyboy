package com.nk.flyboy.core.design_pattern.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/23.
 */
public class CriteriaMale implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> malePersons=new ArrayList<>();
        for (Person person:persons){
            if(person.getGender().equalsIgnoreCase("male")){
                malePersons.add(person);
            }
        }

        return malePersons;
    }
}
