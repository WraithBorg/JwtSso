package com.jsso.wt.common.jwt;


import com.alibaba.fastjson.JSON;
import com.jsso.wt.common.result.DockResult;

public class JwtUtilTest {
    public static void main(String[] args) throws Exception {
        testJwt4KeyPair();
    }

    /**
     * 测试动态key jwt
     */
    static void testJwt4KeyPair() {
        JwtClaim jwtClaim = new JwtClaim("008", "zxu", "4862");
        String jwtToken = new JwtUtil4KeyPair().createJWT(jwtClaim);
        DockResult<JwtClaim> dockResult = new JwtUtil4KeyPair().parseUser(jwtToken);
        System.out.println(JSON.toJSONString(dockResult));
    }
}
