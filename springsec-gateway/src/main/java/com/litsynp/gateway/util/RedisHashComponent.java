package com.litsynp.gateway.util;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHashComponent {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setHash(String key, Object hashKey, Object value) {
        Map ruleHash = MapperUtils.objectMapper(value, Map.class);
        redisTemplate.opsForHash().put(key, hashKey, ruleHash);
    }

    public List<Object> getHashValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    public Object getHash(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }
}
