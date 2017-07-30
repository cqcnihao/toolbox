package com.git.toolbox.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

//import tk.mybatis.spring.mapper.MapperScannerConfigurer;


/**
 * <p>Title: MyBatisMapperScannerConfig</p>
 * <p>Description:  MyBatis扫描接口</p>
 */
@Configuration
@MapperScan(basePackages = "com.git.toolbox.dao.mapper")
public class MyBatisMapperScannerConfig {

}
