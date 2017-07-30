package com.git.toolbox.converter;

import com.git.toolbox.status.BaseByteEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class GeneralByteEnumConverterFactory implements ConverterFactory<String, BaseByteEnum> {


    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    @Override
    public <T extends BaseByteEnum> Converter<String, T> getConverter(Class<T> targetType) {

        Converter convereter=converterMap.get(targetType);
        if(convereter==null) {
            convereter=new ByteStrToByteEnumConverter<T>(targetType);
            converterMap.put(targetType,convereter);
        }

        return convereter;
    }

    /**
     * 将String类型的值转换为枚举类型的具体值.<br>
     * 在Spring MVC和Spring Boot中，由于从客户端接收到的请求都被视为String类型，<br>
     * 所以只能用String转枚举的converter
     * @author lzq
     *
     * @param <T>
     */
    class ByteStrToByteEnumConverter<T extends BaseByteEnum> implements Converter<String, T> {
        private final Class<T> enumType;
        /**存储某个Enum的所有值*/
        private Map<String, T> enumMap = new HashMap<>();

        public ByteStrToByteEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
            T[] enums = enumType.getEnumConstants();
            for(T e : enums) {
                enumMap.put(e.value() + "", e);
            }
        }

        @Override
        public T convert(String source) {
            T result = enumMap.get(source);
            if(result == null) {
                throw new IllegalArgumentException("No element matches " + source);
            }
            return result;
        }
    }

}
