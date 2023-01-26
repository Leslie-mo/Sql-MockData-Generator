package com.leslie.sqlMockDataGenerator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.leslie.sqlMockDataGenerator.common.ErrorCode;
import com.leslie.sqlMockDataGenerator.coreMethon.GeneratorFacade;
import com.leslie.sqlMockDataGenerator.coreMethon.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.exception.BusinessException;
import com.leslie.sqlMockDataGenerator.mapper.TableInfoMapper;
import com.leslie.sqlMockDataGenerator.model.entity.TableInfo;
import com.leslie.sqlMockDataGenerator.service.TableInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @description Database operation service implement for table [table_info]
 */
@Service
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfo> implements TableInfoService {

    private final static Gson GSON = new Gson();

    @Override
    public void validAndHandleTableInfo(TableInfo tableInfo, boolean add) {
        if (tableInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String content = tableInfo.getContent();
        String name = tableInfo.getName();
        Integer reviewStatus = tableInfo.getReviewStatus();
        // When creating, all parameters must be non-null
        if (add && StringUtils.isAnyBlank(name, content)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isNotBlank(name) && name.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "name too long");
        }
        if (StringUtils.isNotBlank(content)) {
            if (content.length() > 20000) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "content too long");
            }
            // verify field content
            try {
                TableSchema tableSchema = GSON.fromJson(content, TableSchema.class);
                GeneratorFacade.validSchema(tableSchema);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "content format error");
            }
        }

    }
}




