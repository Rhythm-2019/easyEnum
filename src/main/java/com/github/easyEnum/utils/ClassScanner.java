package com.github.easyEnum.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description Spring 扫描工具
 */
@Slf4j
public class ClassScanner {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private CachingMetadataReaderFactory metadataReaderFactory;
    private PathMatchingResourcePatternResolver resourcePatternResolver;
    private StandardEnvironment environment;

    private ResourcePatternResolver getResourcePatternResolver() {
        if (this.resourcePatternResolver == null) {
            this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
        }
        return this.resourcePatternResolver;
    }

    public final MetadataReaderFactory getMetadataReaderFactory() {
        if (this.metadataReaderFactory == null) {
            this.metadataReaderFactory = new CachingMetadataReaderFactory();
        }
        return this.metadataReaderFactory;
    }

    public final Environment getEnvironment() {
        if (this.environment == null) {
            this.environment = new StandardEnvironment();
        }
        return this.environment;
    }

    public Set<Class<?>> scanClass(String basePackage, Predicate<Class<?>> filterHandler) {
        // 参考 org/springframework/context/annotation/ClassPathScanningCandidateComponentProvider.java
        Set<Class<?>> candidates = new LinkedHashSet<>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
            Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
            boolean traceEnabled = log.isTraceEnabled();
            boolean debugEnabled = log.isDebugEnabled();
            for (Resource resource : resources) {
                if (traceEnabled) {
                    log.trace("Scanning " + resource);
                }
                try {
                    MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                    Class<?> clz = Class.forName(metadataReader.getClassMetadata().getClassName());
                    if (filterHandler.test(clz)) {
                        candidates.add(clz);
                    } else {
                        if (traceEnabled) {
                            log.trace("Ignored because not matching any filter: " + resource);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    if (traceEnabled) {
                        log.trace("Ignored non-readable " + resource + ": " + ex.getMessage());
                    }
                } catch (Throwable ex) {
                    throw new BeanDefinitionStoreException(
                            "Failed to read  class: " + resource, ex);
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(getEnvironment().resolveRequiredPlaceholders(basePackage));
    }
}
