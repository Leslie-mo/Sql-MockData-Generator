package com.leslie.sqlMockDataGenerator.coreMethod.generator;


import com.leslie.sqlMockDataGenerator.coreMethod.schema.TableSchema.Field;

import java.util.List;

/**
 * data generator
 */
public interface DataGenerator {

    /**
     * do generate
     *
     * @param field
     * @param rowNum
     * @return Generated data
     */
    List<String> doGenerate(Field field, int rowNum);

}
