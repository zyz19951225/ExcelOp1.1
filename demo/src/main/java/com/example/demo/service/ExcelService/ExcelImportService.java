package com.example.demo.service.ExcelService;


import com.example.demo.Utils.DataUtil;
import com.example.demo.error.EmOpratingError;
import com.example.demo.error.ExcelOprateErr;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;


@Service
public class ExcelImportService {


    /**
     * 判断文件格式
     *
     * @param inStr
     * @param fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            System.out.println("文件格式不正确");
            throw new ExcelOprateErr(EmOpratingError.FILE_TYPE_ERROR);
           // throw new Exception("请上传excel文件！");
        }
        return workbook;
    }




    public Map<Integer, List<String>> readExcelContent(InputStream is, String fileName, int startRow) throws Exception {
        Map<Integer, List<String>> content = new HashMap<Integer, List<String>>();
        //得到excel文档对象
        Workbook wb =null;
        Sheet sheet;
        Row row;
            if (fileName.endsWith("xls")) {
                 wb = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                 wb = new XSSFWorkbook(is);
            }
            else {
                System.out.println("文件类型不对呀-readExcelContent");
                throw new ExcelOprateErr(EmOpratingError.FILE_TYPE_ERROR);
            }
        //得到excel的表单
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        System.out.println("解析Excel总行数-----"+rowNum);
        //判断第几行开始不为空
        int rowFlag=0;
        while (true) {
            if (sheet.getRow(rowFlag) == null){
                if (rowFlag > 100) {
                    throw new ExcelOprateErr(EmOpratingError.UNKNOW_ERROR,"文件可能不存在数据");
                }
            rowFlag++;
        }
            else break;
        }
        //得到不为空的那一行
        row = sheet.getRow(rowFlag);
        // 得到总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("解析Excel总列数----"+colNum);


        for (int i = startRow; i <= rowNum; i++) {
            row = sheet.getRow(i);
            //判断第几列开始不为空
            int celFlag=0;
            if(row == null){
                continue;
            }
            while(true){
                if (row.getCell(celFlag)==null) celFlag++;
                else break;
            }
            if (row.getCell(celFlag) == null) {
                continue;
            }
            int j = 0;
            List<String> dataList = new ArrayList<>();
            while (j < colNum) {
                Cell cell = row.getCell(j);
                dataList.add(getCellFormatValue(cell).trim());
                j++;
            }
            content.put(i, dataList);
        }
        return content;
    }
    //得到列中单元格的具体的值
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case NUMERIC: {
                    // 取得当前Cell的数值
                    // 这里的日期类型会被转换为数字类型，需要判别后区分处理
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellvalue = cell.getDateCellValue().toLocaleString();
                    } else {
                        BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                        cellvalue = big.toString();
                    }
                    break;
                }
                // case HSSFCell.CELL_TYPE_FORMULA: {
                // cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                // // 判断当前的cell是否为Date
                // if (HSSFDateUtil.isCellDateFormatted(cell)) {
                // // 如果是Date类型则，转化为Data格式
                // // 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                // // cellvalue = cell.getDateCellValue().toLocaleString();
                // // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                // Date date = cell.getDateCellValue();
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // cellvalue = sdf.format(date);
                // }
                // break;
                // }
                case FORMULA:
                    cell.setCellType(CellType.STRING);
                    cellvalue = cell.getStringCellValue();
                    break;
                // 如果当前Cell的Type为STRING
                case STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = "";
                    break;
            }
        } else {
            cellvalue = "";
        }
        return DataUtil.trim(cellvalue.trim());
    }
}
