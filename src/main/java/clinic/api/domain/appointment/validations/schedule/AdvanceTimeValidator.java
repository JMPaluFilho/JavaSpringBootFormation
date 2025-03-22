package clinic.api.domain.appointment.validations.schedule;

import clinic.api.domain.appointment.AppointmentScheduleData;
import clinic.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component("AdvanceTimeScheduleValidator")
public class AdvanceTimeValidator implements AppointmentScheduleValidator {

  @Override
  public void validate(AppointmentScheduleData appointmentScheduleData) {
    var scheduleDate = appointmentScheduleData.date();
    var now = LocalDateTime.now();
    var differenceInMinutes = Duration.between(now, scheduleDate).toMinutes();

    if (differenceInMinutes < 30) {
      throw new ValidationException(
          "Appointment should be schedule with at least 30 minutes in advance");
    }
  }
}
