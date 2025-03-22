package clinic.api.builders;

import clinic.api.domain.patient.Patient;
import clinic.api.domain.patient.PatientPersistData;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class PatientTestDataBuilder {

  private final AddressTestDataBuilder addressTestDataBuilder;

  public PatientTestDataBuilder() {
    this.addressTestDataBuilder = new AddressTestDataBuilder();
  }

  public PatientPersistData patientPersistData(String name, String mail, String taxId) {
    return new PatientPersistData(
        name, mail, "999888777", taxId, addressTestDataBuilder.addressData());
  }

  public Patient createPatient(
      String name, String mail, String taxId, TestEntityManager testEntityManager) {
    var patient = new Patient(patientPersistData(name, mail, taxId));
    testEntityManager.persist(patient);
    return patient;
  }
}
