package com.leslie.sqlMockDataGenerator.coreMethon.builder;

import com.leslie.sqlMockDataGenerator.common.ErrorCode;
import com.leslie.sqlMockDataGenerator.coreMethon.builder.sql.MySQLDialect;
import com.leslie.sqlMockDataGenerator.coreMethon.builder.sql.SQLDialect;
import com.leslie.sqlMockDataGenerator.coreMethon.builder.sql.SQLDialectFactory;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema.Field;
import com.leslie.sqlMockDataGenerator.exception.BusinessException;
import com.leslie.sqlMockDataGenerator.model.enums.FieldTypeEnum;
import com.leslie.sqlMockDataGenerator.model.enums.MockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SQL builder
 */
@Slf4j
public class SqlBuilder {

    /**
     * dialect
     */
    private SQLDialect sqlDialect;

    public SqlBuilder() {
        this.sqlDialect = SQLDialectFactory.getDialect(MySQLDialect.class.getName());
    }

    public SqlBuilder(SQLDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    /**
     * Get the value string based on the attribute of the column
     *
     * @param field
     * @param value
     * @return
     */
    public static String getValueStr(Field field, Object value) {
        if (field == null || value == null) {
            return "''";
        }
        FieldTypeEnum fieldTypeEnum = Optional.ofNullable(FieldTypeEnum.getEnumByValue(field.getFieldType()))
                .orElse(FieldTypeEnum.TEXT);
        String result = String.valueOf(value);
        switch (fieldTypeEnum) {
            case DATETIME:
            case TIMESTAMP:
                return result.equalsIgnoreCase("CURRENT_TIMESTAMP") ? result : String.format("'%s'", value);
            case DATE:
            case TIME:
            case CHAR:
            case VARCHAR:
            case TINYTEXT:
            case TEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
            case TINYBLOB:
            case BLOB:
            case MEDIUMBLOB:
            case LONGBLOB:
            case BINARY:
            case VARBINARY:
                return String.format("'%s'", value);
            default:
                return result;
        }
    }

    /**
     * set Sql dialect
     *
     * @param sqlDialect
     */
    public void setSqlDialect(SQLDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    /**
     * build SQL
     *
     * @param tableSchema
     * @return built SQL
     */
    public String buildCreateTableSql(TableSchema tableSchema) {
        // build template
        String template = "%s\n"
                + "create table if not exists %s\n"
                + "(\n"
                + "%s\n"
                + ") %s;";
        // build table name
        String tableName = sqlDialect.wrapTableName(tableSchema.getTableName());
        String dbName = tableSchema.getDbName();
        if (StringUtils.isNotBlank(dbName)) {
            tableName = String.format("%s.%s", dbName, tableName);
        }
        // build table prefix annotation
        String tableComment = tableSchema.getTableComment();
        if (StringUtils.isBlank(tableComment)) {
            tableComment = tableName;
        }
        String tablePrefixComment = String.format("-- %s", tableComment);
        // build table suffix comments
        String tableSuffixComment = String.format("comment '%s'", tableComment);
        // build table fields
        List<Field> fieldList = tableSchema.getFieldList();
        StringBuilder fieldStrBuilder = new StringBuilder();
        int fieldSize = fieldList.size();
        for (int i = 0; i < fieldSize; i++) {
            Field field = fieldList.get(i);
            fieldStrBuilder.append(buildCreateFieldSql(field));
            // No comma and newline after the last field
            if (i != fieldSize - 1) {
                fieldStrBuilder.append(",");
                fieldStrBuilder.append("\n");
            }
        }
        String fieldStr = fieldStrBuilder.toString();
        // fill template
        String result = String.format(template, tablePrefixComment, tableName, fieldStr, tableSuffixComment);
        log.info("sql result = " + result);
        return result;
    }

    /**
     * Build SQL to create fields
     *
     * @param field
     * @return
     */
    public String buildCreateFieldSql(Field field) {
        if (field == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String fieldName = sqlDialect.wrapFieldName(field.getFieldName());
        String fieldType = field.getFieldType();
        String defaultValue = field.getDefaultValue();
        boolean notNull = field.isNotNull();
        String comment = field.getComment();
        String onUpdate = field.getOnUpdate();
        boolean primaryKey = field.isPrimaryKey();
        boolean autoIncrement = field.isAutoIncrement();
        // e.g. column_name int default 0 not null auto_increment comment 'example comment' primary key,
        StringBuilder fieldStrBuilder = new StringBuilder();
        // field name
        fieldStrBuilder.append(fieldName);
        // field type
        fieldStrBuilder.append(" ").append(fieldType);
        // default
        if (StringUtils.isNotBlank(defaultValue)) {
            fieldStrBuilder.append(" ").append("default ").append(getValueStr(field, defaultValue));
        }
        // whether is not null
        fieldStrBuilder.append(" ").append(notNull ? "not null" : "null");
        // whether auto increment
        if (autoIncrement) {
            fieldStrBuilder.append(" ").append("auto_increment");
        }
        // additional conditions
        if (StringUtils.isNotBlank(onUpdate)) {
            fieldStrBuilder.append(" ").append("on update ").append(onUpdate);
        }
        // comment
        if (StringUtils.isNotBlank(comment)) {
            fieldStrBuilder.append(" ").append(String.format("comment '%s'", comment));
        }
        // whether is primary key
        if (primaryKey) {
            fieldStrBuilder.append(" ").append("primary key");
        }
        return fieldStrBuilder.toString();
    }

    /**
     * Build insert SQL
     *
     * @param tableSchema
     * @param dataList
     * @return Built insert SQL
     */
    public String buildInsertSql(TableSchema tableSchema, List<Map<String, Object>> dataList) {
        // build template
        String template = "insert into %s (%s) values (%s);";
        // wrap table name
        String tableName = sqlDialect.wrapTableName(tableSchema.getTableName());
        String dbName = tableSchema.getDbName();
        if (StringUtils.isNotBlank(dbName)) {
            tableName = String.format("%s.%s", dbName, tableName);
        }
        // build table schema
        List<Field> fieldList = tableSchema.getFieldList();
        // filter out fields that are not mocked
        fieldList = fieldList.stream()
                .filter(field -> {
                    MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType()))
                            .orElse(MockTypeEnum.NONE);
                    return !MockTypeEnum.NONE.equals(mockTypeEnum);
                })
                .collect(Collectors.toList());
        StringBuilder resultStringBuilder = new StringBuilder();
        int total = dataList.size();
        for (int i = 0; i < total; i++) {
            Map<String, Object> dataRow = dataList.get(i);
            String keyStr = fieldList.stream()
                    .map(field -> sqlDialect.wrapFieldName(field.getFieldName()))
                    .collect(Collectors.joining(", "));
            String valueStr = fieldList.stream()
                    .map(field -> getValueStr(field, dataRow.get(field.getFieldName())))
                    .collect(Collectors.joining(", "));
            // fill template
            String result = String.format(template, tableName, keyStr, valueStr);
            resultStringBuilder.append(result);
            // no newline after the last field
            if (i != total - 1) {
                resultStringBuilder.append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
