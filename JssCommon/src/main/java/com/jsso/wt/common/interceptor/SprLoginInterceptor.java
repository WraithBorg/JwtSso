package com.jsso.wt.common.interceptor;

import com.jsso.wt.common.jwt.JwtClaim;
import com.jsso.wt.common.jwt.JwtUtil4KeyPair;
import com.jsso.wt.common.result.DockResult;
import com.jsso.wt.common.util.SprWebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jsso.wt.common.constant.CConst.*;

public class SprLoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SprLoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.warn("进入拦截器：" + request.getContextPath() + "//" + request.getRequestURL());
        // 根据cookie获取token
        String jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            LOGGER.error("用户不存在,请重新登录");
            response.sendRedirect(LOGIN_PAGE);
            return false;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(CO_JWT_TOKEN)) {
                jwtToken = cookie.getValue();
                break;
            }
        }
        if (jwtToken == null) {
            LOGGER.error("用户不存在,请重新登录");
            response.sendRedirect(LOGIN_PAGE);
            return false;
        }

        // 根据jwt token获取用户信息
        DockResult<JwtClaim> jwtClaimDockResult = JwtUtil4KeyPair.parseUser(jwtToken);
        if (jwtClaimDockResult.error()) {
            LOGGER.error(jwtClaimDockResult.getMessage());
            response.sendRedirect(LOGIN_PAGE);
            return false;
        }
        // warn 表示需要重置token
        if (jwtClaimDockResult.warn()) {
            String jwtNewToken = jwtClaimDockResult.getMessage();
            LOGGER.warn("重置TOKEN:" + jwtNewToken);
            response.addCookie(SprWebUtils.generatorCookie(jwtNewToken));
        }
        // 校验用户信息是否有效
        JwtClaim jwtClaim = jwtClaimDockResult.getData();
        if (!(jwtClaim.getPassword().equals(LOGIN_DEFAULT_PASSWORD) && jwtClaim.getAccount().equals(LOGIN_DEFAULT_ACCOUNT))) {
            LOGGER.error("用户密码不正确,请重新登录");
            response.sendRedirect(LOGIN_PAGE);
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
