package com.leslie.sqlMockDataGenerator.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.leslie.sqlMockDataGenerator.common.*;
import com.leslie.sqlMockDataGenerator.coreMethod.builder.SqlBuilder;
import com.leslie.sqlMockDataGenerator.coreMethod.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.exception.BusinessException;
import com.leslie.sqlMockDataGenerator.model.dto.TableInfoAddRequest;
import com.leslie.sqlMockDataGenerator.model.dto.ValidateRequest;
import com.leslie.sqlMockDataGenerator.model.entity.TableInfo;
import com.leslie.sqlMockDataGenerator.service.TableInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * table information interface
 */
@RestController
@RequestMapping("/table_info")
@Slf4j
public class TableInfoController {

    private final static Gson GSON = new Gson();
    @Resource
    private TableInfoService tableInfoService;

    /**
     * create
     *
     * @param tableInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addTableInfo(@RequestBody TableInfoAddRequest tableInfoAddRequest,
                                           HttpServletRequest request) {
        if (tableInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        TableInfo tableInfo = new TableInfo();
        BeanUtils.copyProperties(tableInfoAddRequest, tableInfo);
        // valid
        tableInfoService.validAndHandleTableInfo(tableInfo, true);
        tableInfo.setUserId(1L);

        boolean result = tableInfoService.save(tableInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(tableInfo.getId());
    }

    /**
     * validate
     *
     * @param validateRequest
     * @param request
     * @return
     */
    @PostMapping("/validate")
    public BaseResponse<String> ValidateInfo(@RequestBody ValidateRequest validateRequest,
                                             HttpServletRequest request) throws Exception {
        System.out.println(validateRequest.getApikey());
        System.out.println(validateRequest.getSql());

        String result = tableInfoService.validateSql(validateRequest.getApikey(), validateRequest.getSql());

        return ResultUtils.success(result);
    }


    /**
     * delete
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTableInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // judge whether exist
        TableInfo oldTableInfo = tableInfoService.getById(id);
        if (oldTableInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean b = tableInfoService.removeById(id);
        return ResultUtils.success(b);
    }


    /**
     * get by id
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<TableInfo> getTableInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        TableInfo tableInfo = tableInfoService.getById(id);
        return ResultUtils.success(tableInfo);
    }

    /**
     * list table info by page
     *
     * @param tableInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<TableInfo>> listTableInfoByPage(TableInfoQueryRequest tableInfoQueryRequest,
                                                             HttpServletRequest request) {
        long current = tableInfoQueryRequest.getCurrent();
        long size = tableInfoQueryRequest.getPageSize();
        Page<TableInfo> tableInfoPage = tableInfoService.page(new Page<>(current, size),
                getQueryWrapper(tableInfoQueryRequest));
        return ResultUtils.success(tableInfoPage);
    }

    /**
     * table info query
     *
     * @param tableInfoQueryRequest
     * @return
     */
    private QueryWrapper<TableInfo> getQueryWrapper(TableInfoQueryRequest tableInfoQueryRequest) {
        if (tableInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Request parameter is empty");
        }
        TableInfo tableInfoQuery = new TableInfo();
        BeanUtils.copyProperties(tableInfoQueryRequest, tableInfoQuery);
        String sortField = tableInfoQueryRequest.getSortField();
        String sortOrder = tableInfoQueryRequest.getSortOrder();
        String name = tableInfoQuery.getName();
        String content = tableInfoQuery.getContent();
        // name and content can be fuzzy searched
        tableInfoQuery.setName(null);
        tableInfoQuery.setContent(null);
        QueryWrapper<TableInfo> queryWrapper = new QueryWrapper<>(tableInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals("ascend"),
                sortField);
        return queryWrapper;
    }

    /**
     * generate create Sql
     *
     * @param id
     * @return
     */
    @PostMapping("/generate/sql")
    public BaseResponse<String> generateCreateSql(@RequestBody long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        TableInfo tableInfo = tableInfoService.getById(id);
        if (tableInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        TableSchema tableSchema = GSON.fromJson(tableInfo.getContent(), TableSchema.class);
        SqlBuilder sqlBuilder = new SqlBuilder();
        return ResultUtils.success(sqlBuilder.buildCreateTableSql(tableSchema));
    }
}
