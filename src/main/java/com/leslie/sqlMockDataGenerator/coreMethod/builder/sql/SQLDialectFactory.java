package com.leslie.sqlMockDataGenerator.coreMethod.builder.sql;

import com.leslie.sqlMockDataGenerator.common.ErrorCode;
import com.leslie.sqlMockDataGenerator.exception.BusinessException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SQL dialect factory
 * Factory + singleton mode
 */
public class SQLDialectFactory {

    /**
     * className => SQL dialect factory
     */
    private static final Map<String, SQLDialect> DIALECT_POOL = new ConcurrentHashMap<>();

    private SQLDialectFactory() {
    }

    /**
     * get dialect
     *
     * @param className
     * @return
     */
    public static SQLDialect getDialect(String className) {
        SQLDialect dialect = DIALECT_POOL.get(className);
        if (null == dialect) {
            synchronized (className.intern()) {
                dialect = DIALECT_POOL.computeIfAbsent(className,
                        key -> {
                            try {
                                return (SQLDialect) Class.forName(className).newInstance();
                            } catch (Exception e) {
                                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                            }
                        });
            }
        }
        return dialect;
    }
}
