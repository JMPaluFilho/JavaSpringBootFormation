package clinic.api.domain.patient;

public record PatientListDate(Long id, String name, String mail, String taxId) {

  public PatientListDate(Patient patient) {
    this(patient.getId(), patient.getName(), patient.getMail(), patient.getTaxId());
  }
}
