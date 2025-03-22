package clinic.api.domain.appointment.validations.schedule;

import clinic.api.domain.appointment.AppointmentScheduleData;
import clinic.api.domain.doctor.DoctorRepository;
import clinic.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class DoctorIsActiveValidator implements AppointmentScheduleValidator {

  private final DoctorRepository doctorRepository;

  public DoctorIsActiveValidator(DoctorRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }

  @Override
  public void validate(AppointmentScheduleData appointmentScheduleData) {
    if (Objects.isNull(appointmentScheduleData.doctorId())) {
      return;
    }

    var isDoctorActive = doctorRepository.findIsActiveById(appointmentScheduleData.doctorId());

    if (!isDoctorActive) {
      throw new ValidationException("Appointment can not be schedule with inactive doctor.");
    }
  }
}
