package com.jsso.wt.common.constant;

/**
 * 常用常量
 */
public class CConst {
    // login
    public static final String LOGIN_DEFAULT_USER_ID = "007";
    public static final String LOGIN_DEFAULT_ACCOUNT = "zxu";
    public static final String LOGIN_DEFAULT_PASSWORD = "4862";

    public static final String LOGIN_PAGE = "http://www.login.sso.com:8081/Login/loginPage";
    // cookie
    public static final String CO_JWT_TOKEN = "jwttoken";
    public static final int CO_MAX_EXPIRE = 2 * 60 * 60;
    // JWT
    public static final String JWT_CID = "JWT_USER_ID";
    public static final String JWT_ACCOUNT = "JWT_ACCOUNT";
    public static final String JWT_PASSWORD = "JWT_PASSWORD";

    public static final Long JWT_DEFAULT_EXPIRE = 60 * 60 * 1000L;
    public static final Long JWT_DEFAULT_ALLOW_MAX_EXPIRE = 2 * 60 * 60L;
}
