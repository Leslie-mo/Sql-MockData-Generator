package com.leslie.sqlGenerator.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * 
 */
@Data
public class TableInfoAddRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}