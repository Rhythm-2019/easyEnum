package icu.rhythm.easyenum.core;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description 包含所有 CodeBaseEnum 的引用
 */
@Data
public class CodeBaseEnumHolder {

    private Map<Class<? super CodeBaseEnum>, List<CodeBaseEnum>> codeBaseEnumMap = new HashMap<>();

    private Set<Class<? super CodeBaseEnum>> codeBaseEnums;

    public CodeBaseEnumHolder(Set<Class<? super CodeBaseEnum>> codeBaseEnums) {
        this.codeBaseEnums = codeBaseEnums;
        this.codeBaseEnums.forEach(this::add);
    }

    private void add(Class<? super CodeBaseEnum> clz) {
        this.codeBaseEnumMap.put(clz, Arrays.stream(clz.getEnumConstants()).map(e -> (CodeBaseEnum) e).collect(Collectors.toList()));

    }

    public Set<Class<? super CodeBaseEnum>> getCodeBaseEnums() {
        return codeBaseEnums;
    }

    public List<CodeBaseEnum> get(Class<? super CodeBaseEnum> clz) {
        return this.codeBaseEnumMap.get(clz);
    }

    public CodeBaseEnum getEnumByCode(Class<?> clz, int code) {
        return this.codeBaseEnumMap.get(clz)
                .stream()
                .filter(codeBaseEnum -> codeBaseEnum.getCode() == code)
                .findFirst()
                .orElse(null);
    }
}
