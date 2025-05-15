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
        registry.addInterceptor(firebaseAuthenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                    "/v3/api-docs/**",        // OpenAPI 文件
                    "/swagger-ui/**",         // Swagger UI 靜態資源
                    "/swagger-ui.html",       // Swagger UI 頁面
                    "/webjars/**",            // 靜態資源 (CSS/JS)
                    "/swagger-resources/**"   // Swagger 資源
                );
    }
}
