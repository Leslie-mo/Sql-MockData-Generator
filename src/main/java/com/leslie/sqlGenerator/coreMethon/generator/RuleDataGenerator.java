package com.leslie.sqlGenerator.coreMethon.generator;

import com.leslie.sqlGenerator.coreMethon.schema.TableSchema.Field;
import com.mifmif.common.regex.Generex;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule data generator
 *
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
