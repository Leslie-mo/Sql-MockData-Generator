package com.leslie.sqlGenerator.coreMethon.builder.sql;

/**
 * SQL dialect
 */
public interface SQLDialect {

    /**
     * wrap field name
     *
     * @param name
     * @return
     */
    String wrapFieldName(String name);

    /**
     * parse field name
     *
     * @param fieldName
     * @return
     */
    String parseFieldName(String fieldName);

    /**
     * wrap table name
     *
     * @param name
     * @return
     */
    String wrapTableName(String name);

    /**
     * parse table name
     *
     * @param tableName
     * @return
     */
    String parseTableName(String tableName);
}
