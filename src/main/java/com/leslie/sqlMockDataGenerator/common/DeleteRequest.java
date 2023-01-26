package com.leslie.sqlMockDataGenerator.common;

import lombok.Data;

import java.io.Serializable;

/**
 * delete request
 */
@Data
public class DeleteRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
}