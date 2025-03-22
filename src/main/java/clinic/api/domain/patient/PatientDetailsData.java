package clinic.api.domain.patient;

import clinic.api.domain.address.Address;

public record PatientDetailsData(
    Long id, String name, String mail, String taxId, String phone, Address address) {

  public PatientDetailsData(Patient patient) {
    this(
        patient.getId(),
        patient.getName(),
        patient.getMail(),
        patient.getTaxId(),
        patient.getPhone(),
        patient.getAddress());
  }
}
