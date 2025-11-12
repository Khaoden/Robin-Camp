package com.robincamp.movierating.config;

import com.robincamp.movierating.security.AuthInterceptor;
import com.robincamp.movierating.security.RaterIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final RaterIdInterceptor raterIdInterceptor;

    public WebConfig(AuthInterceptor authInterceptor, RaterIdInterceptor raterIdInterceptor) {
        this.authInterceptor = authInterceptor;
        this.raterIdInterceptor = raterIdInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/movies")
                .excludePathPatterns("/healthz", "/movies/**/rating");

        registry.addInterceptor(raterIdInterceptor)
                .addPathPatterns("/movies/**/ratings");
    }
}
