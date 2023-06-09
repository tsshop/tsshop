package com.shop.tsshop.config.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xu
 */
@Component
public class TokenUtil {
    /**
     * 加密密钥
     */
    private static final String SECRET = "ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI=";
    /**
     * 默认过期一个小时
     */
    private static final long EXPIRE_TIME = 365 * 24 * 60 * 60 * 1000L;
    private static final String JWT_ISSUER = "JWT";
    private static final String ACCOUNT_CLAIM = "userId";

    /**
     * 生成token
     *
     * @param userId     用户账号 手机号
     * @param expireTime 过期时间（毫秒ms）
     * @return java.lang.String
     */
    public static String sign(Long userId, long expireTime) {
        try {
            Map<String, Object> headerClaims = new HashMap<>(2);
            headerClaims.put("alg", "HS256");
            headerClaims.put("typ", "JWT");
            long currentTimeMillis = System.currentTimeMillis();
            Date expireDate = new Date(currentTimeMillis + expireTime);
            // 附带username信息
            return JWT.create()
                    .withHeader(headerClaims)
                    .withIssuer(JWT_ISSUER)
                    .withClaim(ACCOUNT_CLAIM, userId)
                    .withIssuedAt(new Date(currentTimeMillis))
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 生成签名,60min后过期
     *
     * @return java.lang.String
     */
    public static String sign(JwtUserInfo jwtUserInfo) {
        try {
            Map<String, Object> headerClaims = new HashMap<String, Object>(2);
            headerClaims.put("alg", "HS256");
            headerClaims.put("typ", "JWT");
            long currentTimeMillis = System.currentTimeMillis();
            Date expireDate = new Date(currentTimeMillis + EXPIRE_TIME);
            // 附带username信息
            return JWT.create()
                    .withHeader(headerClaims)
                    .withIssuer(JWT_ISSUER)
                    .withClaim(ACCOUNT_CLAIM, jwtUserInfo.getUserId())
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证token
     *
     * @param token token值
     * @return com.ucar.bean.AccessToken
     */
    public static AccessToken verify(String token) {
        AccessToken result = new AccessToken();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer(JWT_ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            result.setVerify(Boolean.TRUE);
            result.setUserId(jwt.getClaim(ACCOUNT_CLAIM).asLong());
            result.setSignDate(jwt.getIssuedAt());
            result.setExpireDate(jwt.getExpiresAt());
            result.setExpire(Boolean.FALSE);
//            result.setExpire(jwt.getExpiresAt().compareTo(new Date()) <= 0);
        } catch (TokenExpiredException e) {
            DecodedJWT jwt = JWT.decode(token);
            result.setVerify(Boolean.TRUE);
            result.setExpire(Boolean.TRUE);
            result.setUserId(jwt.getClaim(ACCOUNT_CLAIM).asLong());
            result.setSignDate(jwt.getIssuedAt());
            result.setExpireDate(jwt.getExpiresAt());
        } catch (Exception e) {
            result.setVerify(Boolean.FALSE);
        }
        return result;
    }

    /**
     * 验证token
     *
     * @param token token值
     * @return boolean
     */
    public static boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(JWT_ISSUER)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验token
     *
     * @param token   token值
     * @param account 用户的account
     * @return boolean
     */
    public static boolean verify(String token, Long account) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(JWT_ISSUER)
                    .withClaim(ACCOUNT_CLAIM, account)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取用户Account
     *
     * @param token token值
     * @return java.lang.Long
     */
    public static JwtUserInfo getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Long userId = jwt.getClaim(ACCOUNT_CLAIM).asLong();
            return JwtUserInfo.builder().userId(userId).build();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取颁发时间
     *
     * @param token token值
     * @return java.util.Date
     */
    public static Date getIssuedDate(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取过期时间
     *
     * @param token token值
     * @return java.util.Date
     */
    public static Date getExpireDate(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是否过期
     *
     * @param token token值
     * @return boolean
     */
    public static boolean isExpire(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().compareTo(new Date()) <= 0;
        } catch (Exception e) {
            return true;
        }
    }


    public static void main(String[] args) {
        //String token = sign(1L, 60 * 1000 * 24 * 365);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKRUVTRSIsImV4cCI6MTUyNjAxMjkyMywidXNlcklkIjoxLCJpYXQiOjE1MjYwMTI4NjN9.NKhWgl_L-TmZCOSOUzTaKQFYFfM7OrjG6O55BQ2Ts9M";
        System.out.println(token);
        AccessToken result = verify(token);
/*        System.out.println(result.isVerify());
        System.out.println(result.isExpire());*/
        System.out.println(result.getSignDate());
        System.out.println(result.getExpireDate());
        System.out.println(result.getUserId());
        System.out.println(verify(token, 1L));
        System.out.println(getAccount(token));
    }
}
