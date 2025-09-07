package org.jeecg.configurer;

import org.apache.ibatis.session.SqlSessionFactory;
import org.jeecg.mybatis.plugin.SqlInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfigurer
{
    @Bean
    public Boolean sqlInterceptor(SqlSessionFactory sqlSessionFactory)
    {
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.addInterceptor(new SqlInterceptor());
        return Boolean.TRUE;
    }
}
