package com.example.demo.mapper;

import com.example.demo.model.People;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface PeopleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(People record);

    int insertSelective(People record);

    People selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(People record);

    int updateByPrimaryKey(People record);

    ArrayList<People> selectAll();
}