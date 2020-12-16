package com.bmw.seed.dao;


import com.bmw.seed.model.SeckillProducts;
import org.springframework.stereotype.Repository;

@Repository
public interface SeckillProductDao {
    /**
     * 根据id查询商品信息
     */
    int selectProById(Long id);

    //根据id查询productId信息
    SeckillProducts fetch(Long productId);

    int updateByPrimaryKeySelective(SeckillProducts products);

    int updateStockByOptimistic(Long productId);
}
