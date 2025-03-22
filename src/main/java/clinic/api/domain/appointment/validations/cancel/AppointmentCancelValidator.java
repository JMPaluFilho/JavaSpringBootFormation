package clinic.api.domain.appointment.validations.cancel;

import clinic.api.domain.appointment.AppointmentCancelData;

public interface AppointmentCancelValidator {

  void validate(AppointmentCancelData appointmentCancelData);
}
