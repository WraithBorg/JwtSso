package com.jsso.wt.common.util;

import javax.servlet.http.Cookie;

import static com.jsso.wt.common.constant.CConst.CO_JWT_TOKEN;
import static com.jsso.wt.common.constant.CConst.CO_MAX_EXPIRE;

public class SprWebUtils {
    /**
     * 创建Cookie
     */
    public static Cookie generatorCookie(String coValue){
        Cookie cookie = new Cookie(CO_JWT_TOKEN, coValue);
        cookie.setMaxAge(CO_MAX_EXPIRE);
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setVersion(0);
        cookie.setHttpOnly(true);
        cookie.setDomain("sso.com");
        return cookie;
    }
}
