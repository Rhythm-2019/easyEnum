package io.github.rhythm2019.easyEnum;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description 枚举通用接口
 */
public interface CodeBaseEnum {
    /**
     * 获取 code
     *
     * @return code
     */
    int getCode();

    /**
     * 获取描述信息
     *
     * @return 描述信息
     */
    default String getDescription() {
        return null;
    }

    /**
     * 检测是否匹配
     * @param code code
     * @return 是否匹配
     */
    default boolean match(int code) {
        return getCode() == code;
    }
}
