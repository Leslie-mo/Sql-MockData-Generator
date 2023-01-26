package com.leslie.sqlMockDataGenerator.coreMethon.generator;

import cn.hutool.core.date.DateUtil;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Default data generator
 */
public class DefaultDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(Field field, int rowNum) {
        String mockParams = field.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        // The primary key use the incremental strategy
        if (field.isPrimaryKey()) {
            if (StringUtils.isBlank(mockParams)) {
                mockParams = "1";
            }
            int initValue = Integer.parseInt(mockParams);
            for (int i = 0; i < rowNum; i++) {
                list.add(String.valueOf(initValue + i));
            }
            return list;
        }
        // use default value
        String defaultValue = field.getDefaultValue();
        // date
        if ("CURRENT_TIMESTAMP".equals(defaultValue)) {
            defaultValue = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        if (StringUtils.isNotBlank(defaultValue)) {
            for (int i = 0; i < rowNum; i++) {
                list.add(defaultValue);
            }
        }
        return list;
    }
}
