package com.nk.flyboy.jdbc;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
            T t1 = (T) t.getClass().newInstance();
            Field[] fields = t1.getClass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    String fieldName = field.getName().toLowerCase();
                    Class lazz = field.getType();
                    int rowIndex= resultSet.findColumn(fieldName);
                    //Object value = resultSet.getObject(fieldName, lazz);
                    Object object = ConverterTypeByClass(resultSet, rowIndex, lazz);
                    //Object object= JdbcUtils.getResultSetValue(resultSet,rowIndex,lazz);
                    field.set(t1, object);
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

    //由于JdbcUtils没有对localDateTime的属性处理，故重新封装一个处理函数
    public Object ConverterTypeByClass(ResultSet resultSet, int rowIndex, Class clazz) {
        Object result = null;

        try {
            /*if (clazz == java.time.LocalDateTime.class) {
                Timestamp timestamper=resultSet.getTimestamp(rowIndex);
                result = LocalDateTime.ofInstant(timestamper.toInstant(), ZoneId.systemDefault());
            } else if(clazz==java.math.BigDecimal.class){
                result=resultSet.getBigDecimal(rowIndex);
            } else {
                String value=resultSet.getString(rowIndex);
                if(value==null){
                    return null;
                }

                Constructor constructor =  clazz.getConstructor(String.class);;
                result = constructor.newInstance(value);
            }*/
            if (clazz == java.time.LocalDateTime.class) {
                Timestamp timestamper=resultSet.getTimestamp(rowIndex);
                result = LocalDateTime.ofInstant(timestamper.toInstant(), ZoneId.systemDefault());
            }else {
                result= JdbcUtils.getResultSetValue(resultSet,rowIndex,clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


}
