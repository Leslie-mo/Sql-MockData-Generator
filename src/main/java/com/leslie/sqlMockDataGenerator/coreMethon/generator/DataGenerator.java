package com.leslie.sqlMockDataGenerator.coreMethon.generator;


import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema.Field;

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
