package com.example.demo.service.ExcelService;


import com.example.demo.service.InfoServicePak.InfoServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelOutService<E> {

    @Autowired
    InfoServiceImpl Infoservice;

    @Autowired
    InfoServiceImpl InfoServiceImpl;

    /**
     *
     * 功能描述: 生成Excel
     *
     * @param:title 工作表表名
     * @param: String path 文件生成路径
     * @param: List<?> dataList 表格具体内容
     * @param:List<String> columnProperty 列头  excel对应实体类参数列表
     * @param: List<String> columnText 属性对应名称
     */
    public void exportExcel(List<String> columnText, List<String> columnProperty,
                            List<?> dataList, String path, String title) {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            // 创建工作表，设置其表名
            HSSFSheet sheet = workbook.createSheet(title);
            // 创建标题行
            HSSFRow row = sheet.createRow(0);
            sheet.setColumnWidth(0, 5000);
            // 给标题行每行添加标题
            for (int i = 0; i < columnText.size(); i++) {
                row.createCell(i).setCellValue(columnText.get(i));
            }
            //只需要表头，列无内容
            if(columnProperty == null && dataList == null)
                return;
            // 获得这条信息的对象所属类
            Class<? extends Object> objClass = dataList.get(0).getClass();// 返回运行时的类 List<receiveShow> dataLis
            Map<String, Field> fieldMap = new HashMap<>();
            for (int i = 0; i < columnProperty.size(); i++) {
                Field colField = null;
                Class<? extends Object> tempClass = objClass;
                // 消除object类的影响
              //  System.out.println(tempClass.getName().toLowerCase());
                while (tempClass != null && !"java.lang.object".equals(tempClass.getName().toLowerCase())) {
                    try {
                        // getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段
                        // getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
                        colField = tempClass.getDeclaredField(columnProperty.get(i));
                      //  System.out.println(colField);
                        break;
                    } catch (NoSuchFieldException e) {
                        // 子类中没有该属性则在父类中找 companyId createUser createTime.....
                        tempClass = tempClass.getSuperclass();
                        colField = null;
                    }
                }
                if (colField != null) {
                    colField.setAccessible(true);// 类中的成员变量为private,故必须进行此操作
                    fieldMap.put(i + "", colField);
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 数据
            for (int i = 0; i < dataList.size(); i++) {
                HSSFRow dataRow = sheet.createRow(i + 1);//创建新的一行
                for (int j = 0; j < fieldMap.size(); j++) {
                    Field colField = fieldMap.get(j + "");
                    if (colField == null || colField.get(dataList.get(i)) == null) {
                        dataRow.createCell(j).setCellValue("");
                        continue;
                    }
                    switch (colField.getType().toString()) {
                        case "class java.lang.Integer":// 数字类型
                            dataRow.createCell(j).setCellValue(
                                    colField.get(dataList.get(i)) != null ? colField.get(dataList.get(i)).toString() : "");
                            break;
                        case "class java.util.Date":// 日期类型
                            dataRow.createCell(j).setCellValue(sdf.format(colField.get(dataList.get(i))));
                            break;
                        default:
                            dataRow.createCell(j).setCellValue(colField.get(dataList.get(i)).toString());
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File des = new File(path);
            try {
                workbook.write(des);
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




