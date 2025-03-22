package clinic.api.domain.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressData(
    @NotBlank String addressLineOne,
    String addressLineTwo,
    @NotBlank @Pattern(regexp = "^(?:\\d{4}|\\d{5})-\\d{3}$") String zipCode,
    @NotBlank String city,
    @NotBlank String state) {}
