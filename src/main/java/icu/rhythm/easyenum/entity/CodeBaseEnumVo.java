package icu.rhythm.easyenum.entity;

import icu.rhythm.easyenum.core.CodeBaseEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Rhythm-2019
 * @date 2023/2/20
 * @description 返回实体
 */
@Data
@Accessors(chain = true)
public class CodeBaseEnumVo {

    private int code;
    private String description;

    public static CodeBaseEnumVo from(CodeBaseEnum codeBaseEnum) {
        return new CodeBaseEnumVo()
                .setCode(codeBaseEnum.getCode())
                .setDescription(codeBaseEnum.getDescription());
    }
}
