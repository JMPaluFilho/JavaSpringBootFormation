package clinic.api.domain.appointment;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  Boolean existsByDoctorIdAndAppointmentDateAndCancelReasonIsNull(
      Long doctorId, LocalDateTime date);

  Boolean existsByPatientIdAndAppointmentDateBetween(
      Long patientId, LocalDateTime openingHour, LocalDateTime finishingHour);
}
