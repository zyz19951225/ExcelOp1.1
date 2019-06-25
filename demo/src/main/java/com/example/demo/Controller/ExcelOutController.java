package com.example.demo.Controller;

import com.example.demo.Utils.RequestToList;
import com.example.demo.error.EmOpratingError;
import com.example.demo.error.ExcelOprateErr;
import com.example.demo.mapper.AllSqlMapper;
import com.example.demo.model.Failname;
import com.example.demo.model.Info;
import com.example.demo.model.Student;
import com.example.demo.response.CommonReturnType;
import com.example.demo.service.ExcelService.ExcelImportService;
import com.example.demo.service.ExcelService.ExcelOutService;
import com.example.demo.service.PeopleServicePak.PeopleServiceImpl;
import com.example.demo.service.StudentServicePak.StudentServiceImpl;
import com.example.demo.service.InfoServicePak.InfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@Controller
public class ExcelOutController {


    @Autowired
    AllSqlMapper allSqlMapper;


    @Autowired
    ExcelImportService excelImportService;

    @Autowired
    InfoServiceImpl infoServiceImpl;

    @Autowired
    StudentServiceImpl studentServiceImpl;

    @Autowired
    PeopleServiceImpl peopleService;

    @Autowired
    Info info;

    @Autowired
    Student student;

    @Autowired
    ExcelOutService excelout;

    @Autowired
    RequestToList requestToList;

    @Autowired
    Failname failname;



    @RequestMapping(value = "/down")
    public String download(Model model){
        List<String> nameList = allSqlMapper.selectAllSqlName();
        model.addAttribute("nameList",nameList);
        return "download";
    }

//    @RequestMapping(value = "/alldownload/")
//    @ResponseBody
//    public CommonReturnType alldownloadExcel(@RequestParam("sqlname") String sqlname) throws ExcelOprateErr {
//        List<String> columnProperty = allSqlMapper.selectAllColumn_Name(sqlname);
//        List<?> dataList = null;
//        dataList = studentServiceImpl.selectAll();
//        System.out.println(dataList.get(2));
//        if (sqlname.equals("info")){
//            dataList = infoServiceImpl.selectAll();
//        }else if (sqlname.equals("student")){
//            dataList = studentServiceImpl.selectAll();
//            System.out.println(dataList.get(2));
//        }
//        else {
//            throw new ExcelOprateErr(EmOpratingError.UNKNOW_ERROR,"对应的数据库表不存在");
//        }
//
//        List<String>  columnText = allSqlMapper.selectAllColumn_Comment(sqlname);
//
//       // System.out.println(sqlname);
//        String path = "d:"+sqlname+".xls";
//       // System.out.println(path);
//        excelout.exportExcel(columnText,columnProperty,dataList,path,"sheet");
//        return CommonReturnType.create(null);
//    }


    @RequestMapping(value = "/downloadList")
    @ResponseBody

    public CommonReturnType downloadExcelList(@RequestParam("Sqlname") String [] sqlnames ) throws ExcelOprateErr {
        List<Failname> failnames= new ArrayList<>();
        for(String sqlname:sqlnames){
            List<String> columnProperty = allSqlMapper.selectAllColumn_Name(sqlname);
            List<?> dataList = null;
            dataList = studentServiceImpl.selectAll();
          //  System.out.println(dataList.get(2));
            if (sqlname.equals("info")){
                dataList = infoServiceImpl.selectAll();
            }else if (sqlname.equals("student")){
                dataList = studentServiceImpl.selectAll();
               // System.out.println(dataList.get(2));
            }
            else if (sqlname.equals("people")){
                dataList = peopleService.selectAll();
                // System.out.println(dataList.get(2));
            }
            else {
                System.out.println("*****");
                int count = 0;
                failnames.add(new Failname(count,sqlname));
                continue;
                //throw new ExcelOprateErr(EmOpratingError.UNKNOW_ERROR,"对应的数据库表--"+sqlname+"--不存在");
            }

            List<String>  columnText = allSqlMapper.selectAllColumn_Comment(sqlname);

            String path = "d:"+sqlname+".xls";
            excelout.exportExcel(columnText,columnProperty,dataList,path,"sheet");
        }

        if(!failnames.isEmpty()){
            return CommonReturnType.create(failnames,"Some_Files_Fail");

        }
        return CommonReturnType.create(null);

    }


}
