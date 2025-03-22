package clinic.api.domain.appointment;

import clinic.api.domain.appointment.validations.cancel.AppointmentCancelValidator;
import clinic.api.domain.appointment.validations.schedule.AppointmentScheduleValidator;
import clinic.api.domain.doctor.Doctor;
import clinic.api.domain.doctor.DoctorRepository;
import clinic.api.domain.patient.PatientRepository;
import jakarta.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AppointmentSchedule {

  private final AppointmentRepository appointmentRepository;
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;
  private final List<AppointmentScheduleValidator> appointmentScheduleValidatorList;
  private final List<AppointmentCancelValidator> appointmentCancelValidatorList;

  public AppointmentSchedule(
      AppointmentRepository appointmentRepository,
      DoctorRepository doctorRepository,
      PatientRepository patientRepository,
      List<AppointmentScheduleValidator> appointmentScheduleValidatorList,
      List<AppointmentCancelValidator> appointmentCancelValidatorList) {
    this.appointmentRepository = appointmentRepository;
    this.doctorRepository = doctorRepository;
    this.patientRepository = patientRepository;
    this.appointmentScheduleValidatorList = appointmentScheduleValidatorList;
    this.appointmentCancelValidatorList = appointmentCancelValidatorList;
  }

  public AppointmentDetailsData schedule(AppointmentScheduleData appointmentScheduleData) {
    var doctor =
        Objects.nonNull(appointmentScheduleData.doctorId())
            ? doctorRepository
                .findById(appointmentScheduleData.doctorId())
                .orElseThrow(() -> new ValidationException("Doctor not found."))
            : getRandomDoctor(appointmentScheduleData)
                .orElseThrow(
                    () ->
                        new ValidationException(
                            "There is no doctor available with this specialty at this date."));
    var patient =
        patientRepository
            .findById(appointmentScheduleData.patientId())
            .orElseThrow(() -> new ValidationException("Patient not found."));

    appointmentScheduleValidatorList.forEach(
        validator -> validator.validate(appointmentScheduleData));
    var appointment = new Appointment(null, doctor, patient, appointmentScheduleData.date(), null);
    appointmentRepository.save(appointment);

    return new AppointmentDetailsData(appointment);
  }

  private Optional<Doctor> getRandomDoctor(AppointmentScheduleData appointmentScheduleData) {
    if (Objects.isNull(appointmentScheduleData.specialty())) {
      throw new ValidationException("Specialty is mandatory when the doctor is not informed.");
    }

    return doctorRepository.getRandomDoctorWithoutScheduleOnDate(
        appointmentScheduleData.specialty(), appointmentScheduleData.date());
  }

  public void cancel(AppointmentCancelData appointmentCancelData) {
    var appointment =
        appointmentRepository
            .findById(appointmentCancelData.appointmentId())
            .orElseThrow(() -> new ValidationException("Appointment not found."));

    appointmentCancelValidatorList.forEach(validator -> validator.validate(appointmentCancelData));
    appointment.cancel(appointmentCancelData.cancelReason());
  }
}
