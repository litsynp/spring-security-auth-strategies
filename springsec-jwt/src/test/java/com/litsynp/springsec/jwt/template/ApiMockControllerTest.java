package com.litsynp.springsec.jwt.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litsynp.springsec.jwt.config.SpringSecurityWebAuxTestConfig;
import com.litsynp.springsec.jwt.domain.auth.service.AuthService;
import com.litsynp.springsec.jwt.global.auth.JwtAuthEntryPoint;
import com.litsynp.springsec.jwt.global.auth.JwtAuthTokenFilter;
import com.litsynp.springsec.jwt.global.config.SecurityConfig;
import com.litsynp.springsec.jwt.global.util.JwtUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Import({SecurityConfig.class, JwtAuthEntryPoint.class, JwtAuthTokenFilter.class, JwtUtil.class,
        SpringSecurityWebAuxTestConfig.class, ObjectMapper.class})
public abstract class ApiMockControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;
}
