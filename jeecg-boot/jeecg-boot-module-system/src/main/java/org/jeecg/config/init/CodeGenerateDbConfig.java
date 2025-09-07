package org.jeecg.config.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.codegenerate.database.CodegenDatasourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 代码生成器,自定义DB配置
 * 【加了此类，则online模式DB连接，使用平台的配置，jeecg_database.properties配置无效;
 *  但是使用GUI模式代码生成，还是走jeecg_database.properties配置】
 *  提醒： 达梦数据库需要修改下面的参数${spring.datasource.url:}配置
 * @author: scott
 * @date: 2021年02月18日 16:30
 */
@Slf4j
@Configuration
public class CodeGenerateDbConfig
{
    @Value("${spring.datasource.url:}")
    private String url;
    @Value("${spring.datasource.username:}")
    private String username;
    @Value("${spring.datasource.password:}")
    private String password;
    @Value("${spring.datasource.driver-class-name:}")
    private String driverClassName;



    @Bean
    public CodeGenerateDbConfig initCodeGenerateDbConfig() {
        if(StringUtils.isNotBlank(url)){
            CodegenDatasourceConfig.initDbConfig(driverClassName,url, username, password);
            log.info(" Init CodeGenerate Config [ Get Db Config From application.yml ] ");
        }
        return null;
    }
}
