package icu.rhythm.easyenum.config;

import cn.hutool.core.util.StrUtil;
import icu.rhythm.easyenum.annotation.EnableEasyEnum;
import icu.rhythm.easyenum.core.CodeBaseEnum;
import icu.rhythm.easyenum.core.CodeBaseEnumHolder;
import icu.rhythm.easyenum.core.ClassScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description
 */
@Slf4j
@ComponentScan(basePackages = "icu.rhythm.easyenum")
public class AutoConfiguration implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        register(registry, createCodeBaseEnumBeanDifinition(importingClassMetadata));
    }

    private void register(BeanDefinitionRegistry registry, RootBeanDefinition beanDefinition) {
        if (beanDefinition == null) {
            return;
        }
        registry.registerBeanDefinition(StrUtil.lowerFirst(beanDefinition.getBeanClass().getSimpleName()), beanDefinition);
    }

    private RootBeanDefinition createCodeBaseEnumBeanDifinition(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableEasyEnum.class.getName());
        if (annotationAttributes == null) {
            log.error("EnableEasyEnum no found, it is. this is impossible");
            return null;
        }
        Set<Class<?>> classes = new HashSet<>();
        String[] basePackages = (String[]) annotationAttributes.getOrDefault("basePackages", new String[]{});
        for (String basePackage : basePackages) {
            // 加载所有实现了 CodeBaseEnum 的元组
            classes.addAll( new ClassScanner().scanClass(basePackage, clz -> clz.isEnum() &&
                    Arrays.asList(clz.getInterfaces()).contains(CodeBaseEnum.class)));
        }
        // 将 CodeBaseEnumHolder 注册到容器中
        return new RootBeanDefinition(CodeBaseEnumHolder.class, () -> new CodeBaseEnumHolder(
                classes.stream().map(clz -> (Class<CodeBaseEnum>) clz).collect(Collectors.toSet())
        ));

    }
}
