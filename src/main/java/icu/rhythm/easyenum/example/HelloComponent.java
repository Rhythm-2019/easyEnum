package icu.rhythm.easyenum.example;

import icu.rhythm.easyenum.core.CodeBaseEnumHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description
 */
@Component
public class HelloComponent {

    @Resource
    private CodeBaseEnumHolder codeBaseEnumHolder;


    @PostConstruct
    public void init() {
        codeBaseEnumHolder.getCodeBaseEnums().forEach(System.out::println);
    }

}
