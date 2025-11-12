package com.robincamp.movierating.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RaterIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String raterId = request.getHeader("X-Rater-Id");

        if (raterId == null || raterId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter()
                    .write("{\"code\":\"UNAUTHORIZED\",\"message\":\"Missing or invalid authentication information\"}");
            return false;
        }

        return true;
    }
}
