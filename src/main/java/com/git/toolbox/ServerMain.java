package com.git.toolbox;


import com.git.toolbox.config.listener.AppInitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@ServletComponentScan //以便支持@WebFilter注解
public class ServerMain {
    public static Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {

        logger.debug("classPath={}", Thread.currentThread().getContextClassLoader().getResource(""));


        SpringApplication springApplication = new SpringApplication(ServerMain.class);
        springApplication.addListeners(new AppInitListener());
        ConfigurableApplicationContext context = springApplication.run(args);
        context.registerShutdownHook();
    }

}
