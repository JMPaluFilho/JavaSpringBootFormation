package clinic.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  private String addressLineOne;
  private String addressLineTwo;
  private String zipCode;
  private String city;
  private String state;

  public Address(AddressData addressData) {
    this.addressLineOne = addressData.addressLineOne();
    this.addressLineTwo = addressData.addressLineTwo();
    this.zipCode = addressData.zipCode();
    this.city = addressData.city();
    this.state = addressData.state();
  }

  public void updateData(AddressData addressData) {
    if (addressData.addressLineOne() != null) {
      this.addressLineOne = addressData.addressLineOne();
    }
    if (addressData.addressLineTwo() != null) {
      this.addressLineTwo = addressData.addressLineTwo();
    }
    if (addressData.zipCode() != null) {
      this.zipCode = addressData.zipCode();
    }
    if (addressData.city() != null) {
      this.city = addressData.city();
    }
    if (addressData.state() != null) {
      this.state = addressData.state();
    }
  }
}
