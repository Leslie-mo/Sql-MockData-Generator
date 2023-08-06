package com.leslie.sqlMockDataGenerator.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.leslie.sqlMockDataGenerator.common.ErrorCode;
import com.leslie.sqlMockDataGenerator.coreMethod.GeneratorFacade;
import com.leslie.sqlMockDataGenerator.coreMethod.schema.TableSchema;
import com.leslie.sqlMockDataGenerator.exception.BusinessException;
import com.leslie.sqlMockDataGenerator.mapper.TableInfoMapper;
import com.leslie.sqlMockDataGenerator.model.entity.TableInfo;
import com.leslie.sqlMockDataGenerator.service.TableInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


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

        String result = null;

        try {
            result = test(apiKey, sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String test(String apiKey, String sqlQuery) throws Exception {

        String Bearer = "Bearer ";
        String token = Bearer + apiKey;
        String result = null;
        String url = "https://api.openai.com/v1/chat/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", token);

        String model = "gpt-3.5-turbo";

        // Create JSON array containing a JSON object using the newer syntax
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role", "user");
        jsonObject.put("content", "Check the correctness of the SQL query: " + sqlQuery);
        jsonArray.put(jsonObject);

        // Convert JSON array to string
        String prompt = jsonArray.toString();
        int maxTokens = 50;

        con.setDoOutput(true);
        String body = "{\"model\": \"" + model + "\", \"messages\": " + prompt + ", \"max_tokens\": " + maxTokens + "}";


        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
        writer.write(body);
        writer.flush();

        //Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.toString());
            String content = jsonNode.get("choices").get(0).get("message").get("content").asText();
            System.out.println("Content: " + content);
            result = content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}








