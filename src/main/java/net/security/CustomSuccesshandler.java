package net.security;

import net.model.Role;
import net.model.RolesTypes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service("CustomSuccesshandler")
public class CustomSuccesshandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        if (authentication.isAuthenticated()){

            if (authentication.getAuthorities().contains(new Role(RolesTypes.ADMIN))){
                httpServletResponse.sendRedirect("/administrator/usersList");
            }
            else{
                httpServletResponse.sendRedirect("/profile");
            }
        }
    }
}
