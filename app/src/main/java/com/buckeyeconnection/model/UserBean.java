package com.buckeyeconnection.model;

/**
 * Created by yao.590 on 10/17/16.
 */

public class UserBean {
    private String username;
    private String password;
    private int gender; // 0 male; 1 female; 2 others
    private int nation; // ordered by countries array
    private int major;
    private String realName;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public int getNation() {
        return nation;
    }
    public void setNation(int nation) {
        this.nation = nation;
    }
    public int getMajor() {
        return major;
    }
    public void setMajor(int major) {
        this.major = major;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}

