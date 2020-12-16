package com.bmw.seed.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmw.seed.model.Demo;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.PageReq;
import com.bmw.seed.util.bean.PageResp;

@Controller
@RequestMapping(value = "/mock")
@Slf4j
public class MockController {
	
	/**
	 * 模拟登录.
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, String url) {
		if (StringUtils.isNotEmpty(url)) {
			request.setAttribute("url", url);
		}
		request.getSession().setAttribute("user", "user");
		
		return "/loginsuccess";
	}
	
}
