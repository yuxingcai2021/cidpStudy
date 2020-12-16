package com.bmw.seed.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmw.seed.util.bean.BaseResponse;

@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {
	
	/**
	 * 模拟success成功页.
	 */
	@RequestMapping(value = "/success")
	public BaseResponse<Map<String, Object>> success(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "sucess");
		map.put("data", "is success!");
		return BaseResponse.OK(map);
	}
	
	/**
	 * 模拟登录成功后的data页.
	 */
	@RequestMapping(value="/data")
	public BaseResponse<Map<String, Object>> data(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "data");
		map.put("data", "is data!");
		return BaseResponse.OK(map);
	}
}
