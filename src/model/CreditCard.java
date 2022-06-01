package model;

import java.time.LocalDate;
import java.util.Objects;

public class CreditCard {
  private int id;
  private String owner;
  private String name;
  private String number;
  private String country;
  private LocalDate expiry;
  private String cvv;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOwner() {
    return this.owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumber() {
    return this.number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public LocalDate getExpiry() {
    return this.expiry;
  }

  public void setExpiry(LocalDate expiry) {
    this.expiry = expiry;
  }

  public String getCvv() {
    return this.cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof CreditCard)) {
      return false;
    }
    CreditCard creditCard = (CreditCard) o;
    return id == creditCard.id && Objects.equals(owner, creditCard.owner) && Objects.equals(name, creditCard.name)
        && Objects.equals(number, creditCard.number) && Objects.equals(country, creditCard.country)
        && Objects.equals(expiry, creditCard.expiry) && Objects.equals(cvv, creditCard.cvv);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, owner, name, number, country, expiry, cvv);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", owner='" + getOwner() + "'" +
        ", name='" + getName() + "'" +
        ", number='" + getNumber() + "'" +
        ", country='" + getCountry() + "'" +
        ", expiry='" + getExpiry() + "'" +
        ", cvv='" + getCvv() + "'" +
        "}";
  }

}
