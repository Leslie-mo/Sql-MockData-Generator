package com.leslie.sqlGenerator.coreMethon.generator;

import com.leslie.sqlGenerator.coreMethon.schema.TableSchema;
import com.leslie.sqlGenerator.model.enums.MockParamsRandomTypeEnum;
import com.leslie.sqlGenerator.utils.FakerUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Random data generator
 *
 */
public class RandomDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(TableSchema.Field field, int rowNum) {
        String mockParams = field.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            MockParamsRandomTypeEnum randomTypeEnum = Optional.ofNullable(
                            MockParamsRandomTypeEnum.getEnumByValue(mockParams))
                    .orElse(MockParamsRandomTypeEnum.STRING);
            String randomString = FakerUtils.getRandomValue(randomTypeEnum);
            list.add(randomString);
        }
        return list;
    }
}
