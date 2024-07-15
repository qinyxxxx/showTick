package com.qyx.showtick.bo;

import com.qyx.showtick.common.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by Yuxin Qin on 7/15/24
 */
public class CurrentUserDetail implements UserDetails {

    //后台用户
    private final User user;
    //拥有资源列表
//    private final List<UserResource> resourceList;

    public CurrentUserDetail(User user) {
        this.user = user;
//        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

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

//    @Override
//    public boolean isEnabled() {
//        return user.getStatus().equals(1);
//    }
}
