package com.leslie.sqlMockDataGenerator.coreMethod.builder.sql;

/**
 * MySQL dialect
 */
public class MySQLDialect implements SQLDialect {

    /**
     * wrap field name
     *
     * @param name
     * @return
     */
    @Override
    public String wrapFieldName(String name) {
        return String.format("`%s`", name);
    }

    /**
     * parse field name
     *
     * @param fieldName
     * @return
     */
    @Override
    public String parseFieldName(String fieldName) {
        if (fieldName.startsWith("`") && fieldName.endsWith("`")) {
            return fieldName.substring(1, fieldName.length() - 1);
        }
        return fieldName;
    }

    /**
     * wrap table name
     *
     * @param name
     * @return
     */
    @Override
    public String wrapTableName(String name) {
        return String.format("`%s`", name);
    }

    /**
     * parse table name
     *
     * @param tableName
     * @return
     */
    @Override
    public String parseTableName(String tableName) {
        if (tableName.startsWith("`") && tableName.endsWith("`")) {
            return tableName.substring(1, tableName.length() - 1);
        }
        return tableName;
    }
}
