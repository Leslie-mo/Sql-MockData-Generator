package com.leslie.sqlGenerator.core.generator;

import com.leslie.sqlGenerator.model.enums.MockTypeEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataGeneratorFactory {

    private static final Map<MockTypeEnum, DataGenerator> mockTypeDataGeneratorMap = new HashMap<MockTypeEnum, DataGenerator>() {{
        put(MockTypeEnum.NONE, new DefaultDataGenerator());
        put(MockTypeEnum.FIXED, new FixedDataGenerator());
//        put(MockTypeEnum.RANDOM, new RandomDataGenerator());
//        put(MockTypeEnum.RULE, new RuleDataGenerator());
//        put(MockTypeEnum.DICT, new DictDataGenerator());
        put(MockTypeEnum.INCREASE, new IncreaseDataGenerator());
    }};
    /**
     * 获取实例
     *
     * @param mockTypeEnum
     * @return
     */
    public static DataGenerator getGenerator(MockTypeEnum mockTypeEnum) {
        mockTypeEnum = Optional.ofNullable(mockTypeEnum).orElse(MockTypeEnum.NONE);
        return mockTypeDataGeneratorMap.get(mockTypeEnum);
    }
}
