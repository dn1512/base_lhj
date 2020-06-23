package com.tj.lhj;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 应用启动器
 * @author liutengjun
 * @email dn1512@163.com
 * @date 2019-11-23 pm.22:58
 */
@SpringBootApplication
public class LhjApplication {
    private static final Logger logger = LoggerFactory.getLogger(LhjApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(LhjApplication.class, args);
    }
}
