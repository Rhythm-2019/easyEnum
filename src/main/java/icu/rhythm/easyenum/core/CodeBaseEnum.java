package icu.rhythm.easyenum.core;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description
 */
public interface CodeBaseEnum {
    /**
     * 获取 code
     * @return code
     */
    int getCode();

    /**
     * 获取 Name
     * @return name
     */
    String getName();

    /**
     * 获取描述信息
     * @return 描述信息
     */
    default String getDescription() {
        throw new NotImplementedException();
    }
}
