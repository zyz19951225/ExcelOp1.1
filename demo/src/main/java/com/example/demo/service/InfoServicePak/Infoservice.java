package com.example.demo.service.InfoServicePak;

import com.example.demo.model.Info;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public interface Infoservice {
    int insertSelective(Info record);

    int insertOrupdate(Info record);

    ArrayList<Info> selectAll();
}
