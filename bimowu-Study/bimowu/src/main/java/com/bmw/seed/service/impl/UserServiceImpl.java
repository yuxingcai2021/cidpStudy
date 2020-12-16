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
     * �����ʵ�ַ�ʽ
     */
    @Autowired
    private UserDao userDao;
    @Override
    public Boolean register(RegistReq req) {
        //�������ʵ�����
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
     *        ʹ�����ݿⱯ����
     *�������Ĭ��������RuntimeException�ع����������ع���
     * �˴��������е�Exception�ع������񴫲�����˴������ã�����Ĭ��
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long regist(RegistReq req) {
        //������
        User user = userDao.fetchByPhoneForUpdate(req.getPhone());
        if(user!=null){
            //�ֻ����Ѿ���ע��
            log.info("===�ֻ����Ѿ���ע��===");
            return user.getId();
        }else{
            Date date=new Date();
            log.info("now time"+date.getTime());
            User u=new User();
            BeanUtils.copyProperties(req,u);//����ͬ�������൱��u.setPhone(req.getPhone)....
            u.setPassword(DigestUtils.md5Hex(u.getPassword()));
            u.setCreateTime(date);
            u.setUpdateTime(date);

            userDao.register(u);
            return u.getId();
        }
    }

    /**
     *unique����
     *ֱ�Ӳ������ݼ��ɡ�
     * ��������ݵĻ����ᱨ�����ݱ�����Ϣ�а���Duplicate entry�Ϳ����ж��Ѿ��б���̲߳���������
     * ��ʱ����ȡ���У��������������Ļ��׳����У�unique�����ȱ��������ܺã���Ϊû������
     */
    @Override
    public Long uniqueRegister(RegistReq req) {
        //unique����Ҫ�������ܱȱ�������
        Date date= new Date();
        User user=new User();
        //ʹ�ù����ิ��ͬ������
        BeanUtils.copyProperties(req,user);
        //�������
        user.setPassword(DigestUtils.md5Hex(req.getPassword()));
        user.setUpdateTime(date);
        user.setCreateTime(date);
        try{
            userDao.register(user);
        }catch (Exception e){
            //�ж���Ϣ���Ƿ����Duplicate entry
            if(e.getMessage().indexOf("Duplicate entry")>=0){
                User u = userDao.getByPhone(req.getPhone());
                return u.getId();
            }else{
                log.info(e.getMessage(),e);
                throw new  RuntimeException(e.getMessage());
            }

        }
        //��������
        return user.getId();
    }
}
