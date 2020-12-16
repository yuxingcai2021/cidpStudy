package com.bmw.seed.service.impl;

import com.bmw.seed.dao.RankStaticDao;
import com.bmw.seed.service.RankStaticService;
import com.bmw.seed.util.bean.RankResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RankStaitcServiceImpl implements RankStaticService {

    @Autowired
    private RankStaticDao rankStaticDao;
    @Override
    public List<RankResp> getData() {
        int i=0;
        List<RankResp> rankStatic = rankStaticDao.getRankStatic();
        for (RankResp rankResp : rankStatic) {
            rankResp.setRank(i);
            i++;
        }
        return rankStatic;
    }
}
