package com.git;

import com.git.toolbox.ServerMain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ServerMain.class)
//@WebAppConfiguration
//@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerMain.class)
@WebAppConfiguration

public class BaseTest {

	Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	protected WebApplicationContext context;

	protected MockMvc mockMvc;

	@Before
	public void before() {
		System.out.println("Before test.");
		logger.debug("构造mvcMock");
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	// @Rollback(true) 若是写方法，加此注解进行回滚
	public void empty() {
		System.out.println("test empty");

	}

}
