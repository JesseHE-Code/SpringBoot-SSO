package com.example.ssoservernew.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author :hezhiqiang06
 * @Date :2019-09-02 21:03
 **/

public class FaceLoginToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2383092775910246006L;

    private final Object principal;

    public FaceLoginToken(String faceId) {
        super(null);
        this.principal = faceId;
        setAuthenticated(false);
    }

    public FaceLoginToken(Object principal,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        // must use super, as we override
        super.setAuthenticated(true);
    }
    /**
     *
     * @return
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
