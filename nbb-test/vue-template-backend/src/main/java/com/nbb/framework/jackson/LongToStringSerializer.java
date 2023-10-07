package com.nbb.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Long转string 避免返回前端丢失精度
 */
public class LongToStringSerializer extends JsonSerializer<Long> {


    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null && value.toString().length() > 16) {
            gen.writeString(value.toString());
        } else {
            gen.writeNumber(value);
        }
    }
}
