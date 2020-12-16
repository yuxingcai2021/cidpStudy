package com.bmw.seed.service.impl;

import com.bmw.seed.model.Demo;
import com.bmw.seed.service.DemoService;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.PageReq;


import com.bmw.seed.util.bean.PageResp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceImplTest {

    @Autowired
    DemoService demoService;
    @Test
   public void page() {
        PageReq req =new PageReq();
        req.setPage(1);
        req.setPageSize(10);
        PageResp<Demo> page = demoService.page(req);
        PageResp<Demo> data = BaseResponse.OK(page).getData();
        List<Demo> list = data.getList();
        for (Demo demo : list) {
            System.out.println(demo);
        }

    }

}