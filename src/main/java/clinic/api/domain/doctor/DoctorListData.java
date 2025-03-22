package clinic.api.domain.doctor;

public record DoctorListData(
        Long id, String name, String mail, String crm, Specialty specialty) {

  public DoctorListData(Doctor doctor) {
    this(
        doctor.getId(),
        doctor.getName(),
        doctor.getMail(),
        doctor.getCrm(),
        doctor.getSpecialty());
  }
}
