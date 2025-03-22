package clinic.api.domain.doctor;

import clinic.api.domain.address.Address;

public record DoctorDetailsData(
    Long id,
    String name,
    String mail,
    String phone,
    String crm,
    Specialty specialty,
    Address address) {

  public DoctorDetailsData(Doctor doctor) {
    this(
        doctor.getId(),
        doctor.getName(),
        doctor.getMail(),
        doctor.getPhone(),
        doctor.getCrm(),
        doctor.getSpecialty(),
        doctor.getAddress());
  }
}
