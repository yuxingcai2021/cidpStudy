package com.bmw.seed.controller;

import com.bmw.seed.model.Demo;
import com.bmw.seed.model.UserInfo;
import com.bmw.seed.service.DemoService;
import com.bmw.seed.service.UserInfoService;
import com.bmw.seed.util.bean.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/demo")
@Slf4j
public class DemoController {

	@Autowired
	DemoService demoService;



	/**
	 * 分页 demo
	 */
	@RequestMapping(value = "/page")
	//此处要使用post提交json数据 因为@RequestBody
	public BaseResponse<PageResp<Demo>> page( @Valid @RequestBody PageReq req) {
		System.out.println("请求进来了");
		return BaseResponse.OK(demoService.page(req));
	}


}
