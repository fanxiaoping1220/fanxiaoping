package com.langchain4j2.entity.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SQL Server LocalDateTime 类型处理器
 * 绕过 JDBC 驱动在 TLS 1.0 环境下的 Timestamp 转换 bug
 */
public class SqlServerLocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER_WITH_MS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter FORMATTER_NO_MS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.format(FORMATTER_WITH_MS));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dateString = rs.getString(columnName);
        return parseDate(dateString);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String dateString = rs.getString(columnIndex);
        return parseDate(dateString);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String dateString = cs.getString(columnIndex);
        return parseDate(dateString);
    }

    private LocalDateTime parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        
        try {
            // 尝试带毫秒的格式
            return LocalDateTime.parse(dateString, FORMATTER_WITH_MS);
        } catch (Exception e1) {
            try {
                // 尝试不带毫秒的格式
                return LocalDateTime.parse(dateString, FORMATTER_NO_MS);
            } catch (Exception e2) {
                throw new RuntimeException("无法解析日期格式: " + dateString, e2);
            }
        }
    }
}
