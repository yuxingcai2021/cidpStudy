package com.bmw.seed.service.impl;

import com.bmw.seed.dao.RankDynamic;
import com.bmw.seed.service.RankDynamicService;
import com.bmw.seed.util.bean.RankResp;
import com.bmw.seed.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class RankDynamicServiceImpl implements RankDynamicService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RankDynamic rankDynamic;


    //查询数据库
    public List<RankResp> list3(){
        //取出数据库数据库
        List<RankResp> data = rankDynamic.getData();
        //存入缓存
//        redisUtil.set("rankList",data);//????
        for (RankResp datum : data) {
            record(datum.getPhone(),datum.getAmount());
        }

        //读取缓存中的数据
        List<Map<String, Object>> list = searchRank();

        //返回数据
        List<RankResp> res = new ArrayList<>();

        for (Map<String, Object> map : list) {
            //返回实体
            RankResp rankResp = new RankResp();
            rankResp.setRank((Integer) map.get("rank"));
            rankResp.setPhone((String) map.get("phone"));
            rankResp.setAmount((Integer) map.get("amount"));
            res.add(rankResp);
        }
        return res;

    }


    //修改数据库中信息
    public Double record(String phone,Integer amount){
        try{
            //再更新redis
           return redisUtil.zincrby("RankDynamic",phone,amount);
        }catch (Exception e){
            log.error("插入数据是错误",e);
        }
        return 0D;
    }

    public List<Map<String ,Object>> searchRank(){
        List<Map<String ,Object>> list =new ArrayList<>();
        try{
            Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zrevrangeByScoreWithScores("RankDynamic", 0D, 1000000D);
            int i=0;
            for (ZSetOperations.TypedTuple<Object> t : set) {
                Map<String,Object> map=new HashMap<>();
                map.put("rank",i);
                map.put("phone",String.valueOf(t.getValue()));
                map.put("amount",t.getScore().intValue());
                list.add(map);
                i++;
            }
        }catch (Exception e){
            log.error("获取缓存中的数据出现异常",e);
        }
    return list;
    }
}