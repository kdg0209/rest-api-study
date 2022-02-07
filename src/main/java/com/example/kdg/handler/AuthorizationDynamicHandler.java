package com.example.kdg.handler;

import com.example.kdg.common.CustomUserDetailService;
import com.example.kdg.dao.AuthDao;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@Component
public class AuthorizationDynamicHandler {

    public boolean isAuthorization(HttpServletRequest request, Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth.getPrincipal() instanceof AuthDao)){
            return false;
        }

        AuthDao user = (AuthDao) auth.getPrincipal();

        if(user == null){
            return false;
        }

        Iterator<? extends GrantedAuthority> iterator = user.getAuthorities().iterator();

        while (iterator.hasNext()) {
            GrantedAuthority authority = iterator.next();

            if(!"ROLE_ADMIN".equals(authority.getAuthority())){
                return false;
            }
        }

        return true;
    }
}
