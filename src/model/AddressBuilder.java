package src.model;

public class AddressBuilder {
  Address address;

  public AddressBuilder() {
    this.address = new Address();
  }

  public static AddressBuilder builder() {
    return new AddressBuilder();
  }

  public AddressBuilder addId(int id) {
    this.address.setId(id);
    return this;
  }

  public AddressBuilder addAddress(String street, int number, String cityArea, String city, String state,
      String country, String zipCode) {
    this.address.setStreet(street);
    this.address.setNumber(number);
    this.address.setCityArea(cityArea);
    this.address.setCity(city);
    this.address.setState(state);
    this.address.setCountry(country);
    this.address.setZipCode(zipCode);

    return this;
  }

  public AddressBuilder addLine2(String line2) {
    this.address.setLine2(line2);
    return this;
  }

  public AddressBuilder addOwner(Customer owner) {
    this.address.setOwner(owner);
    return this;
  }

  public AddressBuilder addOwnerString(String owner) {
    this.address.setOwnerConcat(owner);
    return this;
  }

  public Address get() {
    return this.address;
  }
}
