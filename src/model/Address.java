package src.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "addresses")
public class Address {
  @Id
  @Column(name = "address_id")
  @NotNull
  private int id;

  @ManyToOne
  @JoinColumn(name = "address_owner")
  @NotNull
  private Customer owner;

  private String ownerConcat;

  @Column(name = "address_street")
  @NotNull
  private String street;

  @Column(name = "address_number")
  private int number;

  @Column(name = "address_line_2")
  private String line2;

  @Column(name = "address_city_area")
  @NotNull
  private String cityArea;

  @Column(name = "address_city")
  @NotNull
  private String city;

  @Column(name = "address_state")
  @NotNull
  private String state;

  @Column(name = "address_country")
  @NotNull
  private String country;

  @Column(name = "address_zip_code")
  @NotNull
  private String zipCode;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Customer getOwner() {
    return this.owner;
  }

  public void setOwner(Customer owner) {
    this.owner = owner;
  }

  public String getOwnerConcat() {
    return this.ownerConcat;
  }

  public void setOwnerConcat(String ownerConcat) {
    this.ownerConcat = ownerConcat;
  }

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public int getNumber() {
    return this.number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getLine2() {
    return this.line2;
  }

  public void setLine2(String line2) {
    this.line2 = line2;
  }

  public String getCityArea() {
    return this.cityArea;
  }

  public void setCityArea(String cityArea) {
    this.cityArea = cityArea;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getZipCode() {
    return this.zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Address)) {
      return false;
    }
    Address address = (Address) o;
    return id == address.id && Objects.equals(owner, address.owner) && Objects.equals(street, address.street)
        && number == address.number && Objects.equals(line2, address.line2)
        && Objects.equals(cityArea, address.cityArea) && Objects.equals(city, address.city)
        && Objects.equals(state, address.state) && Objects.equals(country, address.country)
        && Objects.equals(zipCode, address.zipCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, owner, street, number, line2, cityArea, city, state, country, zipCode);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", owner='" + getOwner() + "'" +
        ", street='" + getStreet() + "'" +
        ", number='" + getNumber() + "'" +
        ", line2='" + getLine2() + "'" +
        ", cityArea='" + getCityArea() + "'" +
        ", city='" + getCity() + "'" +
        ", state='" + getState() + "'" +
        ", country='" + getCountry() + "'" +
        ", zipCode='" + getZipCode() + "'" +
        "}";
  }

}
