package idv.cheng.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cheng
 * @since 2021/12/4 18:05
 **/
@Slf4j
public class JwtUtils {

    private static final String JWT_TOKEN = "cl3au4a83";

    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN) // 簽發的演算法，密鑰是JWT_TOKEN
                .setClaims(claims) // body資料需唯一，可自行設定
                .setIssuedAt(new Date()) // 設定簽發時間
                .setExpiration(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 60 * 1000))
                .compact();
    }

    public static Claims checkToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_TOKEN)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("###ERR:", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String token = JwtUtils.createToken(100L);
        System.out.println("token = " + token);
        Map<String, Object> map = JwtUtils.checkToken(token);
        System.out.println("map = " + map);
    }
}
