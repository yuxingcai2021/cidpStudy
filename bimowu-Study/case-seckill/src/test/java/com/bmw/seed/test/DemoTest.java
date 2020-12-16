package com.bmw.seed.test;

import com.bmw.seed.SeedApplication;
import com.bmw.seed.service.SeckillService;
import com.bmw.seed.util.bean.SeckillReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SeedApplication.class)
@Slf4j
public class DemoTest {

    @Autowired
    private SeckillService seckillService;

    @Test
    public void test() throws InterruptedException {
        ExecutorService fixedTheadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            fixedTheadPool.execute(() -> {
                String s = Thread.currentThread().getName().substring(Thread.currentThread().getName().length()-1, Thread.currentThread().getName().length());
                SeckillReq req = new SeckillReq();
                req.setProductId(1L);
                req.setUserId(Long.parseLong(s));
                seckillService.oOrder(req);
                log.info(Thread.currentThread().getName() + "创建了一个线程执行：");
            }
            );
        }
        //守护线程停止后。子线程就不再运行了，在项目正常启动中，不需要添加
        Thread.sleep(10000);
    }
}
