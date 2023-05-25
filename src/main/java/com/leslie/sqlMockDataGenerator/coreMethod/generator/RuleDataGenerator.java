package com.leslie.sqlMockDataGenerator.coreMethod.generator;

import com.leslie.sqlMockDataGenerator.coreMethod.schema.TableSchema.Field;
import com.mifmif.common.regex.Generex;

import java.util.ArrayList;
import java.util.List;

/**
 * rule data generator (Regular expressions)
 */
public class RuleDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(Field field, int rowNum) {
        String mockParams = field.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        Generex generex = new Generex(mockParams);
        for (int i = 0; i < rowNum; i++) {
            String randomStr = generex.random();
            list.add(randomStr);
        }
        return list;
    }
}
