package com.leslie.sqlGenerator.controller;

import com.leslie.sqlGenerator.model.VO.GenerateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sql")
@Slf4j
public class SQLGenerateController {

    @PostMapping("/generate/schema")
    public GenerateVO generateVO(@RequestBody String tableSchema){
        return new GenerateVO();
    }
}
