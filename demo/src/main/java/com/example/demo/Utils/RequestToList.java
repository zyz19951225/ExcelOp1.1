package com.example.demo.Utils;

import com.example.demo.error.EmOpratingError;
import com.example.demo.error.ExcelOprateErr;
import com.example.demo.service.ExcelService.ExcelImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Service
public class RequestToList {

    @Autowired
    ExcelImportService excelImportService;

    public Map<Integer, List<String>> RequestToList(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("filename");
        String fileName =file.getOriginalFilename();
        try {
            fileName = fileName.substring(0,1).toUpperCase()+fileName.substring(1);
        } catch (Exception e) {
            throw new ExcelOprateErr(EmOpratingError.FILE_EMPTY);
        }
        if (file.isEmpty()) {
            throw new ExcelOprateErr(EmOpratingError.FILE_EMPTY);
        }
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new ExcelOprateErr(EmOpratingError.FILE_TYPE_ERROR);
        }
        InputStream inputStream = file.getInputStream();
        Map<Integer, List<String>> mylist = null;
        //调用导入方法
        mylist = excelImportService.readExcelContent(inputStream,fileName,0);
        return mylist;
    }


}
