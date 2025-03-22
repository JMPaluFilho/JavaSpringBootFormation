package clinic.api.infra.security;

import clinic.api.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  public static final String ISSUER = "API Clinic";

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User user) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer(ISSUER)
          .withSubject(user.getLogin())
          .withExpiresAt(expirationDate())
          .sign(algorithm);
    } catch (Exception exception) {
      throw new RuntimeException("erro ao gerar token JWT", exception);
    }
  }

  public String getSubject(String tokenJWT) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm).withIssuer(ISSUER).build().verify(tokenJWT).getSubject();
    } catch (Exception exception) {
      throw new RuntimeException("Token JWT inválido ou expirado", exception);
    }
  }

  private Instant expirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
