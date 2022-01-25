package com.example.mvplibrary.utils;

/*
TODO com.numberone.cashone.utils 
TODO Time: 2021/11/24 17:00 
TODO Name: zhang
*/

public class DbBean {
    private String name;//联系人姓名
    private String telPhone;//联系人电话号码

    public DbBean(String name, String telPhone) {
        this.name = name;
        this.telPhone = telPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    @Override
    public String toString() {
        return "ZhangsBean{" +
                "name='" + name + '\'' +
                ", telPhone='" + telPhone + '\'' +
                '}';
    }
}
