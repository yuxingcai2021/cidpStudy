package com.bmw.seed.service.impl;

import com.bmw.seed.dao.SeckillOrderDao;
import com.bmw.seed.dao.SeckillProductDao;
import com.bmw.seed.dao.SeckillUserDao;
import com.bmw.seed.model.SeckillOrder;
import com.bmw.seed.model.SeckillProducts;
import com.bmw.seed.service.SeckillService;
import com.bmw.seed.util.ErrorMessage;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.SeckillReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillUserDao userDao;

    @Autowired
    private SeckillProductDao productDao;

    @Autowired
    private SeckillOrderDao orderDao;

    /**
     *  原始秒杀，高并发情况下会出现问题，
     *  数据库中取出销售数量后，再对数据库进行update
     *  并发情况下，校验的过程中，n个线程可能已经对saled值进行修改，此时数据库取出的saled的值还是原来，再进行+1操作
     *  就相当于吧脏数据写回到了数据库
     *
     *
     * 解决方法：加锁，使用数据库悲观锁的话，效率不高，悲观锁是行锁+间隙锁，会对数据库每一行上锁，在高并发情况下就会影响效率
     *  使用数据乐观锁，就是使用代码逻辑来上锁
     *
     * @param req
     * @return
     */
    @Override
    public BaseResponse<Boolean> oOrder(SeckillReq req) {
        log.info("===开始调用秒杀接口");
        //验证参数
        log.info("======验证参数=======");
        BaseResponse paramValidRes = validateParam(req.getProductId(), req.getUserId());
        if(paramValidRes.getCode()!=0){
            return paramValidRes;
        }
        log.info("===校验数据是否合法通过===");

        //校验库存是否足够
        log.info("====校验 库存是否足够===");
        SeckillProducts products = productDao.fetch(req.getProductId());
        if(products.getSaled()>=products.getCount()){
            log.info("===库存不足===");
            return BaseResponse.error(ErrorMessage.STOCK_NOT_ENOUGH);
        }
        log.info("===校验库存是否充足通过====");

        /**
         * 用户重复下单验证
         */
        SeckillOrder param =new SeckillOrder();
        param.setProductId(req.getProductId());
        param.setUserId(req.getUserId());
        List<SeckillOrder> list = orderDao.list(param);
        if(!list.isEmpty()){
            log.info("===用户重复下单===");
            return BaseResponse.ok(ErrorMessage.REPEAT_ORDER_ERROR);
        }
        log.info("==校验  用户并未从未下单==");


        //使用数据库乐观锁解决并发下
       return createOptimisticOrder(req.getProductId(),req.getUserId());
       /* 在此处做出优化，在数据库语句上判断库存是否大于saled值
       Date date=new Date();
        //扣减库存
        log.info("===开始扣减库存===");
        products.setSaled(products.getSaled()+1);//让销售量加1
        productDao.updateByPrimaryKeySelective(products);//修改数据库中的信息
        log.info("===修改库存成功===");

        //创建订单
        SeckillOrder order = new SeckillOrder();
        order.setProductId(products.getId());
        order.setProductName(products.getName());
        order.setUserId(req.getUserId());
        order.setCreateTime(date);
        //插入数据到数据库
        orderDao.insert(order);
        log.info("===创建订单成功===");
        return BaseResponse.ok(Boolean.TRUE);*/
    }

    //验证参数
    public BaseResponse validateParam(Long ProId,Long UserId ){
        //检验参数是为空
        if(ProId!=null&&UserId!=null){
            //检验参数是否存在数据
            if(userDao.selectUserById(UserId)>0&&productDao.selectProById(ProId)>0){
                return BaseResponse.ok(new BaseResponse<>(0,"成功","null"));
            }
        }
        return BaseResponse.ok(new BaseResponse<>(1,"成功","null"));

    }

    //使用数据库乐观锁
    private BaseResponse createOptimisticOrder(Long productId,Long userId){
        log.info("===下单逻辑starting===");

        //扣减库存
        int res = productDao.updateStockByOptimistic(productId);
        if(res==0){
            return BaseResponse.ok(ErrorMessage.SECKILL_FAILED);
        }
        log.info("===扣减库存成功===");
        //创建订单
        SeckillProducts products = productDao.fetch(productId);
        Date date = new Date();
        SeckillOrder order = new SeckillOrder();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setCreateTime(date);
        order.setProductName(products.getName());
        orderDao.insert(order);
        log.info("===创建订单成功===");
        return BaseResponse.ok(true);
    }
}
