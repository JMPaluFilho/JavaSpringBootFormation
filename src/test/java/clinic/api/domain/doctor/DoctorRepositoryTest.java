package clinic.api.domain.doctor;

import static org.assertj.core.api.Assertions.assertThat;

import clinic.api.builders.AppointmentTestDataBuilder;
import clinic.api.builders.DoctorTestDataBuilder;
import clinic.api.builders.PatientTestDataBuilder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

  @Autowired private DoctorRepository doctorRepository;
  @Autowired private TestEntityManager testEntityManager;
  private final DoctorTestDataBuilder doctorTestDataBuilder = new DoctorTestDataBuilder();
  private final PatientTestDataBuilder patientTestDataBuilder = new PatientTestDataBuilder();
  private final AppointmentTestDataBuilder appointmentTestDataBuilder =
      new AppointmentTestDataBuilder();

  @Test
  @DisplayName("Should return null when the only registered doctor is not available at the date.")
  void getRandomDoctorWithoutScheduleOnDateScenario1() {
    // GIVEN OR ARRANGE
    var nextMondayAt10 =
        LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
    var doctor =
        doctorTestDataBuilder.createDoctor(
            "Doctor", "doctor@mail.com", "123456", Specialty.CARDIOLOGY, testEntityManager);
    var patient =
        patientTestDataBuilder.createPatient(
            "Patient", "patient@mail.com", "00000000000", testEntityManager);
    appointmentTestDataBuilder.scheduleAppointment(
        doctor, patient, nextMondayAt10, testEntityManager);

    // WHEN OR ACT
    var availableDoctor =
        doctorRepository.getRandomDoctorWithoutScheduleOnDate(Specialty.CARDIOLOGY, nextMondayAt10);

    // THEN OR ASSERT
    assertThat(availableDoctor).isEmpty();
  }

  @Test
  @DisplayName("Should return a doctor when he is available at the date.")
  void getRandomDoctorWithoutScheduleOnDateScenario2() {
    // GIVEN OR ARRANGE
    var nextMondayAt10 =
        LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
    var doctor =
        doctorTestDataBuilder.createDoctor(
            "Doctor", "doctor@mail.com", "123456", Specialty.CARDIOLOGY, testEntityManager);

    // WHEN OR ACT
    var availableDoctor =
        doctorRepository
            .getRandomDoctorWithoutScheduleOnDate(Specialty.CARDIOLOGY, nextMondayAt10)
            .orElse(null);

    // THEN OR ASSERT
    assertThat(availableDoctor).isEqualTo(doctor);
  }
}
