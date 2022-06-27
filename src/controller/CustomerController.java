package src.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import src.database.CustomerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import src.model.Customer;
import src.model.CustomerBuilder;
import src.util.DateConverter;
import src.util.HibernateUtil;

public class CustomerController implements IController<Customer> {
  private CustomerDao dao = new CustomerDao(HibernateUtil.getSessionFactory());

  private ObservableList<Customer> customers = FXCollections.observableArrayList();

  private StringProperty id = new SimpleStringProperty();
  private StringProperty name = new SimpleStringProperty();
  private StringProperty email = new SimpleStringProperty();
  private StringProperty cpf = new SimpleStringProperty();
  private StringProperty rg = new SimpleStringProperty();
  private StringProperty gender = new SimpleStringProperty();
  private ObjectProperty<LocalDate> birth = new SimpleObjectProperty<>();
  private StringProperty cellPhone = new SimpleStringProperty();

  private TableView<Customer> table = new TableView<>();

  public StringProperty idProperty() {
    return id;
  }
  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty emailProperty() {
    return email;
  }

  public StringProperty cpfProperty() {
    return cpf;
  }

  public StringProperty rgProperty() {
    return rg;
  }

  public StringProperty genderProperty() {
    return gender;
  }

  public ObjectProperty<LocalDate> birthProperty() {
    return birth;
  }

  public StringProperty cellPhoneProperty() {
    return cellPhone;
  }

  @SuppressWarnings("unchecked")
  public CustomerController() {
    customers.addAll(dao.all());

    TableColumn<Customer, Integer> columnId = new TableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Customer, String> columnName = new TableColumn<>("Nome");
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Customer, String> columnEmail = new TableColumn<>("Email");
    columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    TableColumn<Customer, String> columnCpf = new TableColumn<>("CPF");
    columnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

    TableColumn<Customer, String> columnRg = new TableColumn<>("RG");
    columnRg.setCellValueFactory(new PropertyValueFactory<>("rg"));

    TableColumn<Customer, String> columnGender = new TableColumn<>("Sexo");
    columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

    TableColumn<Customer, String> columnBirth = new TableColumn<>("Data de nascimento");
    columnBirth.setCellValueFactory((item) -> {
      LocalDate dt = item.getValue().getBirth();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      return new ReadOnlyStringWrapper(dt.format(formatter));
    });

    TableColumn<Customer, String> columnCellPhone = new TableColumn<>("Celular");
    columnCellPhone.setCellValueFactory(new PropertyValueFactory<>("cellPhone"));

    table.getColumns().addAll(columnId, columnName, columnEmail, columnCpf, columnRg, columnGender, columnBirth,
        columnCellPhone);

    table.setItems(customers);
  }

  @Override
  public void add() {
    if (this.validate()) {
      Customer customer = CustomerBuilder.builder()
          .addName(name.get())
          .addEmail(email.get())
          .addCpf(cpf.get())
          .addRg(rg.get())
          .addGender(gender.get())
          .addBirth(birth.get())
          .addCellPhone(cellPhone.get())
          .get();

      if (dao.create(customer)) {
        this.customers.clear();
        customers.addAll(dao.all());
        this.clear();
        new Alert(Alert.AlertType.INFORMATION, "Cliente adicionado com sucesso").show();
      } else {
        new Alert(Alert.AlertType.ERROR, "Erro ao inserir no banco de dados").show();
      }
    }
  }

  @Override
  public void search() {
    List<Customer> customers = dao.list(name.get());
    this.customers.clear();
    this.customers.addAll(customers);
  }

  public TableView<Customer> getTable() {
    return table;
  }

  @Override
  public boolean validate() {
    try {
      if (name.get().equals("") || name.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Nome é obrigatório").show();
        return false;
      }

      if (email.get().equals("") || email.get().equals(null) || !email.get().contains("@")
          || !email.get().contains(".")) {
        new Alert(Alert.AlertType.ERROR, "Email é inválido").show();
        return false;
      }

      if (cpf.get().equals("") || cpf.get().equals(null) || cpf.get().length() > 11 || cpf.get().length() < 11) {
        new Alert(Alert.AlertType.ERROR, "CPF é inválido").show();
        return false;
      }

      if (rg.get().equals("") || rg.get().equals(null) || rg.get().length() > 9 || rg.get().length() < 9) {
        new Alert(Alert.AlertType.ERROR, "RG é inválido").show();
        return false;
      }

      if (gender.get().equals("") || gender.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Gênero é obrigatório").show();
        return false;
      }

      if (DateConverter.isValid(birth.get().toString()) || birth.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Data de nascimento é obrigatório").show();
        return false;
      }

      if (cellPhone.get().equals("") || cellPhone.get().equals(null) || cellPhone.get().length() > 15
          || cellPhone.get().length() < 10) {
        new Alert(Alert.AlertType.ERROR, "Celular é inválido").show();
        return false;
      }

      return true;
    } catch (Exception e) {
      new Alert(Alert.AlertType.ERROR, "Campos inválidos").show();
      return false;
    }
  }

  @Override
  public void clear() {
    name.set(null);
    email.set(null);
    cpf.set(null);
    rg.set(null);
    gender.set(null);
    birth.set(null);
    cellPhone.set(null);
    id.set(null);
  }

  @Override
  public boolean update() {
    if (id.get() == null) {
      new Alert(Alert.AlertType.INFORMATION, "Id inválido").show();
      return false;
    }

    if (this.validate()) {
      Customer customer = CustomerBuilder.builder()
              .addId(Integer.parseInt(id.get()))
              .addName(name.get())
              .addEmail(email.get())
              .addCpf(cpf.get())
              .addRg(rg.get())
              .addGender(gender.get())
              .addBirth(birth.get())
              .addCellPhone(cellPhone.get())
              .get();

      dao.update(customer);
      this.clear();

      this.customers.clear();
      customers.addAll(dao.all());

      new Alert(Alert.AlertType.INFORMATION, "Cliente atualizado com sucesso").show();

      return true;
    }

    return false;
  }

  @Override
  public boolean delete() {
    if (id.get() == null) {
      new Alert(Alert.AlertType.INFORMATION, "Id inválido").show();
      return false;
    }


      Customer customer = CustomerBuilder.builder()
              .addId(Integer.parseInt(id.get()))
              .addName(name.get())
              .addEmail(email.get())
              .addCpf(cpf.get())
              .addRg(rg.get())
              .addGender(gender.get())
              .addBirth(birth.get())
              .addCellPhone(cellPhone.get())
              .get();

      dao.remove(customer);
      this.clear();

      this.customers.clear();
      customers.addAll(dao.all());

      new Alert(Alert.AlertType.INFORMATION, "Cliente deletado com sucesso").show();

      return true;

  }

  @Override
  public boolean get() {
    if (id.get() == null) {
      new Alert(Alert.AlertType.INFORMATION, "Id inválido").show();
      return false;
    }

    Customer customer = dao.get(Integer.parseInt(id.get()));

    name.set(customer.getName());
    email.set(customer.getEmail());
    cpf.set(customer.getCpf());
    rg.set(customer.getRg());
    gender.set(customer.getGender());
    birth.set(customer.getBirth());
    cellPhone.set(customer.getCellPhone());

    return true;
  }
}
