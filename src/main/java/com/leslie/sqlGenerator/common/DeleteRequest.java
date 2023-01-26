package com.leslie.sqlGenerator.common;

import lombok.Data;

import java.io.Serializable;

/**
 * delete request
 *
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}