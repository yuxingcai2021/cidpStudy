package com.bmw.seed.service;

import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.SeckillReq;

public interface SeckillService {

    BaseResponse<Boolean> oOrder(SeckillReq req);
}
