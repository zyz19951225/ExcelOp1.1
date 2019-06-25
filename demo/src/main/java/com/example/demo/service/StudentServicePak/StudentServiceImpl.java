package com.example.demo.service.StudentServicePak;

import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Override
    public int insertSelective(Student record) {
        int i = studentMapper.insertSelective(record);
        return i;
    }

    @Override
    public ArrayList<Student> selectAll() {
        ArrayList<Student> all = studentMapper.selectAll();
        return all;
    }
}
