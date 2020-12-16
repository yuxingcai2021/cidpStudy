package com.bmw.seed.controller;

import com.alibaba.fastjson.JSONArray;
import com.bmw.seed.service.RankStaticService;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.RankResp;
import com.bmw.seed.util.redis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "rank")
public class RankController {


    /**
     * 静态排行榜
     */
    @Autowired
    private RankStaticService rankStaticService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/list")
    public BaseResponse<List<RankResp>> list(){
        return BaseResponse.OK(rankStaticService.getData());
    }


    /**
     * 非实时排行榜，实现方式定时任务+临时表了(缓存)，定时将数据查询放到缓存，在从里面取出数据
     */
    @RequestMapping("/list1")
    public BaseResponse<List<RankResp>> list2(){
        //取出缓存中的数据
        String result = String.valueOf(redisUtil.get("rankList"));
        List<RankResp> list=new ArrayList<>();
        if(StringUtils.isNotBlank(result)){
           list=JSONArray.parseArray(result,RankResp.class);
        }
        return BaseResponse.ok(list);
    }
}
