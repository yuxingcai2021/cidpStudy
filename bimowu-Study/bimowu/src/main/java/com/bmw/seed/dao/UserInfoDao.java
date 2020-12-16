package com.bmw.seed.dao;


import com.bmw.seed.model.Demo;
import com.bmw.seed.model.UserInfo;
import com.bmw.seed.util.bean.CommonQueryBean;
import com.bmw.seed.util.bean.CursorPageReq;
import com.bmw.seed.util.bean.PageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoDao {
        //表迁移
     void transfer();

     //查询总条数，
     int countTotal();

    //查询被插入表的数据条数
    int countTotalBak();

    //游标分页
    List<UserInfo> cursorPage(@Param("req") CursorPageReq req);
}
