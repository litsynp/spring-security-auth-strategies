package com.litsynp.springsec.oauth.domain.oauth.domain;

import java.util.Map;

/**
 * Abstract class to contain common OAuth user information
 */
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    protected OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Return unique user ID provided by OAuth provider
     *
     * @return unique user ID
     */
    public abstract String getId();

    /**
     * Return name of the user provided by OAuth provider
     *
     * @return name of the user
     */
    public abstract String getName();

    /**
     * Return user email provided by OAuth provider
     *
     * @return user email
     */
    public abstract String getEmail();

    /**
     * Return user profile image URL provided by OAuth provider
     *
     * @return user profile image URL
     */
    public abstract String getImageUrl();
}
