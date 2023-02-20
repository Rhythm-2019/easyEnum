package com.github.easyEnum.persistence;

import com.github.easyEnum.CodeBaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rhythm-2019
 * @date 2023/2/20
 * @description Mybatis 类型转换器
 */
public abstract class MybatisEnumTypeHandler<T extends Enum<?> & CodeBaseEnum> extends BaseTypeHandler<T> {

    private  Map<Integer, T> codeBaseEnums;

    public MybatisEnumTypeHandler(T[] codeBaseEnums) {
        this.codeBaseEnums = new HashMap<>(codeBaseEnums.length);
        Arrays.stream(codeBaseEnums)
                .forEach(codeBaseEnum -> this.codeBaseEnums.put(codeBaseEnum.getCode(), codeBaseEnum));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return codeBaseEnums.get(rs.getInt(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return codeBaseEnums.get(rs.getInt(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return codeBaseEnums.get(cs.getInt(columnIndex));
    }

}
