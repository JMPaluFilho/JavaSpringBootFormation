package clinic.api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentDetailsData(Long id, Long doctorId, Long patientId, LocalDateTime date) {

  public AppointmentDetailsData(Appointment appointment) {
    this(
        appointment.getId(),
        appointment.getDoctor().getId(),
        appointment.getPatient().getId(),
        appointment.getAppointmentDate());
  }
}
