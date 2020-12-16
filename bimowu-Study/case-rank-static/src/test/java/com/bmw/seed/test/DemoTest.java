package com.bmw.seed.test;

import com.bmw.seed.SeedApplication;
import com.bmw.seed.dao.RankStaticDao;
import com.bmw.seed.service.DemoService;
import com.bmw.seed.service.RankStaticService;
import com.bmw.seed.util.bean.RankResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SeedApplication.class)
@Slf4j
public class DemoTest {

	@Autowired
	private DemoService demoService;

	@Autowired
	private RankStaticService rankStaticService;

	@Test
	public void test() {
		List<RankResp> data = rankStaticService.getData();
		for (RankResp datum : data) {
			System.out.println(datum);
		}

	}
}
