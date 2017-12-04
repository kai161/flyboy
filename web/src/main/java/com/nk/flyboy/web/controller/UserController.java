package com.nk.flyboy.web.controller;

import com.nk.flyboy.core.action.user.UserInfoAction;
import com.nk.flyboy.core.service.redis.queue.Product;
import com.nk.flyboy.core.util.Excel;
import com.nk.flyboy.core.util.IDGenerator;
import com.nk.flyboy.model.ExcelModel;
import com.nk.flyboy.model.Member;
import com.nk.flyboy.web.annotation.SqlFilter;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheris on 2016/5/24.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserInfoAction userInfoAction;
    @Resource
    private Product product;


    @SqlFilter
    @RequestMapping(value = "/userinfo")
    public String userInfo(ModelMap model){

        logger.info("logger for controller");

        List<Member> list=userInfoAction.execute();
        model.addAttribute("list",list);


        product.setKey();

        return "userInfo";

    }

    @RequestMapping(value = "/id")
    @ResponseBody
    public Long getId(){
       return IDGenerator.generateId();
    }

    @RequestMapping("/exportExcel/{title}/{postfix}")
    public void exportExcel(@PathVariable String title,@PathVariable String postfix, HttpServletResponse response){
        HashMap<String,List<ExcelModel>> hashMap=new HashMap<String, List<ExcelModel>>();
        for (int j=0;j<5;j++){
            List<ExcelModel> list=new ArrayList<ExcelModel>();

            ExcelModel model=null;
            for(int i=0;i<10;i++){
                model=new ExcelModel();
                model.setActiveName("active_"+j+i);
                model.setTime(System.currentTimeMillis());
                model.setCount(i);
                list.add(model);
            }

            hashMap.put("中文不支持"+j,list);

        }


        //设置response头信息
        String contentType="application/vnd.ms-excel";
        String filePostfix="xls";
        if(!StringUtils.isEmpty(postfix)&&"xlsx".equalsIgnoreCase(postfix)){
            contentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            filePostfix="xlsx";
        }

        if(StringUtils.isEmpty(title)){
            title=System.currentTimeMillis()+"";
        }
        response.setContentType(contentType);        //改成输出excel文件
        //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");        //改成输出excel文件
        response.setHeader("Content-disposition","attachment; filename="+title+"."+filePostfix );

        try {
            OutputStream outputStream=response.getOutputStream();
            //Excel.exportExcel(title,list,filePostfix,outputStream);
            Excel.exportExcelWithManySheet(hashMap,filePostfix,outputStream);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }

    }

}
