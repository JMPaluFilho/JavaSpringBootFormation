package clinic.api.controller;

import jakarta.validation.Valid;
import clinic.api.domain.user.AuthenticationData;
import clinic.api.domain.user.User;
import clinic.api.infra.security.TokenJWTData;
import clinic.api.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  public AuthenticationController(
      AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @PostMapping
  public ResponseEntity<TokenJWTData> doLogin(
      @RequestBody @Valid AuthenticationData authenticationData) {
    var authenticationToken =
        new UsernamePasswordAuthenticationToken(
            authenticationData.login(), authenticationData.password());
    var authentication = authenticationManager.authenticate(authenticationToken);
    var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

    return ResponseEntity.ok(new TokenJWTData(tokenJWT));
  }
}
