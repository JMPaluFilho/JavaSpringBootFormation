package clinic.api.builders;

import clinic.api.domain.appointment.Appointment;
import clinic.api.domain.doctor.Doctor;
import clinic.api.domain.patient.Patient;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.time.LocalDateTime;

public class AppointmentTestDataBuilder {

  public void scheduleAppointment(
      Doctor doctor, Patient patient, LocalDateTime date, TestEntityManager testEntityManager) {
    testEntityManager.persist(new Appointment(null, doctor, patient, date, null));
  }
}
