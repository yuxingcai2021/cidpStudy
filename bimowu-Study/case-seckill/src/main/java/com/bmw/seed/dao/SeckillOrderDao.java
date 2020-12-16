package com.bmw.seed.dao;

import com.bmw.seed.model.SeckillOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeckillOrderDao {

    //创建订单
    int insert(SeckillOrder seckillOrder);

    List<SeckillOrder> list(SeckillOrder seckillOrder);

}
