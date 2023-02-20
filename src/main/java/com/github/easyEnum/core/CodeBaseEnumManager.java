package com.github.easyEnum.core;

import com.github.easyEnum.CodeBaseEnum;

import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2023/2/20
 * @description 用于定义 Manager 的功能
 */
public interface CodeBaseEnumManager {

    /**
     * 将枚举注册到 manager 中
     *
     * @param codebaseEnum 枚举
     */
    void register(Class<? extends CodeBaseEnum> codebaseEnum);

    /**
     * 获取 codebaseEnum 的所有实例
     *
     * @param codebaseEnum 枚举类型
     * @return 所有实例
     */
    List<CodeBaseEnum> findEnumConstants(Class<? extends CodeBaseEnum> codebaseEnum);

    /**
     * 根据 code 获取枚举实例
     *
     * @param codeBaseEnum 枚举类型
     * @param code         code
     * @return
     */
    CodeBaseEnum findEnumConstantByCode(Class<? extends CodeBaseEnum> codeBaseEnum, int code);
}
