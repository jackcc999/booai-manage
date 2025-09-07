package org.jeecg.mybatis.plugin;

import cn.hutool.core.date.DateUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.jeecg.mybatis.plugin.domain.SqlInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @author ZHOUGANG63
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
    }
)
public class SqlInterceptor implements Interceptor
{
    private final static Logger logger = LoggerFactory.getLogger(SqlInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs().length > 1 ? invocation.getArgs()[1] : null;

        Configuration configuration = mappedStatement.getConfiguration();

        long start = System.currentTimeMillis();
        Object returnValue = invocation.proceed();
        long time = System.currentTimeMillis() - start;

        String statementId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        buildFullSql(configuration, statementId, boundSql, time);
        return returnValue;
    }

    private void buildFullSql(Configuration configuration, String statementId, BoundSql boundSql, long time)
    {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        // 替换空格、换行、tab缩进等
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if(parameterMappings.size() > 0 && parameterObject != null)
        {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if(typeHandlerRegistry.hasTypeHandler(parameterObject.getClass()))
            {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            }
            else
            {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for(ParameterMapping parameterMapping : parameterMappings)
                {
                    String propertyName = parameterMapping.getProperty();
                    if(metaObject.hasGetter(propertyName))
                    {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                    else if(boundSql.hasAdditionalParameter(propertyName))
                    {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        logger.info("{}", new SqlInfo(statementId, sql, time));
    }

    private String getParameterValue(Object obj)
    {
        String value;
        if(obj instanceof String)
        {
            value = "'" + obj.toString() + "'";
        }
        else if(obj instanceof Date)
        {
            value = "'" + DateUtil.format((Date) obj, "yyyy-MM-dd HH:mm:ss") + "'";
        }
        else
        {
            value = obj == null ? "NULL" : obj.toString();
        }
        return value.replace("$", "\\$");
    }
}
