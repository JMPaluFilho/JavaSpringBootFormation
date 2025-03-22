package clinic.api.builders;

import clinic.api.domain.address.AddressData;

public class AddressTestDataBuilder {

  public AddressData addressData() {
    return new AddressData("Main Street 9", "Apartment 501", "1500-400", "Lisbon", "LX");
  }
}
