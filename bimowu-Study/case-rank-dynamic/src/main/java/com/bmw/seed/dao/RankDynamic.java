package com.bmw.seed.dao;

import com.bmw.seed.model.Order;
import com.bmw.seed.util.bean.RankResp;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RankDynamic {
   List<RankResp> getData();

   int insert(Order order);
}
