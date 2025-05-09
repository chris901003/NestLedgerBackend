package org.xxooooxx.nestledger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xxooooxx.nestledger.interceptor.FirebaseAuthenticationInterceptor;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Autowired
    FirebaseAuthenticationInterceptor firebaseAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(firebaseAuthenticationInterceptor).addPathPatterns("/**");
    }
}
