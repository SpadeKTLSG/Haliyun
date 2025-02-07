package xyz.spc.serve.auxiliary.config.serial;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import xyz.spc.common.funcpack.BaseEnum;
import xyz.spc.common.funcpack.exception.ServiceException;


public class BaseEnumJsonSerializer<T extends Enum<T> & BaseEnum<T>> extends JsonDeserializer<T> {

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) {
        throw new ServiceException("没有实现反序列化");
    }
}
