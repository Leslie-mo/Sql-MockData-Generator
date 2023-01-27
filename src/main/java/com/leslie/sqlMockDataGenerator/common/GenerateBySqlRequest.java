package com.leslie.sqlMockDataGenerator.common;

import lombok.Data;

import java.io.Serializable;

/**
 * generate by SQL request
 */
@Data
public class GenerateBySqlRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String sql;
}
