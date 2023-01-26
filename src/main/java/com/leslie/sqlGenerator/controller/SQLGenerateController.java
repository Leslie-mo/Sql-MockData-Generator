package com.leslie.sqlGenerator.controller;

import com.alibaba.excel.EasyExcel;
import com.leslie.sqlGenerator.common.BaseResponse;
import com.leslie.sqlGenerator.common.ErrorCode;
import com.leslie.sqlGenerator.common.ResultUtils;
import com.leslie.sqlGenerator.coreMethon.GeneratorFacade;
import com.leslie.sqlGenerator.coreMethon.schema.TableSchema;
import com.leslie.sqlGenerator.coreMethon.schema.TableSchema.Field;
import com.leslie.sqlGenerator.exception.BusinessException;
import com.leslie.sqlGenerator.model.vo.GenerateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * SQL interface
 *
 */
@RestController
@RequestMapping("/sql")
@Slf4j
public class SQLGenerateController {

    /**
     *
     * @param tableSchema
     * @return
     */
    @PostMapping("/generate/schema")
    public BaseResponse<GenerateVO> generateBySchema(@RequestBody TableSchema tableSchema) {
        return ResultUtils.success( GeneratorFacade.generateAll(tableSchema));
    }

    /**
     *
     * @param generateVO
     * @param response
     */
    @PostMapping("/download/data/excel")
    public void downloadDataExcel(@RequestBody GenerateVO generateVO, HttpServletResponse response) {
        TableSchema tableSchema = generateVO.getTableSchema();
        String tableName = tableSchema.getTableName();
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // here URLEncoder.encode can prevent Chinese and Japanese garbled characters
            String fileName = URLEncoder.encode(tableName + " Table data", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            // set table header
            List<List<String>> headList = new ArrayList<>();
            for (TableSchema.Field field : tableSchema.getFieldList()) {
                List<String> head = Collections.singletonList(field.getFieldName());
                headList.add(head);
            }
            List<String> fieldNameList = tableSchema.getFieldList().stream()
                    .map(Field::getFieldName).collect(Collectors.toList());
            // set table data
            List<List<Object>> dataList = new ArrayList<>();
            for (Map<String, Object> data : generateVO.getDataList()) {
                List<Object> dataRow = fieldNameList.stream().map(data::get).collect(Collectors.toList());
                dataList.add(dataRow);
            }
            // set does not close the stream
            EasyExcel.write(response.getOutputStream())
                    .autoCloseStream(Boolean.FALSE)
                    .head(headList)
                    .sheet(tableName)
                    .doWrite(dataList);
        } catch (Exception e) {
            // reset response
            response.reset();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "download fail");
        }
    }


}
