package com.nk.flyboy.core.util;

import com.sun.java.util.jar.pack.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by kai on 2016/12/22.
 */
public class Excel{

    private static final String EXCEL_2003_POSTFIX="xls";
    private static final String EXCEL_2010_POSTFIX="xlsx";
    private static  Map<Integer,String> columes=new  HashMap();
    private static int feildLength=0;

    public static <T>List<T> readExcel  (Class<T> c,String path) throws IllegalAccessException, InstantiationException, IOException, NoSuchFieldException, OpenXML4JException, ParserConfigurationException, SAXException {
        if(StringUtils.isEmpty(path)){
            return null;
        }

        String postfix=getPostfix(path);

        if(EXCEL_2003_POSTFIX.equals(postfix)){
            return readXLS(c,path);
        }
        if(EXCEL_2010_POSTFIX.equals(postfix)){
            return readXLSX(c, path);
        }
/*        InputStream inputStream=new FileInputStream(path);
        Workbook workbook=getWorkbook(inputStream, postfix);
        return   read(c,workbook);*/
        return null;
    }

    private static <T>List<T> readXLS(Class<T> c,String path) throws IOException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> result=new ArrayList<T>();

        InputStream inputStream=new FileInputStream(path);
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook(inputStream);

        for (int index=0;index<hssfWorkbook.getNumberOfSheets();index++){
            HSSFSheet hssfSheet=hssfWorkbook.getSheetAt(index);
            if(hssfSheet==null){
                continue;
            }
            Map<Integer,String> columes=new  HashMap();
            int fieldsLength= c.getDeclaredFields().length;
            int sheetColumes=hssfSheet.getPhysicalNumberOfRows();
            int loopColumes=fieldsLength>sheetColumes?sheetColumes:fieldsLength;

            for(int rowNum=0;rowNum<=hssfSheet.getLastRowNum();rowNum++){
                HSSFRow hssfRow=hssfSheet.getRow(rowNum);
                if(hssfRow!=null){
                    if(rowNum==0){
                        for(int j=0;j<loopColumes;j++) {
                            columes.put(j, hssfRow.getCell(j).toString());
                        }
                        continue;
                    }
                    T t=c.newInstance();
                    for(int j=0;j<loopColumes;j++){
                        Field field= c.getDeclaredField(columes.get(j));
                        field.setAccessible(true);
                        field.set(t,getCellValue(hssfRow.getCell(j)));
                    }
                    result.add(t);
                }
            }
        }

