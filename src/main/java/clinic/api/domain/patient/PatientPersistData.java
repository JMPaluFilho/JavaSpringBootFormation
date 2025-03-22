package clinic.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import clinic.api.domain.address.AddressData;

public record PatientPersistData(
    @NotBlank String name,
    @NotBlank @Email String mail,
    @NotBlank String phone,
    @NotBlank @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}") String taxId,
    @NotNull @Valid AddressData addressData) {}
