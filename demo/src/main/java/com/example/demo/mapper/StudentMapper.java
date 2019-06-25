package com.example.demo.mapper;

import com.example.demo.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    ArrayList<Student> selectAll();
}