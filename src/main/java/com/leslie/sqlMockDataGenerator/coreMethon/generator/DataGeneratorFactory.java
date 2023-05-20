package com.leslie.sqlMockDataGenerator.coreMethon.generator;

import com.leslie.sqlMockDataGenerator.model.enums.MockTypeEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * data generator factory
 */
public class DataGeneratorFactory {

    private static final Map<MockTypeEnum, DataGenerator> mockTypeDataGeneratorMap = new HashMap<MockTypeEnum, DataGenerator>() {{
        put(MockTypeEnum.NONE, new DefaultDataGenerator());
        put(MockTypeEnum.FIXED, new FixedDataGenerator());
        put(MockTypeEnum.RANDOM, new RandomDataGenerator());
        put(MockTypeEnum.RULE, new RuleDataGenerator());
        put(MockTypeEnum.INCREASE, new IncreaseDataGenerator());
    }};

    /**
     * get instance by mockType
     *
     * @param mockTypeEnum
     * @return
     */
    public static DataGenerator getGenerator(MockTypeEnum mockTypeEnum) {
        mockTypeEnum = Optional.ofNullable(mockTypeEnum).orElse(MockTypeEnum.NONE);
        return mockTypeDataGeneratorMap.get(mockTypeEnum);
    }
}
