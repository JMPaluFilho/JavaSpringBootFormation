package clinic.api.domain.patient;

import jakarta.validation.Valid;
import clinic.api.domain.address.AddressData;

public record PatientUpdatedDate(
    Long id, String name, String phone, @Valid AddressData addressData) {}
