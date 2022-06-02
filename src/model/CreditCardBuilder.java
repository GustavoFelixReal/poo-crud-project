package model;

import java.time.LocalDate;

public class CreditCardBuilder {
  private CreditCard creditCard;

  public CreditCardBuilder() {
    this.creditCard = new CreditCard();
  }

  public static CreditCardBuilder builder() {
    return new CreditCardBuilder();
  }

  public CreditCardBuilder addId(int id) {
    this.creditCard.setId(id);
    return this;
  }

  public CreditCardBuilder addCreditCard(String name, String number, String country, LocalDate expiry, String cvv) {
    this.creditCard.setName(name);
    this.creditCard.setNumber(number);
    this.creditCard.setCountry(country);
    this.creditCard.setExpiry(expiry);
    this.creditCard.setCvv(cvv);

    return this;
  }

  public CreditCardBuilder addOwner(String owner) {
    this.creditCard.setOwner(owner);
    return this;
  }

  public CreditCard get() {
    return this.creditCard;
  }

}
