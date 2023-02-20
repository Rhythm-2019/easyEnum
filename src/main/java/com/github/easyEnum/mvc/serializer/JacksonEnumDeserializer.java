package com.github.easyEnum.mvc.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.github.easyEnum.CodeBaseEnum;
import com.github.easyEnum.core.DefaultCodeBaseEnumManager;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Rhythm-
 * @date 2023/2/20
 * @description Jackson 反序列化处理
 * <p>
 * 参考：
 * - https://www.cnblogs.com/kelelipeng/p/13972138.html
 * - https://www.baeldung.com/jackson-deserialization
 */
@JsonComponent
public class JacksonEnumDeserializer
        extends JsonDeserializer<Enum<? extends CodeBaseEnum>>
        implements ContextualDeserializer {

    private DefaultCodeBaseEnumManager codeBaseEnumHolder;

    private Class<?> rawClass;

    public JacksonEnumDeserializer(DefaultCodeBaseEnumManager defluatCodeBaseEnumManager) {
        this.codeBaseEnumHolder = defluatCodeBaseEnumManager;
    }


    public void setCodeBaseEnumHolder(DefaultCodeBaseEnumManager codeBaseEnumHolder) {
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

        return (Enum<? extends CodeBaseEnum>) this.codeBaseEnumHolder.findEnumConstants((Class<? extends CodeBaseEnum>) this.rawClass)
                .stream()
                .filter(e -> e.match(code))
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
