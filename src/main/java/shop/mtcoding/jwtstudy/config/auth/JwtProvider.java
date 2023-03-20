package shop.mtcoding.jwtstudy.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import shop.mtcoding.jwtstudy.model.User;

import java.util.Date;

public class JwtProvider {

    private static final String SUBJECT = "jwt";
    private static final int EXP = 1000 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization"; // 헤더는 밖에서 접근 가능하게 public
    private static final String SECRET = "메타코딩";


    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis()+EXP)) // 토큰이 만들어진 시간
                .withClaim("id", user.getId()) // user의 primary key
                .withClaim("role", user.getRole()) // user의 권한
                .sign(Algorithm.HMAC512(SECRET)); // HMAC 대칭키
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩"))
                .build()
                .verify(jwt);
        return decodedJWT;
    }
}
