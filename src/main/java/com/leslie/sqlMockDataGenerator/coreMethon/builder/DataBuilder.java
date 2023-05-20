package com.leslie.sqlMockDataGenerator.coreMethon.builder;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.leslie.sqlMockDataGenerator.coreMethon.generator.DataGenerator;
import com.leslie.sqlMockDataGenerator.coreMethon.generator.DataGeneratorFactory;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema.Field;
import com.leslie.sqlMockDataGenerator.model.enums.MockTypeEnum;

import java.util.*;

/**
 * data builder
 */
public class DataBuilder {

    /**
     * generate Data
     *
     * @param tableSchema
     * @param rowNum
     * @return built data
     */
    public static List<Map<String, Object>> generateData(TableSchema tableSchema, int rowNum) {
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
        // initialize result list
        List<Map<String, Object>> resultList = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            resultList.add(new HashMap<>());
        }
        // generate each column in turn
        for (Field field : fieldList) {
            MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType()))
                    .orElse(MockTypeEnum.NONE);
            // select generator by mock type
            DataGenerator dataGenerator = DataGeneratorFactory.getGenerator(mockTypeEnum);
            List<String> mockDataList = dataGenerator.doGenerate(field, rowNum);
            String fieldName = field.getFieldName();
            // filling the result list
            if (CollectionUtils.isNotEmpty(mockDataList)) {
                for (int i = 0; i < rowNum; i++) {
                    resultList.get(i).put(fieldName, mockDataList.get(i));
                }
            }
        }
        return resultList;
    }
}
