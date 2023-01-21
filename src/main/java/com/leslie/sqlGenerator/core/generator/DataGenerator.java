package com.leslie.sqlGenerator.core.generator;



import com.leslie.sqlGenerator.core.schema.TableSchema.Field;

import java.util.List;

/**
 * 数据生成器
 *
 */
public interface DataGenerator {

    /**
     * 生成
     *
     * @param field 字段信息
     * @param rowNum 行数
     * @return 生成的数据列表
     */
    List<String> doGenerate(Field field, int rowNum);

}
