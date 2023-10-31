package com.litsynp.gateway.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MapperUtils {

    private ObjectMapper mapper = new ObjectMapper();

    public <T> T objectMapper(Object object, Class<T> contentClass) {
        return mapper.convertValue(object, contentClass);
    }
}
