package com.robincamp.movierating.security;

import com.robincamp.movierating.config.AppConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AppConfig appConfig;

    public AuthInterceptor(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        String expectedToken = appConfig.getAuth().getToken();

        if (expectedToken == null || expectedToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter()
                    .write("{\"code\":\"UNAUTHORIZED\",\"message\":\"Missing or invalid authentication information\"}");
            return false;
        }

        String token = authHeader.substring(7);
        if (!token.equals(expectedToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter()
                    .write("{\"code\":\"UNAUTHORIZED\",\"message\":\"Missing or invalid authentication information\"}");
            return false;
        }

        return true;
    }
}
