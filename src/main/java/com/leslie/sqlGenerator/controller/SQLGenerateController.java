package com.leslie.sqlGenerator.controller;

import com.leslie.sqlGenerator.common.BaseResponse;
import com.leslie.sqlGenerator.common.ResultUtils;
import com.leslie.sqlGenerator.core.GeneratorFacade;
import com.leslie.sqlGenerator.core.schema.TableSchema;
import com.leslie.sqlGenerator.model.VO.GenerateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * SQL interface
 *
 */
@RestController
@RequestMapping("/sql")
@Slf4j
public class SQLGenerateController {


    @PostMapping("/generate/schema")
    public BaseResponse<GenerateVO> generateBySchema(@RequestBody TableSchema tableSchema) {
        return ResultUtils.success(GeneratorFacade.generateAll(tableSchema));
    }
}
