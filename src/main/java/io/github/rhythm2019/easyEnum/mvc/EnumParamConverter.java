package io.github.rhythm2019.easyEnum.mvc;

import io.github.rhythm2019.easyEnum.CodeBaseEnum;
import io.github.rhythm2019.easyEnum.core.DefaultCodeBaseEnumManager;
import io.github.rhythm2019.easyEnum.exception.EnumNoFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description 处理入参中的 @RequestParam 和 @PathVariable
 */

@Order(1)
@Component
public class EnumParamConverter implements ConditionalGenericConverter {

    @Resource
    private DefaultCodeBaseEnumManager codeBaseEnumHolder;

    /**
     * 是否应该选择当前正在考虑的从sourceType到targetType的转换？
     *
     * @param sourceType the type descriptor of the field we are converting from
     * @param targetType the type descriptor of the field we are converting to
     * @return 是否进行转换，true 则执行 convert 方法
     */
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return codeBaseEnumHolder.getCodeBaseEnums().contains(targetType.getType());
    }

    /**
     * 返回此转换器可以在其间转换的源类型和目标类型
     *
     * @return 继承了 CodeBaseEnum 的枚举
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return codeBaseEnumHolder.getCodeBaseEnums()
                .stream()
                .map(e -> new ConvertiblePair(String.class, e))
                .collect(Collectors.toSet());
    }

    /**
     * 参数转换
     *
     * @param source     the source object to convert (may be {@code null})
     * @param sourceType the type descriptor of the field we are converting from
     * @param targetType the type descriptor of the field we are converting to
     * @return 转换后的结果
     */
    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        int code = Integer.parseInt(String.valueOf(source));
        CodeBaseEnum codeBaseEnum = this.codeBaseEnumHolder.findEnumConstantByCode((Class<? extends CodeBaseEnum>) targetType.getType(), code);
        if (codeBaseEnum == null) {
            throw new EnumNoFoundException();
        }
        return codeBaseEnum;
    }
}
