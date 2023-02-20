package icu.rhythm.easyenum.exception;

/**
 * @author Rhythm-2019
 * @date 2023/2/20
 * @description 枚举不存在
 */
public class EnumNoFoundException extends RuntimeException {
    public EnumNoFoundException(String msg) {
        super(msg);
    }

    public EnumNoFoundException() {
        super();
    }
}
