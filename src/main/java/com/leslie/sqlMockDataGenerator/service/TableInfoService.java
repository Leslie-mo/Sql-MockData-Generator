package com.leslie.sqlMockDataGenerator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.sqlMockDataGenerator.model.entity.TableInfo;

import java.io.IOException;

/**
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

    String validateSql(String apikey, String sql) ;
}
