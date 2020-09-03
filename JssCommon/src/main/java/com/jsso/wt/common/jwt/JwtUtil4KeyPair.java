package com.jsso.wt.common.jwt;


import com.jsso.wt.common.result.DockResult;
import com.jsso.wt.common.util.rsa.PairKey;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.util.Date;
import java.util.UUID;

import static com.jsso.wt.common.constant.CConst.*;

/**
 *    根据动态key设置jwt
 */
public class JwtUtil4KeyPair {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil4KeyPair.class);
//    private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    private static final KeyPair keyPair = PairKey.keyPair;

    /**
     * 用户登录成功后生成Jwt,默认token生效时间一小时
     */
    public static String createJWT(JwtClaim jwtClaim) {
        return createJWT(jwtClaim, JWT_DEFAULT_EXPIRE);
    }

    /**
     * 用户登录成功后生成Jwt
     */
    private static String createJWT(JwtClaim jwtClaim, Long ttlMillis) {
        String subject = jwtClaim.getAccount();//签发人
        Date issueDate = new Date(System.currentTimeMillis());//签发时间
        Date expirationDate = new Date(System.currentTimeMillis() + ttlMillis);//过期时间
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(jwtClaim.getMap())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(issueDate)
                .setSubject(subject)
                .signWith(keyPair.getPrivate())
                .setExpiration(expirationDate);
        return jwtBuilder.compact();
    }

    /**
     * 获取用户信息
     */
    public static DockResult<JwtClaim> parseUser(String token) {
        try {
            // 根据jwt token 获取用户信息
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            //
            String caccount = String.valueOf(body.get(JWT_ACCOUNT));
            String cid = String.valueOf(body.get(JWT_CID));
            String cpwd = String.valueOf(body.get(JWT_PASSWORD));
            return DockResult.success(new JwtClaim(cid, caccount, cpwd));
        } catch (ExpiredJwtException e) {// 如果jwt token超时时间小于一小时,则刷新token,否则需要重新登录
            return getJwtClaim4Invalid(token);
        } catch (Exception e) {
            LOGGER.error("登录信息异常,请重新登录-01:", e);
            return DockResult.fail("登录信息异常,请重新登录-01");
        }
    }

    /**
     * 获取失效用户信息
     */
    private  static DockResult<JwtClaim> getJwtClaim4Invalid(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .setAllowedClockSkewSeconds(JWT_DEFAULT_ALLOW_MAX_EXPIRE)// 允许失效一小时的用户可以得到用户信息
                    .build()
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String caccount = String.valueOf(body.get(JWT_ACCOUNT));
            String cid = String.valueOf(body.get(JWT_CID));
            String cpwd = String.valueOf(body.get(JWT_PASSWORD));
            JwtClaim jwtClaim = new JwtClaim(cid, caccount, cpwd);
            String jwtToken = createJWT(jwtClaim);
            LOGGER.warn("JWT TOKEN 失效,已重新获取Token");
            return DockResult.warn(jwtToken,new JwtClaim(cid, caccount, cpwd));
        } catch (ExpiredJwtException e) {
            return DockResult.fail("登录信息失效,请重新登录-02");
        } catch (Exception e) {
            return DockResult.fail("登录信息异常,请重新登录-03");
        }
    }
}
