package org.jeecg;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
* 单体启动类
* 报错提醒: 未集成mongo报错，可以打开启动类上面的注释 exclude={MongoAutoConfiguration.class}
*/
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@ComponentScan({"org.jeecg", "org.wg.flowable", "com.financial.manage"})
@MapperScan({"org.jeecg.modules.**.mapper", "org.wg.flowable.**.mapper", "com.financial.manage.mapper"})
@ServletComponentScan("cn.com.dgwl.tools.v2.filter")
@EnableScheduling
@EnableAsync
public class JeecgSystemApplication
{
    private final static Logger logger = LoggerFactory.getLogger(JeecgSystemApplication.class);

    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(JeecgSystemApplication.class, args);

        Environment env = context.getEnvironment();
        String ip = "127.0.0.1";
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        logger.info("\n----------------------------------------------------------\n\t" +
                "Application Jeecg-Boot is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
