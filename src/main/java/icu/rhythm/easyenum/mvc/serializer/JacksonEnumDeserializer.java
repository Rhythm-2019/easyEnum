package icu.rhythm.easyenum.mvc.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import icu.rhythm.easyenum.core.CodeBaseEnum;
import icu.rhythm.easyenum.core.CodeBaseEnumHolder;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Rhythm-
 * @date 2023/2/20
 * @description Jackson 反序列化处理
 *
 * 参考：
 *  - https://www.cnblogs.com/kelelipeng/p/13972138.html
 *  - https://www.baeldung.com/jackson-deserialization
 */
@JsonComponent
public class JacksonEnumDeserializer
        extends JsonDeserializer<Enum<? extends CodeBaseEnum>>
        implements ContextualDeserializer {

    private CodeBaseEnumHolder codeBaseEnumHolder;

    private Class<?> rawClass;

    public JacksonEnumDeserializer(CodeBaseEnumHolder codeBaseEnumHolder) {
        this.codeBaseEnumHolder = codeBaseEnumHolder;
    }


    public void setCodeBaseEnumHolder(CodeBaseEnumHolder codeBaseEnumHolder) {
        this.codeBaseEnumHolder = codeBaseEnumHolder;
    }

    public JacksonEnumDeserializer setRawClass(Class<?> rawClass) {
        this.rawClass = rawClass;
        return this;
    }

    @Override
    public Enum<? extends CodeBaseEnum> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (!Arrays.asList(this.rawClass.getInterfaces()).contains(CodeBaseEnum.class)) {
            return null;
        }
        int code = Integer.parseInt(p.getValueAsString());

        return (Enum<? extends CodeBaseEnum>) this.codeBaseEnumHolder.get((Class<? super CodeBaseEnum>) this.rawClass)
                .stream()
                .filter(e -> e.getCode() == code)
                .findFirst()
                .orElse(null);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        // 注意由于 JsonDeserializer 不受 Spring 管理，所以需要手动传入 codeBaseEnumHolder
        return new JacksonEnumDeserializer(this.codeBaseEnumHolder)
                .setRawClass(property.getType().getRawClass());
    }
}
