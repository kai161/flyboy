package com.nk.flyboy.core.design_pattern.filter;

import java.util.List;

/**
 * Created on 2017/3/23.
 */
public interface Criteria {
    public List<Person> meetCriteria(List<Person> persons);
}
