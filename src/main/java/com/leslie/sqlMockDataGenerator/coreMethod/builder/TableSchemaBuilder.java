package com.leslie.sqlMockDataGenerator.coreMethod.builder;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLPrimaryKey;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlCreateTableParser;
import com.alibaba.excel.EasyExcel;
import com.leslie.sqlMockDataGenerator.common.ErrorCode;
import com.leslie.sqlMockDataGenerator.coreMethod.builder.sql.MySQLDialect;
import com.leslie.sqlMockDataGenerator.coreMethod.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.coreMethod.schema.TableSchema.Field;
import com.leslie.sqlMockDataGenerator.exception.BusinessException;
import com.leslie.sqlMockDataGenerator.model.enums.FieldTypeEnum;
import com.leslie.sqlMockDataGenerator.model.enums.MockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * table schema builder
 */
@Component
@Slf4j
public class TableSchemaBuilder {

    private static final MySQLDialect sqlDialect = new MySQLDialect();


    /**
     * Date type
     */
    private static final String[] DATE_PATTERNS = {"yyyy-MM-dd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};


    /**
     * build from SQL
     *
     * @param sql
     * @return built tableSchema
     */
    public static TableSchema buildFromSql(String sql) {
        if (StringUtils.isBlank(sql)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        try {
            // parse SQL
            MySqlCreateTableParser parser = new MySqlCreateTableParser(sql);
            SQLCreateTableStatement sqlCreateTableStatement = parser.parseCreateTable();
            TableSchema tableSchema = new TableSchema();
            tableSchema.setDbName(sqlCreateTableStatement.getSchema());
            tableSchema.setTableName(sqlDialect.parseTableName(sqlCreateTableStatement.getTableName()));
            String tableComment = null;
            if (sqlCreateTableStatement.getComment() != null) {
                tableComment = sqlCreateTableStatement.getComment().toString();
                if (tableComment.length() > 2) {
                    tableComment = tableComment.substring(1, tableComment.length() - 1);
                }
            }
            tableSchema.setTableComment(tableComment);
            List<Field> fieldList = new ArrayList<>();
            // parse column
            for (SQLTableElement sqlTableElement : sqlCreateTableStatement.getTableElementList()) {
                // primary key constraints
                if (sqlTableElement instanceof SQLPrimaryKey) {
                    SQLPrimaryKey sqlPrimaryKey = (SQLPrimaryKey) sqlTableElement;
                    String primaryFieldName = sqlDialect.parseFieldName(sqlPrimaryKey.getColumns().get(0).toString());
                    fieldList.forEach(field -> {
                        if (field.getFieldName().equals(primaryFieldName)) {
                            field.setPrimaryKey(true);
                        }
                    });
                } else if (sqlTableElement instanceof SQLColumnDefinition) {
                    // column
                    SQLColumnDefinition columnDefinition = (SQLColumnDefinition) sqlTableElement;
                    Field field = new Field();
                    field.setFieldName(sqlDialect.parseFieldName(columnDefinition.getNameAsString()));
                    field.setFieldType(columnDefinition.getDataType().toString());
                    String defaultValue = null;
                    if (columnDefinition.getDefaultExpr() != null) {
                        defaultValue = columnDefinition.getDefaultExpr().toString();
                    }
                    field.setDefaultValue(defaultValue);
                    field.setNotNull(columnDefinition.containsNotNullConstaint());
                    String comment = null;
                    if (columnDefinition.getComment() != null) {
                        comment = columnDefinition.getComment().toString();
                        if (comment.length() > 2) {
                            comment = comment.substring(1, comment.length() - 1);
                        }
                    }
                    field.setComment(comment);
                    field.setPrimaryKey(columnDefinition.isPrimaryKey());
                    field.setAutoIncrement(columnDefinition.isAutoIncrement());
                    String onUpdate = null;
                    if (columnDefinition.getOnUpdate() != null) {
                        onUpdate = columnDefinition.getOnUpdate().toString();
                    }
                    field.setOnUpdate(onUpdate);
                    field.setMockType(MockTypeEnum.NONE.getValue());
                    fieldList.add(field);
                }
            }
            tableSchema.setFieldList(fieldList);
            return tableSchema;
        } catch (Exception e) {
            log.error("SQL parsing error", e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Please make sure the SQL is correct");
        }
    }

    /**
     * build from Excel
     *
     * @param file Excel
     * @return built tableSchema
     */
    public static TableSchema buildFromExcel(MultipartFile file) {
        try {
            List<Map<Integer, String>> dataList = EasyExcel.read(file.getInputStream()).sheet().headRowNumber(0).doReadSync();
            if (CollectionUtils.isEmpty(dataList)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "No data in Excel");
            }
            // first row is table header
            Map<Integer, String> map = dataList.get(0);
            List<Field> fieldList = map.values().stream().map(name -> {
                Field field = new Field();
                field.setFieldName(name);
                field.setComment(name);
                field.setFieldType(FieldTypeEnum.TEXT.getValue());
                return field;
            }).collect(Collectors.toList());
            // Second row is value
            if (dataList.size() > 1) {
                Map<Integer, String> dataMap = dataList.get(1);
                for (int i = 0; i < fieldList.size(); i++) {
                    String value = dataMap.get(i);
                    // determine the type by value
                    String fieldType = getFieldTypeByValue(value);
                    fieldList.get(i).setFieldType(fieldType);
                }
            }
            TableSchema tableSchema = new TableSchema();
            tableSchema.setFieldList(fieldList);
            return tableSchema;
        } catch (Exception e) {
            log.error("buildFromExcel error", e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Form parsing error");
        }
    }

    /**
     * Determining field types
     *
     * @param value
     * @return
     */
    public static String getFieldTypeByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return FieldTypeEnum.TEXT.getValue();
        }
        // TINYINT
        if ("false".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
            return FieldTypeEnum.TINYINT.getValue();
        }
        // INT
        if (StringUtils.isNumeric(value)) {
            long number = Long.parseLong(value);
            if (number > Integer.MAX_VALUE) {
                return FieldTypeEnum.BIGINT.getValue();
            }
            return FieldTypeEnum.INT.getValue();
        }
        // DOUBLE
        if (isDouble(value)) {
            return FieldTypeEnum.DOUBLE.getValue();
        }
        // DATETIME
        if (isDate(value)) {
            return FieldTypeEnum.DATETIME.getValue();
        }
        return FieldTypeEnum.TEXT.getValue();
    }

    /**
     * Whether is double
     *
     * @param str
     * @return
     */
    private static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * Whether is date
     *
     * @param str
     * @return
     */
    private static boolean isDate(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        try {
            DateUtils.parseDate(str, DATE_PATTERNS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * get default field
     *
     * @param word
     * @return
     */
    private static Field getDefaultField(String word) {
        final Field field = new Field();
        field.setFieldName(word);
        field.setFieldType("text");
        field.setDefaultValue("");
        field.setNotNull(false);
        field.setComment(word);
        field.setPrimaryKey(false);
        field.setAutoIncrement(false);
        field.setMockType("");
        field.setMockParams("");
        field.setOnUpdate("");
        return field;
    }

}
