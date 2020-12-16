package com.bmw.seed.service.impl;


import com.bmw.seed.dao.UserDao;
import com.bmw.seed.model.User;
import com.bmw.seed.service.UserService;
import com.bmw.seed.util.bean.RegistReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 最初的实现方式
     */
    @Autowired
    private UserDao userDao;
    @Override
    public Boolean register(RegistReq req) {
        //设置入参实体参数
        User user=new User();
        user.setPhone(req.getPhone());
        user.setPassword(req.getPassword());
        int i = userDao.register(user);
        if (i>0)
            return true;
        else
            return false;
    }

    /**
     *        使用数据库悲观锁
     *添加事务，默认事务是RuntimeException回滚，其他不回滚，
     * 此处设置所有的Exception回滚，事务传播级别此处不设置，采用默认
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long regist(RegistReq req) {
        //悲观锁
        User user = userDao.fetchByPhoneForUpdate(req.getPhone());
        if(user!=null){
            //手机号已经被注册
            log.info("===手机号已经被注册===");
            return user.getId();
        }else{
            Date date=new Date();
            log.info("now time"+date.getTime());
            User u=new User();
            BeanUtils.copyProperties(req,u);//复制同名属性相当于u.setPhone(req.getPhone)....
            u.setPassword(DigestUtils.md5Hex(u.getPassword()));
            u.setCreateTime(date);
            u.setUpdateTime(date);

            userDao.register(u);
            return u.getId();
        }
    }

    /**
     *unique索引
     *直接插入数据即可。
     * 如果有数据的话，会报错，根据报错信息中包含Duplicate entry就可以判读已经有别的线程插入数据了
     * 此时，读取就行，如果是其他错误的话抛出就行，unique索引比悲观锁性能好，因为没有事务
     */
    @Override
    public Long uniqueRegister(RegistReq req) {
        //unique不需要事务，性能比悲观锁好
        Date date= new Date();
        User user=new User();
        //使用工具类复制同名属性
        BeanUtils.copyProperties(req,user);
        //密码加密
        user.setPassword(DigestUtils.md5Hex(req.getPassword()));
        user.setUpdateTime(date);
        user.setCreateTime(date);
        try{
            userDao.register(user);
        }catch (Exception e){
            //判断信息中是否包含Duplicate entry
            if(e.getMessage().indexOf("Duplicate entry")>=0){
                User u = userDao.getByPhone(req.getPhone());
                return u.getId();
            }else{
                log.info(e.getMessage(),e);
                throw new  RuntimeException(e.getMessage());
            }

        }
        //？？？？
        return user.getId();
    }
}