        return result;
    }

    private static <T>List<T> readXLSX(Class<T> c,String path) throws IOException, IllegalAccessException, InstantiationException, NoSuchFieldException, OpenXML4JException, ParserConfigurationException, SAXException {
        List<T> result=new ArrayList<T>();
        long before=System.currentTimeMillis();
        feildLength=c.getDeclaredFields().length;
        OPCPackage p=OPCPackage.open(new File(path), PackageAccess.READ);
        XlSX2CSV xlSX2CSV=new XlSX2CSV(p);
        result= xlSX2CSV.process(c);
        p.close();
        long end=System.currentTimeMillis();

        System.out.println(end-before);
        return result;
    }


    private static <T>List<T> read(Class<T> c,Workbook workbook) throws IOException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> result=new ArrayList<T>();

        for(int index=0;index<workbook.getNumberOfSheets();index++){
            Sheet sheet=workbook.getSheetAt(index);
            if(sheet==null){
                continue;
            }

            Map<Integer,String> columes=new  HashMap();
            int fieldsLength= c.getDeclaredFields().length;
            int sheetColumes=sheet.getPhysicalNumberOfRows();
            int loopColumes=fieldsLength>sheetColumes?sheetColumes:fieldsLength;

            for(int rowNum=0;rowNum<=sheet.getLastRowNum();rowNum++){
                Row row=sheet.getRow(rowNum);
                if(row!=null){
                    if(rowNum==0){
                        for(int j=0;j<loopColumes;j++) {
                            columes.put(j, row.getCell(j).toString());
                        }
                        continue;
                    }
                    T t=c.newInstance();
                    for(int j=0;j<loopColumes;j++){
                        Field field= c.getDeclaredField(columes.get(j));
                        field.setAccessible(true);
                        field.set(t,getCellValue(row.getCell(j)));
                    }
                    result.add(t);
                }
            }
        }
        return result;
    }

    private static Workbook getWorkbook(InputStream inputStream, String postfix) throws IOException {

        if(EXCEL_2003_POSTFIX.equals(postfix)){
            return new HSSFWorkbook(inputStream);
        }
        if(EXCEL_2010_POSTFIX.equals(postfix)){
            return new XSSFWorkbook(inputStream);
        }

        return null;
    }

    private static String getCellValue(Cell cell){
        String cellValue="";
        DecimalFormat decimalFormat=new DecimalFormat("");
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue=String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellValue=decimalFormat.format(cell.getNumericCellValue()).toString().trim();
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue=cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue=cell.getStringCellValue();
                break;
            default:
                cellValue="";
                break;
        }
        return cellValue;
    }
    private static String getPostfix(String path){
        return path.contains(".")?path.substring(path.indexOf(".")+1,path.length()):null;
    }

    public static class XlSX2CSV{

        public class MyHander<T> implements XSSFSheetXMLHandler.SheetContentsHandler {
            private Class<T> tClass;
            private  T t;
            public MyHander(Class<T> c){
                this.tClass=c;
            }

            List<T> list=new ArrayList<T>();

            public List<T> getList(){
                return list;
            }

            private boolean firstCellOfRow=false;

            //用于判断当前行是否有cell
            private boolean hasCell=false;
            private int currentRow=-1;
            private int currentCol=-1;

            public void startRow(int rowNum) {
                firstCellOfRow=true;
                hasCell=false;
                currentRow=rowNum;
                currentCol=0;
                try {
                    t=tClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            public void endRow(int rowNum) {
                if(currentRow>0&&t!=null&&hasCell){
                    list.add(t);
                }else {
                    t=null;
                }

            }

            public void cell(String cellReference, String formattedValue, XSSFComment comment) {
                hasCell=true;
                if(currentCol>=feildLength){
                    return;
                }

                if(cellReference==null){
                    cellReference=new CellAddress(currentRow,currentCol).formatAsString();
                }

                if(currentRow==0){
                    columes.put(currentCol,formattedValue);
                }else {
                    try {
                        Field field= tClass.getDeclaredField(columes.get(currentCol));
                        field.setAccessible(true);
                        field.set(t,formattedValue);

                    }catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }

                int thisCol=(new CellReference(cellReference)).getCol();
                currentCol=thisCol+1;

            }

            public void headerFooter(String text, boolean isHeader, String tagName) {

            }
        }


        private final OPCPackage xlsxPackage;

        public XlSX2CSV(OPCPackage pkg){
            this.xlsxPackage=pkg;
        }

        public <T>List<T> processSheet(StylesTable stylesTable,ReadOnlySharedStringsTable readOnlySharedStringsTable,MyHander sheetContentsHandler,
                                    InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
            DataFormatter dataFormat=new DataFormatter();
            InputSource inputSource=new InputSource(inputStream);
            XMLReader sheetParser= SAXHelper.newXMLReader();
            ContentHandler handler=new XSSFSheetXMLHandler(stylesTable,null,readOnlySharedStringsTable,sheetContentsHandler,dataFormat,false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(inputSource);
            return sheetContentsHandler.getList();
        }

        private <T>List<T> process(Class<T> c) throws IOException, SAXException, OpenXML4JException, ParserConfigurationException, IllegalAccessException, InstantiationException {
            ReadOnlySharedStringsTable readOnlySharedStringsTable=new ReadOnlySharedStringsTable(this.xlsxPackage);
            XSSFReader xssfReader=new XSSFReader(this.xlsxPackage);
            StylesTable stylesTable=xssfReader.getStylesTable();
            XSSFReader.SheetIterator iterator=(XSSFReader.SheetIterator)xssfReader.getSheetsData();

            List<T> list=new ArrayList<T>();
            int index=0;
            while (iterator.hasNext()){
                InputStream inputStream=iterator.next();
                String sheetName=iterator.getSheetName();
                System.out.println("sheetname:" + sheetName);
                list= processSheet(stylesTable,readOnlySharedStringsTable,new MyHander(c),inputStream);
                inputStream.close();
                ++index;
            }
            return list;
        }

    }

}
