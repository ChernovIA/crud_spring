package net.configuration;

import net.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service("CustomSuccesshandler")
public class CustomSuccesshandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        if (authentication.isAuthenticated()){

            boolean isAdmin = false;
            for(Role role: (List<Role>)authentication.getAuthorities()){
                if (role.getAuthority().equals("ROLE_ADMIN")){
                    isAdmin = true;
                }
            }
            if (isAdmin){
                httpServletResponse.sendRedirect("/administrator/usersList");
            }
            else{
                httpServletResponse.sendRedirect("/profile");
            }
        }
    }
}
