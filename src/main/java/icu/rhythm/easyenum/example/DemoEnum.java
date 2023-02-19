package icu.rhythm.easyenum.example;

import icu.rhythm.easyenum.core.CodeBaseEnum;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description
 */
public enum DemoEnum implements CodeBaseEnum {
    START(0, "开始"),
    STOP(1, "暂停"),
    RUNNING(2, "运行中"),
    ;

    private int code;
    private String name;

    DemoEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }
}
