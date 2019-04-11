package com.wildcardenter.myfab.schoolbuslocation.models;

import java.io.Serializable;

public class UserDetail implements Serializable {
    private String email;
    private String Id;
    private String name;
    private boolean isDriver;
    private String busNo;
    private String imgUrl;

    public UserDetail(String email, String id, String name, boolean isDriver, String busNo,String imgUrl) {
        this.email = email;
        Id = id;
        this.name = name;
        this.isDriver = isDriver;
        this.busNo = busNo;
        this.imgUrl=imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UserDetail() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }
}
