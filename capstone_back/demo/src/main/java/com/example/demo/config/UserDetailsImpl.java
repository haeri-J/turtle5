package com.example.demo.config;

import com.example.demo.entity.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private Client user;

    public UserDetailsImpl(Client user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 모든 사용자에게 "ROLE_USER" 권한을 부여한다고 가정합니다.
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // Client 엔티티의 PW 엔티티로부터 비밀번호를 가져옵니다.
        return user.getPasswordId().getPasswordHash();
    }

    @Override
    public String getUsername() {
        // 사용자의 이메일을 사용자명으로 사용합니다.
        return user.getEmail();
    }

    // 나머지 메소드들(isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled)
    // 은 기본적으로 true를 반환합니다. 실제 애플리케이션에서는 이러한 상태들을 관리할 필요가 있을 수 있으며,
    // 그 경우 Client 엔티티에 이러한 상태를 나타내는 필드들을 추가하고, 해당 필드들을 기반으로
    // 값들을 반환하도록 구현해야 합니다.

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
