package shop.mtcoding.jwtclone.config.auth;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.jwtclone.model.User;

public class JwtProvider {

    private static final String SUBJECT = "jwtStudy";
    private static final int EXP = 1000 * 60 * 60; // 1hr
    private static final String SECRET = "secretKey";
    // TOKEN_PREFIX, HEADER(Token keyname)
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER = "Authorization";

    // user : id, role (claim)
    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwt;
    }

    // verification of jwt when logging in
    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        return decodedJWT; // null?
    }
}
