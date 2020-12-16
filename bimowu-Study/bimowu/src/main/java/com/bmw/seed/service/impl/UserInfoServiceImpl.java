package com.bmw.seed.service.impl;

import com.bmw.seed.dao.UserInfoDao;
import com.bmw.seed.model.UserInfo;
import com.bmw.seed.service.UserInfoService;
import com.bmw.seed.util.bean.CommonQueryBean;
import com.bmw.seed.util.bean.CursorPageReq;
import com.bmw.seed.util.bean.CursorPageResp;
import com.bmw.seed.util.bean.PageReq;
import com.bmw.seed.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private RedisUtil redisUtil;


   /* @Override
    public Boolean transfer() {
        int bak = userInfoDao.countTotalBak();
        log.info("迁移前，user_info_bak表中数据为:"+bak);
        log.info("迁移前，user_info表中数据0条");
        log.info("开始执行transfer方法");
        userInfoDao.transfer();
        int total = userInfoDao.countTotal();
       log.info("迁移之后user_info中的数据是"+total);
        if (total>0)
            return true;
        else
            return false;

    }*/
   @Override
   public Boolean transfer() {
       int before = userInfoDao.countTotal();
       int bak = userInfoDao.countTotalBak();
       log.info("迁移前，user_info表中数据为:"+before);
       log.info("迁移前，user_info_bak表中数据为:"+bak);
       if(before>0) {
            log.info("数据已经存在说明已经执行过表迁移，业务逻辑校验失败");
           return false;
       }
       log.info("开始执行transfer方法");
       userInfoDao.transfer();
       int afterTotal = userInfoDao.countTotal();
       log.info("迁移完成之后user_info中的数据是"+afterTotal);
       if (afterTotal>0)
           return true;
       else
           return false;

   }

    public Boolean transferExpand(){
        log.info("开始执行调用幂等性校验的表迁移接口-----");
        //根据业务，生成唯一标识token
        String token = "transfer";
        if(!redisUtil.setIfAbsent(token,1)){
            //如果存在key
          log.error("重复执行了api，直接返回失败");
          return false;
        }else{
            try{
                //执行方法
                return transfer();
            }catch (Exception e){
                log.info("表迁移出现异常？？？");
                return false;
            }finally {
                //删除缓存
                redisUtil.del(token);
            }

        }

    }



    /**
     * 游标分页
     */
    @Override
    public CursorPageResp<UserInfo> cursorPage(CursorPageReq req) {
        //封装参数数据
       req.setCursor(req.getCursor()-(req.getPageSize()));
        //查询数据

        List<UserInfo> userInfos = userInfoDao.cursorPage(req);
//        log.info(userInfos.toString());
        //封装响应实体
        CursorPageResp cursorPageResp=new CursorPageResp();
        //设置游标
        cursorPageResp.setCursor(req.getCursor());
        cursorPageResp.setList(userInfos);
        return cursorPageResp;
    }

}
