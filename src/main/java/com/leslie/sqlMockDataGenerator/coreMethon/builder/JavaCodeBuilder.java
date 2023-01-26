package com.leslie.sqlMockDataGenerator.coreMethon.builder;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.leslie.sqlMockDataGenerator.common.ErrorCode;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema.Field;
import com.leslie.sqlMockDataGenerator.exception.BusinessException;
import com.leslie.sqlMockDataGenerator.model.dto.JavaEntityGenerateDTO;
import com.leslie.sqlMockDataGenerator.model.dto.JavaEntityGenerateDTO.FieldDTO;
import com.leslie.sqlMockDataGenerator.model.dto.JavaObjectGenerateDTO;
import com.leslie.sqlMockDataGenerator.model.enums.FieldTypeEnum;
import com.leslie.sqlMockDataGenerator.model.enums.MockTypeEnum;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Java code builder
 */
@Component
@Slf4j
public class JavaCodeBuilder {

    private static Configuration configuration;

    /**
     * Build Java Entity Code
     *
     * @param tableSchema
     * @return built Java code
     */
    @SneakyThrows
    public static String buildJavaEntityCode(TableSchema tableSchema) {
        // pass parameters
        JavaEntityGenerateDTO javaEntityGenerateDTO = new JavaEntityGenerateDTO();
        String tableName = tableSchema.getTableName();
        String tableComment = tableSchema.getTableComment();
        String upperCamelTableName = StringUtils.capitalize(StrUtil.toCamelCase(tableName));
        // class name is uppercase table name
        javaEntityGenerateDTO.setClassName(upperCamelTableName);
        // class annotations are table annotations > table name
        javaEntityGenerateDTO.setClassComment(Optional.ofNullable(tableComment).orElse(upperCamelTableName));
        // fill each column in turn
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        for (Field field : tableSchema.getFieldList()) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setComment(field.getComment());
            FieldTypeEnum fieldTypeEnum = Optional.ofNullable(FieldTypeEnum.getEnumByValue(field.getFieldType())).orElse(FieldTypeEnum.TEXT);
            fieldDTO.setJavaType(fieldTypeEnum.getJavaType());
            fieldDTO.setFieldName(StrUtil.toCamelCase(field.getFieldName()));
            fieldDTOList.add(fieldDTO);
        }
        javaEntityGenerateDTO.setFieldList(fieldDTOList);
        StringWriter stringWriter = new StringWriter();
        Template temp = configuration.getTemplate("java_entity.ftl");
        temp.process(javaEntityGenerateDTO, stringWriter);
        return stringWriter.toString();
    }

    /**
     * Build Java object code
     *
     * @param tableSchema
     * @param dataList
     * @return Generated java code
     */
    @SneakyThrows
    public static String buildJavaObjectCode(TableSchema tableSchema, List<Map<String, Object>> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Missing sample data");
        }
        // pass parameters
        JavaObjectGenerateDTO javaObjectGenerateDTO = new JavaObjectGenerateDTO();
        String tableName = tableSchema.getTableName();
        String camelTableName = StrUtil.toCamelCase(tableName);
        // class name is uppercase table name
        javaObjectGenerateDTO.setClassName(StringUtils.capitalize(camelTableName));
        // object name is table name
        javaObjectGenerateDTO.setObjectName(camelTableName);
        // fill each column in turn
        Map<String, Object> fillData = dataList.get(0);
        List<JavaObjectGenerateDTO.FieldDTO> fieldDTOList = new ArrayList<>();
        List<Field> fieldList = tableSchema.getFieldList();
        // filter out fields that are not simulated
        fieldList = fieldList.stream()
                .filter(field -> {
                    MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType())).orElse(MockTypeEnum.NONE);
                    return !MockTypeEnum.NONE.equals(mockTypeEnum);
                })
                .collect(Collectors.toList());
        for (Field field : fieldList) {
            JavaObjectGenerateDTO.FieldDTO fieldDTO = new JavaObjectGenerateDTO.FieldDTO();
            // set field name into hump-shaped
            String fieldName = field.getFieldName();
            fieldDTO.setSetMethod(StrUtil.toCamelCase("set_" + fieldName));
            fieldDTO.setValue(getValueStr(field, fillData.get(fieldName)));
            fieldDTOList.add(fieldDTO);
        }
        javaObjectGenerateDTO.setFieldList(fieldDTOList);
        StringWriter stringWriter = new StringWriter();
        Template temp = configuration.getTemplate("java_object.ftl");
        temp.process(javaObjectGenerateDTO, stringWriter);
        return stringWriter.toString();
    }

    /**
     * Get the value string based on the column's properties
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
        switch (fieldTypeEnum) {
            case DATE:
            case TIME:
            case DATETIME:
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
                return String.format("\"%s\"", value);
            default:
                return String.valueOf(value);
        }
    }

    @Resource
    public void setConfiguration(Configuration configuration) {
        JavaCodeBuilder.configuration = configuration;
    }
}
