package model;

import java.util.Objects;

public class Category {
  private String name;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "{" +
        " name='" + getName() + "'" +
        "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Category)) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(name, category.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }

}
