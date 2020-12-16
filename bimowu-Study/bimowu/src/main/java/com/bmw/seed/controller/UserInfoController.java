package com.bmw.seed.controller;



import com.bmw.seed.model.UserInfo;
import com.bmw.seed.service.UserInfoService;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.CursorPageReq;
import com.bmw.seed.util.bean.CursorPageResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/transfer")
    public BaseResponse<Boolean> transfer(){
        return BaseResponse.ok(userInfoService.transferExpand());
    }


    /**
     * 基于游标分页
     */

    @RequestMapping(value = "cursor/page")
    public BaseResponse<CursorPageResp<UserInfo>>cursorPage(@Valid @RequestBody CursorPageReq req){
        return BaseResponse.OK(userInfoService.cursorPage(req));
    }


}
