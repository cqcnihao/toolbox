package com.git.toolbox.config.init;

import com.git.toolbox.config.CustomPropertiesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * 
 *<p>Title: InitListener</p>
 */
//@Component
public class AppDataInit implements CommandLineRunner {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	 
	@Autowired
	private CustomPropertiesConfig customPropertiesConfig;
//	@Autowired
//	private StringRedisTemplate stringRedisTemplate;
	
	 

	@Override
	public void run(String... args) throws Exception {
		logger.debug("项目启动后加载配置"); 
		
	}

 
}
