package clinic.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentCancelData(
    @NotNull Long appointmentId, @NotNull CancelReason cancelReason) {}
