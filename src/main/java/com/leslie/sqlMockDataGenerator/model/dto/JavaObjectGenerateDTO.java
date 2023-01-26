package com.leslie.sqlMockDataGenerator.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Java Object generate DTO
 */
@Data
public class JavaObjectGenerateDTO {

    /**
     * class name
     */
    private String className;

    /**
     * object name
     */
    private String objectName;

    /**
     * field list
     */
    private List<FieldDTO> fieldList;

    /**
     * field DTO
     */
    @Data
    public static class FieldDTO {
        /**
         * set method
         */
        private String setMethod;

        /**
         * value
         */
        private String value;
    }

}
