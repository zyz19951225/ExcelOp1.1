package com.example.demo.mapper;

import com.example.demo.model.Info;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface InfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Info record);

    int insertSelective(Info record);

    Info selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Info record);

    int updateByPrimaryKey(Info record);

    int insertOrupdate(Info record);

    ArrayList<Info> selectAll();

    //根据主键 保存或更新excel表中数据
  //  int saveOrupdate(ArrayList<Info> infoArrayList);
}