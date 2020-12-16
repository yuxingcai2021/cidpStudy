package com.bmw.seed.service;

import com.bmw.seed.model.UserInfo;
import com.bmw.seed.util.bean.CursorPageReq;
import com.bmw.seed.util.bean.CursorPageResp;

public interface UserInfoService {
    //表迁移
    Boolean transfer();

    Boolean transferExpand();

    //游标分页
    CursorPageResp<UserInfo> cursorPage(CursorPageReq req);

}
