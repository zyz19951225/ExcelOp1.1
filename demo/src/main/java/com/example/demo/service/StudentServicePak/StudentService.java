package com.example.demo.service.StudentServicePak;


import com.example.demo.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface StudentService {

    int insertSelective(Student record);

    ArrayList<Student> selectAll();
}
