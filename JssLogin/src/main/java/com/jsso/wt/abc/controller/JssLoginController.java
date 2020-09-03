package com.jsso.wt.abc.controller;

import com.jsso.wt.common.jwt.JwtClaim;
import com.jsso.wt.common.jwt.JwtUtil4KeyPair;
import com.jsso.wt.common.result.DockResult;
import com.jsso.wt.common.util.SprWebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jsso.wt.common.constant.CConst.*;

@Controller
public class JssLoginController {

    /**
     * 跳转到登录页
     */
    @GetMapping({"/Login/loginPage"})
    public String login() {
        return "login";
    }

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = "/Login/loginByUserName",produces = "application/json; charset=utf-8")
    public DockResult loginByUserName(HttpServletRequest request, HttpServletResponse response,
                                      String account, String password) {
        // validate TODO
        if (!(account.equals(LOGIN_DEFAULT_ACCOUNT) && password.equals(LOGIN_DEFAULT_PASSWORD))) {
            return DockResult.fail("用户名密码不正确！");
        }
        //
        JwtClaim jwtClaim = new JwtClaim(LOGIN_DEFAULT_USER_ID, LOGIN_DEFAULT_ACCOUNT, LOGIN_DEFAULT_PASSWORD);
        String jwtToken = JwtUtil4KeyPair.createJWT(jwtClaim);
        // 设置cookie
        response.addCookie(SprWebUtils.generatorCookie(jwtToken));

        return DockResult.success("登陆成功,可以访问以下资源:" +
                "127.0.0.1 www.abc.sso.com," +
                "127.0.0.1 www.mno.sso.com," +
                "127.0.0.1 www.xyz.sso.com");
    }

}
