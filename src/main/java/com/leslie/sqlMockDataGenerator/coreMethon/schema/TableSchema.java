package com.leslie.sqlMockDataGenerator.coreMethon.schema;

import lombok.Data;

import java.util.List;

/**
 * table summary
 */
@Data
public class TableSchema {

    /**
     * dbName
     */
    private String dbName;

    /**
     * tableName
     */
    private String tableName;

    /**
     * tableComment
     */
    private String tableComment;

    /**
     * mockNum
     */
    private Integer mockNum;

    /**
     * fieldList
     */
    private List<Field> fieldList;

    /**
     * properties of a field
     */
    @Data
    public static class Field {
        /**
         * fieldName
         */
        private String fieldName;

        /**
         * fieldType
         */
        private String fieldType;

        /**
         * defaultValue
         */
        private String defaultValue;

        /**
         * notNull
         */
        private boolean notNull;

        /**
         * comment
         */
        private String comment;

        /**
         * is primaryKey
         */
        private boolean primaryKey;

        /**
         * is autoIncrement
         */
        private boolean autoIncrement;

        /**
         * mockType（random、image、rule、dictionary）
         */
        private String mockType;

        /**
         * mockParams
         */
        private String mockParams;

        /**
         * onUpdate
         */
        private String onUpdate;
    }

}
