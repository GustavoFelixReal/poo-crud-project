package src.model;

import java.time.LocalDate;

public class CustomerBuilder {
  private Customer customer;

  public CustomerBuilder() {
    customer = new Customer();
  }

  public static CustomerBuilder builder() {
    return new CustomerBuilder();
  }

  public CustomerBuilder addId(int id) {
    this.customer.setId(id);
    return this;
  }

  public CustomerBuilder addName(String name) {
    this.customer.setName(name);
    return this;
  }

  public CustomerBuilder addEmail(String email) {
    this.customer.setEmail(email);
    return this;
  }

  public CustomerBuilder addCpf(String cpf) {
    this.customer.setCpf(cpf);
    return this;
  }

  public CustomerBuilder addRg(String rg) {
    this.customer.setRg(rg);
    return this;
  }

  public CustomerBuilder addGender(String gender) {
    this.customer.setGender(gender);
    return this;
  }

  public CustomerBuilder addBirth(LocalDate birth) {
    this.customer.setBirth(birth);
    return this;
  }

  public CustomerBuilder addCellPhone(String cellPhone) {
    this.customer.setCellPhone(cellPhone);
    return this;
  }

  public Customer get() {
    return this.customer;
  }
}
