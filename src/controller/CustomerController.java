package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import database.CustomerDao;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.CustomerBuilder;

public class CustomerController implements IController<Customer> {
  private CustomerDao dao = new CustomerDao();

  private ObservableList<Customer> customers = FXCollections.observableArrayList();

  private StringProperty name = new SimpleStringProperty();
  private StringProperty email = new SimpleStringProperty();
  private StringProperty cpf = new SimpleStringProperty();
  private StringProperty rg = new SimpleStringProperty();
  private StringProperty gender = new SimpleStringProperty();
  private ObjectProperty<LocalDate> birth = new SimpleObjectProperty<>();
  private StringProperty cellPhone = new SimpleStringProperty();

  private TableView<Customer> table = new TableView<>();

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

  public CustomerController() {
    customers.addAll(dao.all());

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

    table.getColumns().addAll(columnName, columnEmail, columnCpf, columnRg, columnGender, columnBirth,
        columnCellPhone);

    table.setItems(customers);
  }

  @Override
  public void add() {
    Customer customer = CustomerBuilder.builder()
        .addName(name.get())
        .addEmail(email.get())
        .addCpf(cpf.get())
        .addRg(rg.get())
        .addGender(gender.get())
        .addBirth(birth.get())
        .addCellPhone(cellPhone.get())
        .get();

    customers.add(customer);

    dao.create(customer);
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

}
