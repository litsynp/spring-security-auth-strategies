package com.litsynp.springsec.oauth.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litsynp.springsec.oauth.config.SpringSecurityWebAuxTestConfig;
import com.litsynp.springsec.oauth.domain.auth.service.AuthService;
import com.litsynp.springsec.oauth.domain.oauth.service.OAuth2UserService;
import com.litsynp.springsec.oauth.global.auth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.litsynp.springsec.oauth.global.auth.JwtAuthEntryPoint;
import com.litsynp.springsec.oauth.global.auth.JwtAuthTokenFilter;
import com.litsynp.springsec.oauth.global.auth.OAuth2AuthenticationFailureHandler;
import com.litsynp.springsec.oauth.global.auth.OAuth2AuthenticationSuccessHandler;
import com.litsynp.springsec.oauth.global.config.AuthProperties;
import com.litsynp.springsec.oauth.global.config.CorsProperties;
import com.litsynp.springsec.oauth.global.config.SecurityConfig;
import com.litsynp.springsec.oauth.global.util.JwtUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Import({SecurityConfig.class, JwtAuthEntryPoint.class, AuthProperties.class, CorsProperties.class,
        JwtAuthTokenFilter.class, JwtUtil.class, SpringSecurityWebAuxTestConfig.class,
        ObjectMapper.class})
@TestPropertySource(properties = {
        "app.auth.jwt-secret=foobar",
        "app.auth.jwt-access-expiration-ms=3600000",
        "app.auth.jwt-refresh-expiration-ms=86400000"})
@EnableConfigurationProperties
public abstract class ApiMockControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected OAuth2UserService oAuth2UserService;

    @MockBean
    protected OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @MockBean
    protected OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @MockBean
    protected HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;
}
