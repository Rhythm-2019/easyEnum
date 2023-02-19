package icu.rhythm.easyenum.core;

import icu.rhythm.easyenum.core.CodeBaseEnum;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description 包含所有 CodeBaseEnum 的引用
 */
public class CodeBaseEnumHolder {


    private Map<Class<? super CodeBaseEnum>, List<CodeBaseEnum>> holder = new HashMap<>();

    private Set<Class<? super CodeBaseEnum>> codeBaseEnums;
    public CodeBaseEnumHolder(Set<Class<? super CodeBaseEnum>> codeBaseEnums) {
        this.codeBaseEnums = codeBaseEnums;
        this.codeBaseEnums.forEach(this::add);
    }

    private void add(Class<? super CodeBaseEnum> clz) {
        this.holder.put(clz, Arrays.stream(clz.getEnumConstants()).map(e -> (CodeBaseEnum) e).collect(Collectors.toList()));

    }

    public Set<Class<? super CodeBaseEnum>> getCodeBaseEnums() {
        return codeBaseEnums;
    }

    public List<? super CodeBaseEnum> get(Class<? super CodeBaseEnum> clz) {
        return this.holder.get(clz);
    }

}
