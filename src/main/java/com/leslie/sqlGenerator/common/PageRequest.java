package com.leslie.sqlGenerator.common;


import lombok.Data;

/**
 * page request
 *
 */
@Data
public class PageRequest {

    /**
     * current page
     */
    private long current = 1;

    /**
     * page size
     */
    private long pageSize = 10;

    /**
     * sort field
     */
    private String sortField;

    /**
     * ascending order
     */
    private String sortOrder = "ascend";
}
