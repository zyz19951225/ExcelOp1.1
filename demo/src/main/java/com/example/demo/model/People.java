package com.example.demo.model;

import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class People {
    private Integer id;

    private String str;

    private Boolean sex;

    private Double dou;

    private Long big;

    private String cha;

    private Byte bye;

    private Date da;

    private Date tim;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str == null ? null : str.trim();
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Double getDou() {
        return dou;
    }

    public void setDou(Double dou) {
        this.dou = dou;
    }

    public Long getBig() {
        return big;
    }

    public void setBig(Long big) {
        this.big = big;
    }

    public String getCha() {
        return cha;
    }

    public void setCha(String cha) {
        this.cha = cha == null ? null : cha.trim();
    }

    public Byte getBye() {
        return bye;
    }

    public void setBye(Byte bye) {
        this.bye = bye;
    }

    public Date getDa() {
        return da;
    }

    public void setDa(Date da) {
        this.da = da;
    }

    public Date getTim() {
        return tim;
    }

    public void setTim(Date tim) {
        this.tim = tim;
    }
}