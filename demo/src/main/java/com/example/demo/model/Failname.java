package com.example.demo.model;


import org.springframework.stereotype.Component;

@Component
public class Failname {

    int num;

    String filename;



    public Failname() {

    }

    public Failname(int num,String filename){
        this.num = num;
        this.filename = filename;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
