package com.nk.flyboy.dba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import javax.sql.DataSource;
import javax.swing.tree.RowMapper;
import java.util.Map;

/**
 * Created by kai on 2017/1/5.
 */
@RestController
public class LiquibaseController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateDataBase(){

        Map<String,Object> map= jdbcTemplate.queryForMap("select vcard_id from vcard_account where vcard_id='212939088025317376'");

        if(map!=null){
            return map.get("vcard_id").toString();
        }

        return  "success";
    }


}
