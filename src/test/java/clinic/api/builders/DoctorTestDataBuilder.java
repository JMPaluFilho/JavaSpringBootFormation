package clinic.api.builders;

import clinic.api.domain.address.Address;
import clinic.api.domain.doctor.Doctor;
import clinic.api.domain.doctor.DoctorDetailsData;
import clinic.api.domain.doctor.DoctorPersistData;
import clinic.api.domain.doctor.Specialty;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class DoctorTestDataBuilder {

  private final AddressTestDataBuilder addressTestDataBuilder;

  public DoctorTestDataBuilder() {
    this.addressTestDataBuilder = new AddressTestDataBuilder();
  }

  public DoctorPersistData doctorPersistData(
      String name, String mail, String crm, Specialty specialty) {
    return new DoctorPersistData(
        name, mail, "111222333", crm, specialty, addressTestDataBuilder.addressData());
  }

  public Doctor createDoctor(
      String name,
      String mail,
      String crm,
      Specialty specialty,
      TestEntityManager testEntityManager) {
    var doctor = new Doctor(doctorPersistData(name, mail, crm, specialty));
    testEntityManager.persist(doctor);
    return doctor;
  }

  public DoctorDetailsData getDoctorDetailsData(DoctorPersistData doctorPersistData) {
    return new DoctorDetailsData(
        null,
        doctorPersistData.name(),
        doctorPersistData.mail(),
        doctorPersistData.phone(),
        doctorPersistData.crm(),
        doctorPersistData.specialty(),
        new Address(doctorPersistData.addressData()));
  }
}
