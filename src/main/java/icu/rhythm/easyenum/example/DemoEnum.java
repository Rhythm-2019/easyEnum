package icu.rhythm.easyenum.example;

import icu.rhythm.easyenum.core.CodeBaseEnum;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description
 */
public enum DemoEnum implements CodeBaseEnum {
    START(0, "开始"),
    STOP(10, "暂停"),
    RUNNING(100, "运行中"),
    ;

    private int code;
    private String desc;

    DemoEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
}
