package io.github.rhythm2019.easyEnum.mvc.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.rhythm2019.easyEnum.CodeBaseEnum;
import io.github.rhythm2019.easyEnum.CodeBaseEnumVo;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author Rhythm-
 * @date 2023/2/20
 * @description Jackson 反序列化处理
 * <p>
 * 参考：https://www.cnblogs.com/kelelipeng/p/13972138.html
 */
@JsonComponent
public class JacksonEnumSerializer extends JsonSerializer<CodeBaseEnum> {

    @Override
    public void serialize(CodeBaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(CodeBaseEnumVo.from(value));
    }
}
