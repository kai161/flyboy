package com.nk.flyboy.jdbc;


import com.nk.flyboy.model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by cheris on 2016/5/25.
 */
public class BaseMapper<T> implements RowMapper<T> {

    public T t;

    public BaseMapper(T t) {
        this.t = t;
    }

    public T mapRow(ResultSet resultSet, int i) throws SQLException {


        try {
            T t1=(T)t.getClass().newInstance();
            Field[] fields= t1.getClass().getDeclaredFields();
            if(fields!=null&&fields.length>0){
                for(int j=0;j<fields.length;j++){
                    Field field=fields[j];
                    String fieldName=field.getName().toLowerCase();
                    //Class clazz=field.getDeclaringClass();
                    field.set(t1, resultSet.getString(fieldName));
                }
            }
            return t1;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return t;
    }
}
