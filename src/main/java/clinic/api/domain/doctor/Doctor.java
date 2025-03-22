package clinic.api.domain.doctor;

import clinic.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String mail;
  private String phone;
  private String crm;
  private boolean isActive;

  @Enumerated(EnumType.STRING)
  private Specialty specialty;

  @Embedded private Address address;

  public Doctor(DoctorPersistData doctorPersistData) {
    this.isActive = true;
    this.name = doctorPersistData.name();
    this.mail = doctorPersistData.mail();
    this.phone = doctorPersistData.phone();
    this.crm = doctorPersistData.crm();
    this.specialty = doctorPersistData.specialty();
    this.address = new Address(doctorPersistData.addressData());
  }

  public void updateData(DoctorUpdatedData doctorUpdatedData) {
    if (doctorUpdatedData.name() != null) {
      this.name = doctorUpdatedData.name();
    }
    if (doctorUpdatedData.phone() != null) {
      this.phone = doctorUpdatedData.phone();
    }
    if (doctorUpdatedData.addressData() != null) {
      this.address.updateData(doctorUpdatedData.addressData());
    }
  }

  public void delete() {
    this.isActive = false;
  }
}
