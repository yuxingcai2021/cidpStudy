package com.bmw.seed.dao;

import com.bmw.seed.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    //зЂВс
    int register(User user);

    User fetchByPhoneForUpdate(String phone);

    User getByPhone(String phone);
}
