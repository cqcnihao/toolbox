package com.git.toolbox.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 
 *<p>Title: InitListener</p>
 *<p>Description: 增加系统启动时的配置信息</p>
 *@date 2017年2月22日 上午11:10:13
 */

public class AppInitListener implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent   event) {
		 
		 //防止重复执行。
        if(event.getApplicationContext().getParent() == null){
             
        }
	}
	
	 
		

 
}
