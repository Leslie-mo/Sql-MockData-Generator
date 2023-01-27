package com.leslie.sqlMockDataGenerator.coreMethon.builder;


import cn.hutool.core.util.StrUtil;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema.Field;
import com.leslie.sqlMockDataGenerator.model.dto.TypescriptTypeGenerateDTO;
import com.leslie.sqlMockDataGenerator.model.dto.TypescriptTypeGenerateDTO.FieldDTO;
import com.leslie.sqlMockDataGenerator.model.enums.FieldTypeEnum;
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
import java.util.Optional;

/**
 * frontend code builder
 */
@Component
@Slf4j
public class FrontendCodeBuilder {

    private static Configuration configuration;

    /**
     * Build Typescript code
     *
     * @param tableSchema 表概要
     * @return built code
     */
    @SneakyThrows
    public static String buildTypeScriptTypeCode(TableSchema tableSchema) {
        // pass parameters
        TypescriptTypeGenerateDTO generateDTO = new TypescriptTypeGenerateDTO();
        String tableName = tableSchema.getTableName();

        String tableComment = tableSchema.getTableComment();
        String upperCamelTableName = StringUtils.capitalize(StrUtil.toCamelCase(tableName));
        //  class name is uppercase table name
        generateDTO.setClassName(upperCamelTableName);
        // Class annotations are table annotations > table name
        generateDTO.setClassComment(Optional.ofNullable(tableComment).orElse(upperCamelTableName));
        // Fill each column in turn
        List<TypescriptTypeGenerateDTO.FieldDTO> fieldDTOList = new ArrayList<>();
        for (Field field : tableSchema.getFieldList()) {
            TypescriptTypeGenerateDTO.FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setComment(field.getComment());
            FieldTypeEnum fieldTypeEnum = Optional.ofNullable(FieldTypeEnum.getEnumByValue(field.getFieldType())).orElse(FieldTypeEnum.TEXT);
            fieldDTO.setTypescriptType(fieldTypeEnum.getTypescriptType());
            fieldDTO.setFieldName(StrUtil.toCamelCase(field.getFieldName()));
            fieldDTOList.add(fieldDTO);
        }
        generateDTO.setFieldList(fieldDTOList);
        StringWriter stringWriter = new StringWriter();
        Template temp = configuration.getTemplate("typescript_type.ftl");
        temp.process(generateDTO, stringWriter);
        return stringWriter.toString();
    }

    @Resource
    public void setConfiguration(Configuration configuration) {
        FrontendCodeBuilder.configuration = configuration;
    }

}
