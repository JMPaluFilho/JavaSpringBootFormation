package clinic.api.domain.appointment.validations.schedule;

import clinic.api.domain.appointment.AppointmentScheduleData;
import clinic.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class ClinicOpeningHoursValidator implements AppointmentScheduleValidator {

  @Override
  public void validate(AppointmentScheduleData appointmentScheduleData) {
    var scheduleDate = appointmentScheduleData.date();

    var sunday = scheduleDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var beforeClinicOpening = scheduleDate.getHour() < 7;
    var afterClinicOpening = scheduleDate.getHour() > 18;

    if (sunday || beforeClinicOpening || afterClinicOpening) {
      throw new ValidationException(
          "Appointment could not be schedule because date is out of clinic opening hours");
    }
  }
}
