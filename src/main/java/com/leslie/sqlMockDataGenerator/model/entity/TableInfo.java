package com.leslie.sqlMockDataGenerator.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * table info entity
 *
 * @TableName table_info
 */
@TableName(value = "table_info")
@Data
public class TableInfo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * name
     */
    private String name;
    /**
     * content
     */
    private String content;
    /**
     * reviewStatus
     */
    private Integer reviewStatus;
    /**
     * review message
     */
    private String reviewMessage;
    /**
     * userId
     */
    private Long userId;
    /**
     * create time
     */
    private Date createTime;
    /**
     * update time
     */
    private Date updateTime;
    /**
     * isDelete
     */
    private Integer isDelete;
}