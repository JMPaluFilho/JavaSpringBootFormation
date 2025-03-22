package clinic.api.domain.appointment.validations.cancel;

import clinic.api.domain.appointment.AppointmentCancelData;
import clinic.api.domain.appointment.AppointmentRepository;
import clinic.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component("AdvanceTimeCancelValidator")
public class AdvanceTimeValidator implements AppointmentCancelValidator {

  private final AppointmentRepository appointmentRepository;

  public AdvanceTimeValidator(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public void validate(AppointmentCancelData appointmentCancelData) {
    var schedule = appointmentRepository.getReferenceById(appointmentCancelData.appointmentId());
    var now = LocalDateTime.now();
    var differenceInHours = Duration.between(now, schedule.getAppointmentDate()).toHours();

    if (differenceInHours < 24) {
      throw new ValidationException(
          "Appointment only can be cancelled within 24 hours in advance.");
    }
  }
}
