package com.leslie.sqlMockDataGenerator.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Java entity generation wrapper DTO
 */
@Data
public class JavaEntityGenerateDTO {

    /**
     * class name
     */
    private String className;

    /**
     * class comment
     */
    private String classComment;

    /**
     * field info list
     */
    private List<FieldDTO> fieldList;

    /**
     * Field DTO
     */
    @Data
    public static class FieldDTO {
        /**
         * field name
         */
        private String fieldName;

        /**
         * Java type
         */
        private String javaType;

        /**
         * comment
         */
        private String comment;
    }

}
