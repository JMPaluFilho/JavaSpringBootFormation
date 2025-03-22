package clinic.api.infra.security;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public PasswordService(BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public String encryptPassword(@NotBlank String password) {
    return bCryptPasswordEncoder.encode(password);
  }
}
