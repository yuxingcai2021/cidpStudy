package com.bmw.seed.controller;

import com.bmw.seed.service.UserService;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.RegistReq;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;//£¿£¿£¿£¿

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping("/register")
    public BaseResponse<Boolean> register( @Valid @RequestBody RegistReq req){
        return BaseResponse.OK(userService.register(req));

    }
}
