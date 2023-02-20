package icu.rhythm.easyenum.mvc.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import icu.rhythm.easyenum.core.CodeBaseEnum;
import icu.rhythm.easyenum.core.CodeBaseEnumHolder;
import icu.rhythm.easyenum.entity.CodeBaseEnumVo;
import org.springframework.boot.jackson.JsonComponent;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author Rhythm-
 * @date 2023/2/20
 * @description Jackson 反序列化处理
 *
 * 参考：https://www.cnblogs.com/kelelipeng/p/13972138.html
 */
@JsonComponent
public class JacksonEnumSerializer extends JsonSerializer<CodeBaseEnum> {

    @Override
    public void serialize(CodeBaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(CodeBaseEnumVo.from(value));
    }
}
