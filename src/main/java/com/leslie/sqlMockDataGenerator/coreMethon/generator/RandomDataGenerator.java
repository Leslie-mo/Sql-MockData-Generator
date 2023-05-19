package com.leslie.sqlMockDataGenerator.coreMethon.generator;

import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.model.enums.MockParamsRandomTypeEnum;
import com.leslie.sqlMockDataGenerator.utils.FakerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * random data generator
 */
public class RandomDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(TableSchema.Field field, int rowNum) {
        String mockParams = field.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            // isnull check
            MockParamsRandomTypeEnum randomTypeEnum = Optional.ofNullable(
                            MockParamsRandomTypeEnum.getEnumByValue(mockParams))
                    .orElse(MockParamsRandomTypeEnum.STRING);
            String randomString = FakerUtils.getRandomValue(randomTypeEnum);
            list.add(randomString);
        }
        return list;
    }
}
