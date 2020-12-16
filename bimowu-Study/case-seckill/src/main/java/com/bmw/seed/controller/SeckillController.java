package com.bmw.seed.controller;

import com.bmw.seed.service.SeckillService;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.SeckillReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/order")
    public BaseResponse<Boolean> order(@Valid @RequestBody SeckillReq req){
        return seckillService.oOrder(req);
    }

}
