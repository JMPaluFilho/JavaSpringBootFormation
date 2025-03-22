package clinic.api.domain.appointment.validations.schedule;

import clinic.api.domain.appointment.AppointmentRepository;
import clinic.api.domain.appointment.AppointmentScheduleData;
import clinic.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class DoctorSameTimeAppointmentValidator implements AppointmentScheduleValidator {

  private final AppointmentRepository appointmentRepository;

  public DoctorSameTimeAppointmentValidator(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public void validate(AppointmentScheduleData appointmentScheduleData) {
    var isDoctorHaveAnotherAppointmentAtSameTime =
        appointmentRepository.existsByDoctorIdAndAppointmentDateAndCancelReasonIsNull(
            appointmentScheduleData.doctorId(), appointmentScheduleData.date());

    if (isDoctorHaveAnotherAppointmentAtSameTime) {
      throw new ValidationException(
          "Appointment can not be schedule because doctor already has a appointment at same time.");
    }
  }
}
