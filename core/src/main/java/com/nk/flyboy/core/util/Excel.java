package com.nk.flyboy.core.util;

import com.sun.java.util.jar.pack.*;
import org.apache.poi.hssf.usermodel.*;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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


    public static<T> void exportExcelXLS(String title, List<T> list, OutputStream out) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 30);

        // 遍历集合数据，产生数据行
        Iterator<T> it = list.iterator();
        Field[] fields=null;
        int index = 0;
        while (it.hasNext()) {
            T t = (T) it.next();

            if(index==0){
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                fields = t.getClass().getDeclaredFields();

                // 产生表格标题行
                HSSFRow headerRow = sheet.createRow(0);
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = headerRow.createCell(i);
                    HSSFRichTextString text = new HSSFRichTextString(fields[i].getName());
                    cell.setCellValue(text);
                }
            }
            index++;
            HSSFRow row = sheet.createRow(index);

            for (short i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    String textValue = null;

                    if (value instanceof Date)
                    {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        textValue = sdf.format(date);
                    }else {
                        textValue= value.toString();
                    }

                    if(textValue!=null){
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        cell.setCellValue(richString);
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static Workbook getWorkBook(String postfix){
        if(EXCEL_2003_POSTFIX.equals(postfix)){
            return new HSSFWorkbook();
        }
        if(EXCEL_2010_POSTFIX.equals(postfix)){
            return new XSSFWorkbook();
        }

        return new HSSFWorkbook();
    }
    public static<T> void exportExcel(String title, List<T> list,String postfix,OutputStream out) {
        // 声明一个工作薄
        Workbook workbook = getWorkBook(postfix);

        createNewSheet(workbook,title,list);

        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static<T> void exportExcelWithManySheet(HashMap<String,List<T>> hashMap,String postfix,OutputStream out) {
        // 声明一个工作薄
        Workbook workbook = getWorkBook(postfix);

        if (hashMap!=null&&hashMap.size()>0){
            for(Map.Entry<String,List<T>> entry:hashMap.entrySet()){
                if(entry.getValue()==null||entry.getValue().size()<=0){
                    continue;
                }
                createNewSheet(workbook, entry.getKey(),entry.getValue());
            }
        }

        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static <T> void createNewSheet(Workbook workbook, String title,List<T> list) {
        // 生成一个表格
        Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 30);

        // 遍历集合数据，产生数据行
        Iterator<T> it = list.iterator();
        Field[] fields=null;
        int index = 0;
        while (it.hasNext()) {
            T t = (T) it.next();

            if(index==0){
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                fields = t.getClass().getDeclaredFields();

                // 产生表格标题行
                Row headerRow = sheet.createRow(0);
                for (short i = 0; i < fields.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(fields[i].getName());
                }
            }
            index++;
            Row row = sheet.createRow(index);

            for (short i = 0; i < fields.length; i++) {
                Cell cell = row.createCell(i);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    String textValue = null;

                    if (value instanceof Date)
                    {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        textValue = sdf.format(date);
                    }else {
                        textValue= value.toString();
                    }

                    if(textValue!=null){
                        cell.setCellValue(textValue);
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
    }

}
