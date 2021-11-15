package com.okava.pay.utils.dtos;

import javax.validation.constraints.NotEmpty;

public class RegisterSchoolDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    private String location;

    @NotEmpty
    private String headMaster;

    @NotEmpty
    private String userName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHeadMaster() {
        return headMaster;
    }

    public void setHeadMaster(String headMaster) {
        this.headMaster = headMaster;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
