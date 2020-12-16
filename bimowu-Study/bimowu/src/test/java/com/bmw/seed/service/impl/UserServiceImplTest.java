package com.bmw.seed.service.impl;


import com.bmw.seed.SeedApplication;
import com.bmw.seed.service.UserService;
import com.bmw.seed.util.HttpClientUtil;
import com.bmw.seed.util.bean.RegistReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest(classes = SeedApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    /**
     * 使用数据库悲观锁
     * @throws InterruptedException
     */
    @Test
    public void regist() throws InterruptedException {
        ExecutorService fixedTheadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i <5 ; i++) {
            fixedTheadPool.execute(()->{
                RegistReq req=new RegistReq();
                req.setPhone("130000000");
                req.setPassword("12314q");
                Long id = userService.regist(req);
                log.info(Thread.currentThread().getName()+"创建了一个线程执行："+id);
            }
            );
        }
        //守护线程停止后。子线程就不再运行了，在项目正常启动中，不需要添加
        Thread.sleep(10000);
    }

    /**
     * 使用unique索引的方式
     * @throws InterruptedException
     */
    @Test
    public void testUniqueRegister() throws InterruptedException {
        //使用单个线程池
        ExecutorService es =Executors.newFixedThreadPool(2);
        Runnable task=()->{
            RegistReq req=new RegistReq();
            req.setPhone("111234343");
            req.setPassword("1237743");

            Long id = userService.uniqueRegister(req);
            log.info(Thread.currentThread().getName()+"创建了一个线程执行："+id);
        };
        //使用submit传递线程任务，开启线程
        es.submit(task);
        es.submit(task);

        //守护线程停止后。子线程就不再运行了，在项目正常启动中，不需要添加
        Thread.sleep(10000);
    }


    @Autowired
    HttpClientUtil httpClientUtil;

    @Test
    public void test()  {

        try {
            String str = httpClientUtil.doGet("http://www.baidu.com");
            System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
