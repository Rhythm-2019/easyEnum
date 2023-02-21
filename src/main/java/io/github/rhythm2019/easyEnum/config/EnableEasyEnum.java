package io.github.rhythm2019.easyEnum.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description 开启配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfiguration.class)
public @interface EnableEasyEnum {
    /**
     * @return 枚举类所在的包
     */
    String[] basePackages();
}
