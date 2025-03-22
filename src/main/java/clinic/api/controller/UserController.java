package clinic.api.controller;

import clinic.api.domain.user.User;
import clinic.api.domain.user.UserDetailsData;
import clinic.api.domain.user.UserPersistData;
import clinic.api.domain.user.UserRepository;
import clinic.api.infra.security.PasswordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

  private final PasswordService passwordService;
  private final UserRepository userRepository;

  public UserController(PasswordService passwordService, UserRepository userRepository) {
    this.passwordService = passwordService;
    this.userRepository = userRepository;
  }

  @PostMapping
  @Transactional
  public ResponseEntity<UserDetailsData> create(
      @RequestBody @Valid UserPersistData userPersistData, UriComponentsBuilder uriBuilder) {
    var encryptedPassword = passwordService.encryptPassword(userPersistData.password());
    var user = new User(userPersistData, encryptedPassword);
    userRepository.save(user);
    var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

    return ResponseEntity.created(uri).body(new UserDetailsData(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDetailsData> getDetails(@PathVariable Long id) {
    var user = userRepository.getReferenceById(id);
    return ResponseEntity.ok(new UserDetailsData(user));
  }
}
