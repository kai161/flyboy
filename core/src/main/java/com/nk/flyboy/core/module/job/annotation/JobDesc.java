package com.nk.flyboy.core.module.job.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2017/7/10.
 */
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface JobDesc {

    //项目名称
    String project();

    //业务名称
    String name();

    //业务描述
    String desc();
}
