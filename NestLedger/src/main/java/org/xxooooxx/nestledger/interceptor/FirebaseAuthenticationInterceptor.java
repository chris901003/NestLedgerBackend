package org.xxooooxx.nestledger.interceptor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.xxooooxx.nestledger.exception.CustomException;
import org.xxooooxx.nestledger.exception.CustomExceptionEnum;
import org.xxooooxx.nestledger.utility.UserContext;

import java.util.Set;

@Slf4j
@Component
public class FirebaseAuthenticationInterceptor implements HandlerInterceptor {

    private static final Set<String> whitelist = Set.of("/", "/error");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (whitelist.contains(path)) { return true; }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                String uid = decodedToken.getUid();
                UserContext.setUid(uid);
                System.out.println("User ID: " + uid);
                log.info("User ID: " + uid + " authenticated successfully.");
                return true;
            } catch (FirebaseAuthException e) {
                throw new CustomException(CustomExceptionEnum.ACCOUNT_AUTHENTICATION_FAILED);
            }
        } else {
            throw new CustomException(CustomExceptionEnum.ACCOUNT_AUTHENTICATION_FAILED);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
