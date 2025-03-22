package clinic.api.domain.appointment.validations.schedule;

import clinic.api.domain.appointment.AppointmentScheduleData;
import clinic.api.domain.patient.PatientRepository;
import clinic.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PatientIsActiveValidator implements AppointmentScheduleValidator {

  private final PatientRepository patientRepository;

  public PatientIsActiveValidator(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  @Override
  public void validate(AppointmentScheduleData appointmentScheduleData) {
    var isPatientActive = patientRepository.findIsActiveById(appointmentScheduleData.patientId());

    if (!isPatientActive) {
      throw new ValidationException("Appointment can not be schedule with inactive patient.");
    }
  }
}
