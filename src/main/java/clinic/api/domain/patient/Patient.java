package clinic.api.domain.patient;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import clinic.api.domain.address.Address;

@Entity(name = "Patient")
@Table(name = "patients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String mail;
  private String taxId;
  private String phone;

  @Embedded private Address address;

  private boolean isActive;

  public Patient(PatientPersistData patientPersistData) {
    this.isActive = true;
    this.name = patientPersistData.name();
    this.mail = patientPersistData.mail();
    this.phone = patientPersistData.phone();
    this.taxId = patientPersistData.taxId();
    this.address = new Address(patientPersistData.addressData());
  }

  public void updateData(PatientUpdatedDate patientUpdatedDate) {
    if (patientUpdatedDate.name() != null) this.name = patientUpdatedDate.name();

    if (patientUpdatedDate.phone() != null) this.phone = patientUpdatedDate.phone();

    if (patientUpdatedDate.addressData() != null)
      address.updateData(patientUpdatedDate.addressData());
  }

  public void delete() {
    this.isActive = false;
  }
}
