package com.example.demo.service.InfoServicePak;

import com.example.demo.mapper.InfoMapper;
import com.example.demo.model.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InfoServiceImpl implements Infoservice {

    @Autowired
    InfoMapper infoMapper;

    @Autowired
    Info info;


    @Override
    public int insertSelective(Info info) {
        int i = infoMapper.insertSelective(info);

        return i;
    }

    @Override
    public int insertOrupdate(Info record) {
        int i = infoMapper.insertOrupdate(info);
        return i;
    }

    @Override
    public ArrayList<Info> selectAll() {
        ArrayList<Info> allinfo = infoMapper.selectAll();
        info = allinfo.get(0);
        System.out.println(info.getName());
        System.out.println(allinfo.get(0).getName());
        //System.out.println(allinfo);
        return allinfo;
    }
}
