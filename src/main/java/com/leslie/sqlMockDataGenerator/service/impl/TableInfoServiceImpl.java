package com.leslie.sqlMockDataGenerator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.leslie.sqlMockDataGenerator.common.ErrorCode;
import com.leslie.sqlMockDataGenerator.coreMethod.GeneratorFacade;
import com.leslie.sqlMockDataGenerator.coreMethod.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.exception.BusinessException;
import com.leslie.sqlMockDataGenerator.mapper.TableInfoMapper;
import com.leslie.sqlMockDataGenerator.model.entity.TableInfo;
import com.leslie.sqlMockDataGenerator.service.TableInfoService;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

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

    @Override
    public String validateSql(String apiKey, String sqlQuery) {
        // config api keys
        OpenAiClient openAiClient = new OpenAiClient(apiKey);

        String payload = "{\"prompt\": \"Check the correctness of the SQL query: \\\"" + sqlQuery + "\\\".\","
                + "\"max_tokens\": 100,\"stop\": \"\\n\"}";

        CompletionResponse completions = openAiClient.completions(payload);

        Arrays.stream(completions.getChoices()).forEach(System.out::println);

        String response = completions.getChoices()[0].getText();

        // Return the response from OpenAI
        return response;
    }
}




