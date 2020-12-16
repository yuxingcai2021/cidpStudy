package com.bmw.seed.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    //用户id
    private Integer id;
    //金额
    private BigDecimal amount;
}
