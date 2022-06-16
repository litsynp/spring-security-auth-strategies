package com.litsynp.springsec.oauth.domain.oauth.domain;

import com.litsynp.springsec.oauth.domain.oauth.domain.impl.FacebookOAuth2UserInfo;
import com.litsynp.springsec.oauth.domain.oauth.domain.impl.GoogleOAuth2UserInfo;
import com.litsynp.springsec.oauth.domain.oauth.domain.impl.KakaoOAuth2UserInfo;
import com.litsynp.springsec.oauth.domain.oauth.domain.impl.NaverOAuth2UserInfo;
import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType,
            Map<String, Object> attributes) {
        return switch (providerType) {
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case FACEBOOK -> new FacebookOAuth2UserInfo(attributes);
            case NAVER -> new NaverOAuth2UserInfo(attributes);
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };
    }
}
