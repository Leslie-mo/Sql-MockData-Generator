package com.leslie.sqlMockDataGenerator.model.vo;

import com.leslie.sqlMockDataGenerator.coreMethod.schema.TableSchema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class GenerateVO implements Serializable {
    private static final long serialVersionUID = 7122637163626243606L;
    private TableSchema tableSchema;
    private String createSql;
    private List<Map<String, Object>> dataList;
    private String insertSql;
    private String dataJson;
    private String javaEntityCode;
    private String javaObjectCode;
    private String typescriptTypeCode;
}
