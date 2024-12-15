package Enos.SpringProject.medVoll.infra.security;

import Enos.SpringProject.medVoll.domain.models.User;
import Enos.SpringProject.medVoll.domain.exceptions.CreationTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.gen-pass}")
    private String gen_pass;

    private String ISSUER = "MedVoll.API";

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(gen_pass);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getLogin())
                    .withExpiresAt(this.dataExpire())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new CreationTokenException("Error: " + exception.getMessage());
        }
    }

    public String getSubject(String tokenJWT){
        try {
            Algorithm algorithm = Algorithm.HMAC256(gen_pass);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Error: Invalid TokenJWT or Expired TokenJWT");
        }
    }

    private Instant dataExpire() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
