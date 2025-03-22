package clinic.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import clinic.api.domain.address.AddressData;

public record DoctorUpdatedData(
        @NotNull Long id, String name, String phone, AddressData addressData) {}
