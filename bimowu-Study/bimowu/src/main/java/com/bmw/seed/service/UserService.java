package com.bmw.seed.service;


import com.bmw.seed.model.User;
import com.bmw.seed.util.bean.RegistReq;

public interface UserService {
    Boolean register(RegistReq req);

    Long regist(RegistReq req);

    Long uniqueRegister(RegistReq req);

}
