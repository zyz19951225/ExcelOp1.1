package com.example.demo.service.PeopleServicePak;

import com.example.demo.mapper.PeopleMapper;
import com.example.demo.model.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    PeopleMapper peopleMapper;

    @Override
    public ArrayList<People> selectAll() {
        peopleMapper.selectAll();
        return peopleMapper.selectAll();
    }
}
