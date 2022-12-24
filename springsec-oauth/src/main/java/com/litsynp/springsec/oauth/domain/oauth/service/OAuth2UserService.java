package com.litsynp.springsec.oauth.domain.oauth.service;

import com.litsynp.springsec.oauth.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.oauth.domain.member.dao.MemberRepository;
import com.litsynp.springsec.oauth.domain.member.domain.Member;
import com.litsynp.springsec.oauth.domain.oauth.domain.OAuth2UserInfo;
import com.litsynp.springsec.oauth.domain.oauth.domain.OAuth2UserInfoFactory;
import com.litsynp.springsec.oauth.domain.oauth.domain.ProviderType;
import com.litsynp.springsec.oauth.global.error.exception.OAuth2ProviderMismatchException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Register or update an OAuth member
     */
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest,
        OAuth2User oAuth2User) {
        // Check the OAuth provider type
        ProviderType providerType = ProviderType.valueOf(
            oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());

        // Get user information from the OAuth provider
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType,
            oAuth2User.getAttributes());

        // Check for existing member
        Optional<Member> savedMemberOptional = memberRepository.findByOauthId(userInfo.getId());
        Member savedMember;
        if (savedMemberOptional.isPresent()) {
            // If member exists, use it
            savedMember = savedMemberOptional.get();

            // If attempted provider type does not match saved provided type
            if (providerType != savedMember.getProviderType()) {
                throw new OAuth2ProviderMismatchException(providerType,
                    savedMember.getProviderType());
            }

            // Update member information from OAuth provider
            savedMember.update(userInfo);
        } else {
            // If member doesn't exist, create one
            savedMember = memberRepository.save(new Member(userInfo, providerType));
        }

        return UserDetailsVo.from(savedMember, oAuth2User.getAttributes());
    }
}
