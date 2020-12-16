package com.bmw.seed.util.bean;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RegistReq implements Serializable {
    @NotBlank(message = "手机号 不能为空")
    private  String phone;
    @NotBlank(message = "密码 不能为空")
    private  String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
