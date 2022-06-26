package src.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
public class Customer {
  @Id
  @Column(name = "customer_id")
  @NotNull
  private int id;

  @Column(name = "customer_full_name")
  @NotNull
  private String name;

  @Column(name = "customer_email")
  @NotNull
  private String email;

  @Column(name = "customer_cpf")
  @NotNull
  private String cpf;

  @Column(name = "customer_rg")
  @NotNull
  private String rg;

  @Column(name = "customer_gender")
  @NotNull
  private String gender;

  @Column(name = "customer_birth")
  @NotNull
  private LocalDate birth;

  @Column(name = "customer_cellphone")
  private String cellPhone;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCpf() {
    return this.cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getRg() {
    return this.rg;
  }

  public void setRg(String rg) {
    this.rg = rg;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public LocalDate getBirth() {
    return this.birth;
  }

  public void setBirth(LocalDate birth) {
    this.birth = birth;
  }

  public String getCellPhone() {
    return this.cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Customer)) {
      return false;
    }
    Customer customer = (Customer) o;
    return id == customer.id && Objects.equals(name, customer.name) && Objects.equals(email, customer.email)
        && Objects.equals(cpf, customer.cpf) && Objects.equals(rg, customer.rg) && gender == customer.gender
        && Objects.equals(birth, customer.birth) && Objects.equals(cellPhone, customer.cellPhone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, cpf, rg, gender, birth, cellPhone);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", name='" + getName() + "'" +
        ", email='" + getEmail() + "'" +
        ", cpf='" + getCpf() + "'" +
        ", rg='" + getRg() + "'" +
        ", gender='" + getGender() + "'" +
        ", birth='" + getBirth() + "'" +
        ", cellPhone='" + getCellPhone() + "'" +
        "}";
  }

}
