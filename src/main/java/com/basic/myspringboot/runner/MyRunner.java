package com.basic.myspringboot.runner;

import com.basic.myspringboot.config.CustomVO;
import com.basic.myspringboot.property.MybootProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements ApplicationRunner {
    @Value("${myboot.name}")
    private String name;
    @Value("${myboot.age}")
    private int age;

    @Autowired
    Environment env;

    @Autowired
    MybootProperties properties;

    @Autowired
    CustomVO customVO;

    private Logger logger = LoggerFactory.getLogger(MyRunner.class);

    public void run(ApplicationArguments args) throws Exception {

        logger.info("현재 로거 구현체 클래스명 {}", logger.getClass().getName());

        logger.info("현재 활성화 된 CustomVO {}", customVO);
        logger.info("@Value myboot.name {}", name);
        logger.info("@Value myboot.age {}", age);
        logger.info("Environment myboot.fullName {}", env.getProperty("myboot.fullName"));
        logger.info("Environment server.port {}", env.getProperty("local.server.port"));

        logger.debug("MybootProperties getFullName = {}", properties.getFullName());
        logger.debug("VM Arguments foo : {}", args.containsOption("foo"));
        logger.debug("Program Arguments bar : {}", args.containsOption("bar"));

        //환경변수 이름 출력
        args.getOptionNames()
                .forEach(name -> System.out.println(name));


    }

}
