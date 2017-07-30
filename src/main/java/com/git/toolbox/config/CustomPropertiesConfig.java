package com.git.toolbox.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 加载其他自定义的配置
 */
@Component
public class CustomPropertiesConfig {
    private Logger logger = LoggerFactory.getLogger(CustomPropertiesConfig.class);
    @Value("${password.algorithmName:md5}")
    private String algorithmName;
    @Value("${password.hashIterations:2}")
    private int hashIterations;

    @Value("${token.verify.switch:false}")
    private boolean tokenVerifySwitch;

    @Value("${accesstoken.ttl:30}")
    private int accessTokenTtl;


    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public boolean isTokenVerifySwitch() {
        return tokenVerifySwitch;
    }

    public void setTokenVerifySwitch(boolean tokenVerifySwitch) {
        this.tokenVerifySwitch = tokenVerifySwitch;
    }

    public int getAccessTokenTtl() {
        return accessTokenTtl;
    }

    public void setAccessTokenTtl(int accessTokenTtl) {
        this.accessTokenTtl = accessTokenTtl;
    }


}
