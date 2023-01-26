package com.leslie.sqlMockDataGenerator.utils;


import com.leslie.sqlMockDataGenerator.model.enums.MockParamsRandomTypeEnum;
import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 * random number generator
 */
public class FakerUtils {

    private final static Faker EN_FAKER = new Faker();

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * get random value
     *
     * @param randomTypeEnum
     * @return
     */
    public static String getRandomValue(MockParamsRandomTypeEnum randomTypeEnum) {
        String defaultValue = RandomStringUtils.randomAlphanumeric(2, 6);
        if (randomTypeEnum == null) {
            return defaultValue;
        }
        switch (randomTypeEnum) {
            case NAME:
                return EN_FAKER.name().name();
            case CITY:
                return EN_FAKER.address().city();
            case EMAIL:
                return EN_FAKER.internet().emailAddress();
            case URL:
                return EN_FAKER.internet().url();
            case IP:
                return EN_FAKER.internet().ipV4Address();
            case INTEGER:
                return String.valueOf(EN_FAKER.number().randomNumber());
            case DECIMAL:
                return String.valueOf(RandomUtils.nextFloat(0, 100000));
            case UNIVERSITY:
                return EN_FAKER.university().name();
            case DATE:
                return EN_FAKER.date()
                        .between(Timestamp.valueOf("2022-01-01 00:00:00"), Timestamp.valueOf("2023-01-01 00:00:00"))
                        .toLocalDateTime().format(DATE_TIME_FORMATTER);
            case TIMESTAMP:
                return String.valueOf(EN_FAKER.date()
                        .between(Timestamp.valueOf("2022-01-01 00:00:00"), Timestamp.valueOf("2023-01-01 00:00:00"))
                        .getTime());
            case PHONE:
                return EN_FAKER.phoneNumber().cellPhone();
            default:
                return defaultValue;
        }
    }

    public static void main(String[] args) {
        getRandomValue(null);
    }

}
