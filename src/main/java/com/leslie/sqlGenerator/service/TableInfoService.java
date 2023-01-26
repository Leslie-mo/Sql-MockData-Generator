package com.leslie.sqlGenerator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.sqlGenerator.model.entity.TableInfo;

/**
 * li
 * @description Database operation Service for table [table_info]
 */
public interface TableInfoService extends IService<TableInfo> {

    /**
     * valid and handle table info
     *
     * @param tableInfo
     * @param add
     */
    void validAndHandleTableInfo(TableInfo tableInfo, boolean add);
}
