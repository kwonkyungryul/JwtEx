package shop.mtcoding.jwtstudy.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;

// JSON Web Token
// 대칭키를 쓸 때와, 공개키를 쓸 때를 알아야 한다.
public class JwtTest {
    
    // ABC(메타코딩) -> 1313AB 
    // ABC(메타코) -> 5335KD
    // 똑같은 ABC라도 시크릿 값이 다르면 결과가 다르게 나온다.
    
    // 1313AB - 토큰(로그인을 완료하면 서버가 생성해줌)
    // 서버가 "메타코딩" 대칭키로 잠군 값을 유저에게 준다. 로그인 시 서버는 그 대칭키로 열어보고 본인의 것인지 확인한다. -> 인증완료
    // 토큰안의 값은 id와 권한 정도만 들어있는 게 좋다. 토큰 탈취 시 위험.

    //

    @Test
    public void createJwt_test() {
        // given

        // when
        String jwt = JWT.create()
                .withSubject("토큰 제목")
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*7)) // 토큰이 만들어진 시간
                .withClaim("id", "1") // user의 primary key
                .withClaim("role", "guest") // user의 권한
                .sign(Algorithm.HMAC512("메타코딩")); // HMAC 대칭키
        System.out.println(jwt);
        // then

    }

    @Test
    public void verifyJwt_test() {
        // given
        String jwt = JWT.create()
                .withSubject("토큰 제목")
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*7)) // 토큰이 만들어진 시간
                .withClaim("id", 1) // user의 primary key
                .withClaim("role", "guest") // user의 권한
                .sign(Algorithm.HMAC512("메타코딩")); // HMAC 대칭키
        System.out.println(jwt);

        // when

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩"))
                    .build()
                    .verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();
            System.out.println(id);
            System.out.println(role);
        } catch (SignatureVerificationException sve) {
            System.out.println("토큰 검증 실패" + sve.getMessage());
        } catch (TokenExpiredException tee) {
            System.out.println("토큰 만료" + tee.getMessage());
        }

        // then

    }
}
