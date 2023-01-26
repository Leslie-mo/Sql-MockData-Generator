package com.leslie.sqlGenerator.coreMethon.generator;



import com.leslie.sqlGenerator.coreMethon.schema.TableSchema.Field;

import java.util.List;

/**
 * Data generator
 *
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
