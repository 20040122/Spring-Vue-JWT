package com.project.springboot_jwt.Interceptor;

import com.project.springboot_jwt.Utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.IOException;

@Configuration
@Component
public class tokenInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(tokenInterceptor.class);
    private final JwtUtil jwtUtil;
    @Autowired
    public tokenInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        logger.info("Request method: {}", method);
        // 对于 OPTIONS 请求，直接返回 true，不做拦截处理
        if ("OPTIONS".equals(method)) {
            return true;
        }
        // 从请求头中获取 token
        String token = request.getHeader("token");
        logger.info("Token from request header: {}", token);
        if (token == null || token.isEmpty()) {
            logger.warn("Token is null or empty");
            sendUnauthorizedResponse(response, "Token is null or empty");
            return false;
        }
        try {
            // 验证 token 的有效性
            if (!jwtUtil.validateToken(token)) {
                logger.warn("Invalid token");
                sendUnauthorizedResponse(response, "Invalid token");
                return false;
            }
        } catch (JwtException e) {
            logger.error("JWT validation error: {}", e.getMessage());
            sendUnauthorizedResponse(response, "JWT validation error");
            return false;
        }
        // 如果 token 验证通过，返回 true，请求继续向下执行
        return true;
    }
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

}
