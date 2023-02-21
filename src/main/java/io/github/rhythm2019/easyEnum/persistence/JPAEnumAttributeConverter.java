package io.github.rhythm2019.easyEnum.persistence;

import io.github.rhythm2019.easyEnum.CodeBaseEnum;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description JPA 通用枚举转换器
 */
public abstract class JPAEnumAttributeConverter<E extends Enum<E> & CodeBaseEnum>
        implements AttributeConverter<E, Integer> {
    private final Map<Integer, E> codeBaseEnums;

    public JPAEnumAttributeConverter(E[] commonEnums) {
        this(Arrays.asList(commonEnums));
    }

    public JPAEnumAttributeConverter(List<E> commonEnums) {
        this.codeBaseEnums = new HashMap<>(commonEnums.size());
        commonEnums.forEach(codeBaseEnum -> this.codeBaseEnums.put(codeBaseEnum.getCode(), codeBaseEnum));
    }

    @Override
    public Integer convertToDatabaseColumn(E e) {
        return e.getCode();
    }

    @Override
    public E convertToEntityAttribute(Integer code) {
        return this.codeBaseEnums.get(code);
    }
}