package shop.mtcoding.jwtclone.example;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTest {
    @Test
    public void createJwt_test() {
        // given
        // when
        String jwt = JWT.create()
                .withSubject("myJwtToken")// token name
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7days after
                .withClaim("id", 1) // detail
                .withClaim("role", "guest")
                .sign(Algorithm.HMAC512("secretKey")); // electronic signature
        // then
    }

    @Test
    public void verifyJwt_test() {
        // given
        String jwt = JWT.create()
                .withSubject("myJwtToken")// token name
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7days after
                .withClaim("id", 1) // detail
                .withClaim("role", "guest")
                .sign(Algorithm.HMAC512("secretKey")); // electronic signature
        // when
        // 1. create mock jwt token -> Decode
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("secretKey")).build().verify(jwt);
            System.out.println(decodedJWT);
            // 2. Id verification
            int id = decodedJWT.getClaim("id").asInt();
            System.out.println(id);
            // 3. Role verification
            String role = decodedJWT.getClaim("role").asString();
            System.out.println(role);
        } catch (SignatureVerificationException sve) {
            System.out.println("토큰 검증 실패 " + sve.getMessage()); // falsification
        } catch (TokenExpiredException tee) {
            System.out.println("토큰 만료 " + tee.getMessage()); // token expired
        }

        // then
    }
}
