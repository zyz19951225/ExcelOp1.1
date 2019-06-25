package com.example.demo.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AllSqlMapper {

    @Select("select table_name from information_schema.tables where table_schema='excel'")
    List<String> selectAllSqlName();

    @Select("select COLUMN_NAME from information_schema.COLUMNS where table_schema='excel' AND TABLE_NAME =#{tableName}")
    List<String> selectAllColumn_Name(String tableName);
    @Select("select COLUMN_COMMENT from information_schema.COLUMNS where TABLE_SCHEMA = 'excel' and TABLE_NAME=#{tableName}")
    List<String> selectAllColumn_Comment(String tableName);


}