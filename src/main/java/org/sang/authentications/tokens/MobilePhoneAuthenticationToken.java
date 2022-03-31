package org.sang.authentications.tokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MobilePhoneAuthenticationToken extends AbstractAuthenticationToken {
    // 登录身份，这里是手机号
    private Object principal;
    // 登录凭据，这里是手机验证码
    private Object credentials;

    /**
     * 构造方法
     * @param authorities 权限集合
     * @param principal 登录身份
     * @param credentials 登录凭据
     */
    public MobilePhoneAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }

    @Override
    public String getName() {
        return "mobilePhoneAuthenticationToken";
    }
}
