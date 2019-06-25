package com.example.demo.Utils;

import com.example.demo.error.EmOpratingError;
import com.example.demo.error.ExcelOprateErr;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ListToBean {

    public static <T> void listToModel(List<? extends String> list, T t) throws Exception {
        Field[] fields = t.getClass().getDeclaredFields();
        if (list.size() != fields.length) {
            return;
        }
        for (int k = 0, len = fields.length; k < len; k++) {
            // 根据属性名称,找寻合适的set方法
            String fieldName = fields[k].getName();
            String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);
            Method method = null;

            Class<?> clazz = t.getClass();
            try {
                method = clazz.getDeclaredMethod(setMethodName,fields[k].getType());
            } catch (SecurityException e1) {
                e1.printStackTrace();
                return;
            } catch (NoSuchMethodException e1) {
                String newMethodName = "set" + fieldName.substring(0, 1).toLowerCase()
                        + fieldName.substring(1);
                try {
                    method = clazz.getMethod(newMethodName, t.getClass());
                } catch (SecurityException e) {
                    e.printStackTrace();
                    return;
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (method == null) {
                return;
            }

            //格式转换
            switch (fields[k].getType().toString()){
                case "class java.lang.Integer":// 数字类型
                    method.invoke(t,Integer.parseInt(list.get(k)));
                    break;
                case "class java.lang.String"://字符串类型
                    method.invoke(t,(String)list.get(k));
                    break;
                case "class java.lang.Long"://Long类型
                    method.invoke(t, Long.parseLong(list.get(k)));
                    break;
                case "class java.lang.Byte"://Byte类型
                    method.invoke(t,list.get(k).getBytes()[0]);
                    break;
                case "class java.lang.Short"://Short类型
                    method.invoke(t, Short.parseShort(list.get(k)));
                    break;
                case "class java.lang.Float"://Float类型
                    method.invoke(t, Float.parseFloat(list.get(k)));
                    break;
                case "class java.lang.Double"://Double类型
                    method.invoke(t, Double.parseDouble(list.get(k)));
                    break;
                case "class java.math.BigDecimal"://BigDecimal类型
                    method.invoke(t, new BigDecimal(list.get(k)));
                    break;
                case "class java.lang.Boolean"://Boolean类型
                    method.invoke(t, Boolean.parseBoolean(list.get(k)));
                    break;
                case "class java.util.Date"://date类型 可进行格式处理
                    System.out.println(Date.class.toString());
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    method.invoke(t,format.parse(list.get(k)));
                    break;
                default:
                    throw new ExcelOprateErr(EmOpratingError.UNKNOW_ERROR,"格式转换异常---请检查ListToBean文件,错误转换类型为-->"+fields[k].getType().toString());
            }
        }
    }

}
