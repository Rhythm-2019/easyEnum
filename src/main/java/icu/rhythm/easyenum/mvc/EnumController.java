package icu.rhythm.easyenum.mvc;

import icu.rhythm.easyenum.core.CodeBaseEnum;
import icu.rhythm.easyenum.core.CodeBaseEnumHolder;
import icu.rhythm.easyenum.entity.CodeBaseEnumVo;
import icu.rhythm.easyenum.example.DemoEnum;
import icu.rhythm.easyenum.exception.EnumNoFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description
 */
@RestController
@RequestMapping("/easyEnum")
public class EnumController {

    @Resource
    private CodeBaseEnumHolder codeBaseEnumHolder;

    @GetMapping("/all")
    public Map<String, List<CodeBaseEnumVo>> getAll() {
        HashMap<String, List<CodeBaseEnumVo>> result = new HashMap<>();
        this.codeBaseEnumHolder.getCodeBaseEnumMap()
                .forEach((k, v) -> {
                    result.put(k.getSimpleName(), v.stream()
                                                    .map(CodeBaseEnumVo::from)
                                                    .collect(Collectors.toList()));
                });
        return result;
    }

    @GetMapping("/all/type")
    public List<String> getAllType() {
        return this.codeBaseEnumHolder.getCodeBaseEnumMap()
                .keySet()
                .stream()
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
    }

    @GetMapping("/{typeName}")
    public List<CodeBaseEnumVo> getByType(@PathVariable("typeName") String typeName) {
        return this.codeBaseEnumHolder.getCodeBaseEnumMap()
                .keySet()
                .stream()
                .filter(clz -> clz.getSimpleName().equals(typeName))
                .findFirst()
                .map(clz -> this.codeBaseEnumHolder.get(clz)
                        .stream()
                        .map(CodeBaseEnumVo::from)
                        .collect(Collectors.toList()))
                .orElseThrow(EnumNoFoundException::new);
    }
}
