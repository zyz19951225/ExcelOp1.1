package com.example.demo.Controller;


import com.example.demo.Utils.ListToBean;
import com.example.demo.Utils.RequestToList;
import com.example.demo.error.EmOpratingError;
import com.example.demo.error.ExcelOprateErr;
import com.example.demo.model.Info;
import com.example.demo.model.Student;
import com.example.demo.response.CommonReturnType;
import com.example.demo.service.ExcelService.ExcelImportService;
import com.example.demo.service.ExcelService.ExcelOutService;
import com.example.demo.service.StudentServicePak.StudentServiceImpl;
import com.example.demo.service.InfoServicePak.InfoServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
public class ExcelImportController<T> {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(ExcelImportController.class);


    @Autowired
    ExcelImportService excelImportService;

    @Autowired
    InfoServiceImpl InfoServiceImpl;

    @Autowired
    StudentServiceImpl studentServiceImpl;

    @Autowired
    Info info;

    @Autowired
    Student student;

    @Autowired
    ExcelOutService excelout;

    @Autowired
    RequestToList requestToList;


    @RequestMapping(value = "/up")
    public String html(){
        return "index";
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public CommonReturnType uploadExcel(HttpServletRequest request) throws Exception {
        logger.info("进入upload***");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("filename");
        if (file.isEmpty()) {
           throw new ExcelOprateErr(EmOpratingError.FILE_EMPTY);
        }
        String fileName =file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new ExcelOprateErr(EmOpratingError.FILE_TYPE_ERROR);
        }
        //文件字符串处理 将字符串转换仅首字母大写与对应实体类匹配
        String filename = (fileName.substring(0,1).toUpperCase()+fileName.substring(1).toLowerCase()).split("\\.")[0];
        //获取文件名
        //拼接类名
        String clazzName = "com.example.demo.model."+filename;
        Object instance = null;
        try {
            //加载要反射的类
            Class clz = Class.forName(clazzName);
            //使用找到的构造方法创建实例
            instance = clz.newInstance();
        } catch (Exception e) {
            System.out.println("数据库对应表不存在");
            throw new ExcelOprateErr(EmOpratingError.TABLE_NAME_NOT_FOUND);
        }
        catch (NoClassDefFoundError e){
            System.out.println("检查文件名字");
            throw new ExcelOprateErr(EmOpratingError.TABLE_NAME_ERROR);
        }

        //调用导入方法
       // Map<Integer, List<String>> mylist = null;
        Map<Integer, List<String>> mylist = excelImportService.readExcelContent(inputStream,fileName,0);
        inputStream.close();

        for (int i = 1 ;i<mylist.size(); i++){
            List<String> list = mylist.get(i);
            //list转对象
            ListToBean.listToModel(mylist.get(i),instance);
            //对应不同Excel操作
            if (filename.equals("Info")){
                Info info =(Info)instance;
                InfoServiceImpl.insertSelective(info);
            }
            if (filename.equals("Student")){
                Student student =(Student) instance;
                studentServiceImpl.insertSelective(student);
            }
        }
        return CommonReturnType.create(null);
    }



}