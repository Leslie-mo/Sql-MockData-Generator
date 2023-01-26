package com.leslie.sqlGenerator.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mock type enum
 *
 */
public enum MockTypeEnum {

    NONE("不模拟"),
    INCREASE("递增"),
    FIXED("固定"),
    RANDOM("随机"),
    RULE("规则"),
    DICT("词库");

    private final String value;

    MockTypeEnum(String value) {
        this.value = value;
    }

    /**
     *  get values
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(MockTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     *  get enum by value
     *
     * @param value
     * @return
     */
    public static MockTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (MockTypeEnum mockTypeEnum : MockTypeEnum.values()) {
            if (mockTypeEnum.value.equals(value)) {
                return mockTypeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
