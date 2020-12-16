package com.bmw.seed.service.impl;

import com.bmw.seed.dao.DemoDao;
import com.bmw.seed.model.Demo;
import com.bmw.seed.service.DemoService;
import com.bmw.seed.util.bean.CommonQueryBean;
import com.bmw.seed.util.bean.PageReq;
import com.bmw.seed.util.bean.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class DemoServiceImpl implements DemoService {

	@Autowired
	private DemoDao demoDao;

	/**
	 * 计算总页数
	 * @param count 总记录条数
	 * @param pageSize 每页数量
	 * @return
	 */
	public static int getPageCount(int count, int pageSize) {
		if (pageSize == 0) {
			return 0;
		} else {
			return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		}
	}

	@Override
	public PageResp<Demo> page(PageReq req) {
		//第几条数据开始读取
		int start = (req.getPage() - 1) * req.getPageSize();
		Demo param = new Demo();
		//通用分页插叙bean  起始条数、每页条数（limit）、排序、sort？？？
		CommonQueryBean queryBean = new CommonQueryBean();
		queryBean.setStart(start);//第几条数据开始读取
		queryBean.setPageSize(req.getPageSize());//每页多少条数据
		//物理分页
		List<Demo> list = demoDao.list4Page(param, queryBean);//param是实体demo，queryBean是分页bean
		int count = demoDao.count(param);//查询总条数
		PageResp pageResp = new PageResp();//封装响应实体
		pageResp.setList(list);
		pageResp.setPageNum(getPageCount(start + 1, req.getPageSize()));//当前页数
		pageResp.setPageSize(req.getPageSize());//页数大小
		pageResp.setTotalNum(count);//总记录条数
		pageResp.setTotalPage(getPageCount(count, req.getPageSize()));//总页数
		return pageResp;
	}

}
