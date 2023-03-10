package com.leslie.sqlMockDataGenerator.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * mock params random type Enum
 */
public enum MockParamsRandomTypeEnum {

    STRING("String"),
    NAME("Person name"),
    CITY("City"),
    URL("URL"),
    EMAIL("Email"),
    IP("IP"),
    INTEGER("Integer"),
    DECIMAL("Decimal"),
    UNIVERSITY("University"),
    DATE("Date"),
    TIMESTAMP("Timestamp"),
    PHONE("Phone number");

    private final String value;

    MockParamsRandomTypeEnum(String value) {
        this.value = value;
    }

    /**
     * get values
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(MockParamsRandomTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * get enum by value
     *
     * @param value
     * @return
     */
    public static MockParamsRandomTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (MockParamsRandomTypeEnum mockTypeEnum : MockParamsRandomTypeEnum.values()) {
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
