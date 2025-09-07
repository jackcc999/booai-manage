package org.jeecg.mybatis.plugin.domain;

public class SqlInfo
{
    private String id;
    private String sql;
    private Long execTime;

    public SqlInfo() {
    }

    public SqlInfo(String id, String sql, long execTime) {
        this.id = id;
        this.sql = sql;
        this.execTime = execTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Long getExecTime() {
        return this.execTime;
    }

    public void setExecTime(Long execTime) {
        this.execTime = execTime;
    }

    public String toString() {
        return "{\"id\":\"" + this.id + "\",\"sql\":\"" + this.sql + "\",\"execTime\":\"" + this.execTime + "ms\"}";
    }
}
