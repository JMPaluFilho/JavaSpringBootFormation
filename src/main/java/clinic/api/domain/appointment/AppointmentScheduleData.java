package clinic.api.domain.appointment;

import clinic.api.domain.doctor.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentScheduleData(
    Long doctorId,
    @NotNull Long patientId,
    @NotNull @Future LocalDateTime date,
    Specialty specialty) {}
