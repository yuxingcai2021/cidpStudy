package com.bmw.seed.scheduled;


import com.alibaba.druid.support.profile.ProfileEntryKey;
import com.alibaba.fastjson.JSON;
import com.bmw.seed.service.RankStaticService;
import com.bmw.seed.util.bean.RankResp;
import com.bmw.seed.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务
 */

@Component
@Slf4j
public class RankRefreshJob {

    @Autowired
    private RankStaticService rankStaticService;

    @Autowired
    private RedisUtil redisUtil;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void run(){
        log.info("====开始定时更新排行榜任务====");
        //查询数据库中的数据
        List<RankResp> data = rankStaticService.getData();
        //将其放到redis缓存中
        redisUtil.set("rankList", JSON.toJSONString(data));
        log.info("===结束定时更新排行榜任务");

    }
}
