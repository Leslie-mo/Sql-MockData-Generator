package com.leslie.sqlMockDataGenerator.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Typescript type generate DTO
 */
@Data
public class TypescriptTypeGenerateDTO {

    /**
     * class name
     */
    private String className;

    /**
     * class comment
     */
    private String classComment;

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
         * field name
         */
        private String fieldName;

        /**
         * Typescript type
         */
        private String typescriptType;

        /**
         * comment
         */
        private String comment;
    }

}
