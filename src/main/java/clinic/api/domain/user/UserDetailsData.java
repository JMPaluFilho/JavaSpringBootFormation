package clinic.api.domain.user;

public record UserDetailsData(Long id, String login) {

  public UserDetailsData(User user) {
    this(user.getId(), user.getLogin());
  }
}
