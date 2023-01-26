package com.leslie.sqlMockDataGenerator.coreMethon.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * Json builder
 */
@Slf4j
public class JsonBuilder {

    /**
     * Build data json
     * e.g. {"id": 1}
     *
     * @param dataList
     * @return built json data
     */
    public static String buildJson(List<Map<String, Object>> dataList) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(dataList);
    }
}
