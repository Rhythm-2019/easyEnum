package com.github.easyEnum.core;

import com.github.easyEnum.CodeBaseEnum;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description 包含所有 CodeBaseEnum 的引用
 */
@Data
public class DefaultCodeBaseEnumManager implements CodeBaseEnumManager {

    private Map<Class<? extends CodeBaseEnum>, List<CodeBaseEnum>> codeBaseEnumMap = new HashMap<>();

    private Set<Class<? extends CodeBaseEnum>> codeBaseEnums;

    public DefaultCodeBaseEnumManager(Set<Class<? extends CodeBaseEnum>> codeBaseEnums) {
        this.codeBaseEnums = codeBaseEnums;
        this.codeBaseEnums.forEach(this::register);
    }

    public CodeBaseEnum findEnumConstantByCode(Class<? extends CodeBaseEnum> codeBaseEnum, int code) {
        return this.codeBaseEnumMap.get(codeBaseEnum)
                .stream()
                .filter(e -> e.match(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void register(Class<? extends CodeBaseEnum> codebaseEnum) {
        this.codeBaseEnumMap.put(codebaseEnum, Arrays.stream(codebaseEnum.getEnumConstants()).map(e -> (CodeBaseEnum) e).collect(Collectors.toList()));
    }

    @Override
    public List<CodeBaseEnum> findEnumConstants(Class<? extends CodeBaseEnum> codebaseEnum) {
        return this.codeBaseEnumMap.get(codebaseEnum);
    }
}
