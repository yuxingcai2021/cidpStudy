package com.bmw.seed.controller;

import com.bmw.seed.service.RankStaticService;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.RankResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "rank")
public class RankController {

    @Autowired
    private RankStaticService rankStaticService;

    @RequestMapping(value = "/list")
    public BaseResponse<List<RankResp>> list(){
        return BaseResponse.OK(rankStaticService.getData());
    }
}
