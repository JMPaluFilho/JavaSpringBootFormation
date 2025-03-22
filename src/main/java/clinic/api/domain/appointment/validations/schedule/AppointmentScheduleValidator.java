package clinic.api.domain.appointment.validations.schedule;

import clinic.api.domain.appointment.AppointmentScheduleData;

public interface AppointmentScheduleValidator {

  void validate(AppointmentScheduleData appointmentScheduleData);
}
