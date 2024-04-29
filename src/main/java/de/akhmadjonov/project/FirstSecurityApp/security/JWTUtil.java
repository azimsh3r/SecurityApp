package de.akhmadjonov.project.FirstSecurityApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${secret}")
    private String secret;
    public String generateToken(String username) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("Azimjon")
                .withExpiresAt(Date.from(ZonedDateTime.now().plusHours(5).toInstant()))
                .sign(Algorithm.HMAC256(secret));
    }


    public String validateTokenAndRetrieveData(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Azimjon")
                .withSubject("User Details")
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
