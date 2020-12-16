package com.bmw.seed.dao;

import com.bmw.seed.util.bean.RankResp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankStaticDao {
    List<RankResp> getRankStatic();
}
