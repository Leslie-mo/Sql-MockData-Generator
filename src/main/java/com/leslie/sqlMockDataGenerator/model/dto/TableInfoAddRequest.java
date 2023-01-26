package com.leslie.sqlMockDataGenerator.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * table info add request
 */
@Data
public class TableInfoAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * name
     */
    private String name;
    /**
     * content
     */
    private String content;
}