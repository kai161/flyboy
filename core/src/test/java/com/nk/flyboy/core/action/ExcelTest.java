package com.nk.flyboy.core.action;

import com.nk.flyboy.core.action.model.ExcelModel;
import com.nk.flyboy.core.util.Excel;
import com.nk.flyboy.model.Member;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Created by kai on 2016/12/22.
 */
public class ExcelTest {

    public static void main(String[] args) {

        try {
            List<ExcelModel> list=Excel.readExcel(ExcelModel.class,"D:\\test2.xlsx");

            int size=list.size();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
