package com.litsynp.gateway;

import com.litsynp.gateway.dto.ApiKey;
import com.litsynp.gateway.util.AppConstants;
import com.litsynp.gateway.util.RedisHashComponent;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GatewayApplication {

    private RedisHashComponent redisHashComponent;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    public void initKeysToRedis() {
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("343C-ED08-4137-B27E",
                List.of(AppConstants.STUDENT_SERVICE_KEY, AppConstants.COURSE_SERVICE_KEY)));
        apiKeys.add(new ApiKey("Fa48-EF0C-427E-8CCF",
                List.of(AppConstants.COURSE_SERVICE_KEY)));

        List<Object> hashValues = redisHashComponent.getHashValues(AppConstants.RECORD_KEY);
        if (hashValues.isEmpty()) {
            apiKeys.forEach(it -> redisHashComponent.setHash(
                    AppConstants.RECORD_KEY, it.getKey(), it));
        }
    }

    public RouteLocater customRouterLocater()
}
