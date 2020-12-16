package com.bmw.seed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrder {

    private Long id;
    private  Long ProductId;
    private Long  userId;
    private String ProductName;
    private Date createTime;

}
