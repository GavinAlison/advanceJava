package com.alison.Utils.sqlutil;

public enum DbType {
    /**
     * 关系数据库
     */
    MYSQL("Mysql", "mysql", "`", "`"),
    /**
     * 内存数据库
     */
    IGNITE("Ignite", "ignite"),
    IGNITE_SQL("Ignite_Sql", "ignite_sql"),
    JDBC("Jdbc", "jdbc"),
    /**
     * kv
     */
    HBASE("Hbase", "hbase"),
    /**
     * kv 和关系数据
     */
    ELASTICSEARCH("Elasticsearch", "es"),
    /**
     * 接口数据
     */
    HTTP("Http", "http"),
    /**
     * Flink数据
     */
    FLINK("Flink", "flink"),
    /**
     * 外部接口数据
     */
    HTTP_PROXY("Http-Proxy", "ext-proxy"),
    /**
     * kv数据
     */
    REDIS("Redis", "redis"),
    GREENPLUM("Greenplum", "postgresql"),
    KAFKA("Kafka", "kafka"),
    /**
     * hive关系数据
     */
    HIVE("Hive", "hive"),
    BIG_DATA("Big-Data", "big-data"),
    MEMORY_STORAGE("Memory-Storage", "memory-storage");

    private String code;
    private String description;
    private String prefix;
    private String suffix;

    DbType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    DbType(String code, String description, String prefix, String suffix) {
        this.code = code;
        this.description = description;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
