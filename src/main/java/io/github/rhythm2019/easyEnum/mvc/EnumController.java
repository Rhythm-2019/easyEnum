package io.github.rhythm2019.easyEnum.mvc;

import io.github.rhythm2019.easyEnum.CodeBaseEnumVo;
import io.github.rhythm2019.easyEnum.core.DefaultCodeBaseEnumManager;
import io.github.rhythm2019.easyEnum.exception.EnumNoFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description 枚举字典接口
 */
@RestController
@RequestMapping("/easyEnum")
public class EnumController {

    @Resource
    private DefaultCodeBaseEnumManager codeBaseEnumHolder;

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
                .map(clz -> this.codeBaseEnumHolder.findEnumConstants(clz)
                        .stream()
                        .map(CodeBaseEnumVo::from)
                        .collect(Collectors.toList()))
                .orElseThrow(EnumNoFoundException::new);
    }
}
