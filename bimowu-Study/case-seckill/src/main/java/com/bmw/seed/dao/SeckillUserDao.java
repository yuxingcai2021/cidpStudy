package com.bmw.seed.dao;


import org.springframework.stereotype.Repository;

@Repository
public interface SeckillUserDao {
    /**
     * 根据用户id查询
     */
    int selectUserById(Long id);

}
