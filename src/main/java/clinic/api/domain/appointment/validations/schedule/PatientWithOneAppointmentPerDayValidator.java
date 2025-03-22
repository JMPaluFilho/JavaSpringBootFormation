package clinic.api.domain.appointment.validations.schedule;

import clinic.api.domain.appointment.AppointmentRepository;
import clinic.api.domain.appointment.AppointmentScheduleData;
import clinic.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PatientWithOneAppointmentPerDayValidator implements AppointmentScheduleValidator {

  private final AppointmentRepository appointmentRepository;

  public PatientWithOneAppointmentPerDayValidator(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public void validate(AppointmentScheduleData appointmentScheduleData) {
    var openingHour = appointmentScheduleData.date().withHour(7);
    var finishingHour = appointmentScheduleData.date().withHour(18);
    var patientHasMoreThanOneAppointmentAtSameDay =
        appointmentRepository.existsByPatientIdAndAppointmentDateBetween(
            appointmentScheduleData.patientId(), openingHour, finishingHour);

    if (patientHasMoreThanOneAppointmentAtSameDay) {
      throw new ValidationException(
          "Appointment can not be schedule because patient already has a appointment at same day.");
    }
  }
}
