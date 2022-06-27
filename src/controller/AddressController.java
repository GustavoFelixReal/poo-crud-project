package src.controller;

import java.util.List;

import src.database.AddressDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import src.database.CustomerDao;
import src.model.*;
import src.util.HibernateUtil;

public class AddressController implements IController<Address> {
  private AddressDao dao = new AddressDao(HibernateUtil.getSessionFactory());
  private CustomerDao customerDao = new CustomerDao(HibernateUtil.getSessionFactory());

  private ObservableList<String> customers = FXCollections.observableArrayList();

  private ObservableList<Address> addresses = FXCollections.observableArrayList();

  private StringProperty id = new SimpleStringProperty();
  private StringProperty street = new SimpleStringProperty();
  private StringProperty number = new SimpleStringProperty();
  private StringProperty line2 = new SimpleStringProperty();
  private StringProperty cityArea = new SimpleStringProperty();
  private StringProperty city = new SimpleStringProperty();
  private StringProperty state = new SimpleStringProperty();
  private StringProperty country = new SimpleStringProperty();
  private StringProperty zipCode = new SimpleStringProperty();
  private StringProperty owner = new SimpleStringProperty();

  private TableView<Address> table = new TableView<>();

  public StringProperty idProperty() {
    return id;
  }

  public StringProperty streetProperty() {
    return street;
  }

  public StringProperty numberProperty() {
    return number;
  }

  public StringProperty line2Property() {
    return line2;
  }

  public StringProperty cityAreaProperty() {
    return cityArea;
  }

  public StringProperty cityProperty() {
    return city;
  }

  public StringProperty stateProperty() {
    return state;
  }

  public StringProperty countryProperty() {
    return country;
  }

  public StringProperty zipCodeProperty() {
    return zipCode;
  }

  public StringProperty ownerProperty() {
    return owner;
  }

  @SuppressWarnings("unchecked")
  public AddressController() {
    addresses.addAll(dao.all());
    customers.addAll(dao.allCustomers());

    TableColumn<Address, String> columnId = new TableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Address, String> columnOwner = new TableColumn<>("Cliente");
    columnOwner.setCellValueFactory(new PropertyValueFactory<>("ownerConcat"));

    TableColumn<Address, String> columnStreet = new TableColumn<>("Logradouro");
    columnStreet.setCellValueFactory(new PropertyValueFactory<>("street"));

    TableColumn<Address, Integer> columnNumber = new TableColumn<>("Número");
    columnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));

    TableColumn<Address, String> columnLine2 = new TableColumn<>("Complemento");
    columnLine2.setCellValueFactory(new PropertyValueFactory<>("line2"));

    TableColumn<Address, String> columnCityArea = new TableColumn<>("Bairro");
    columnCityArea.setCellValueFactory(new PropertyValueFactory<>("cityArea"));

    TableColumn<Address, String> columnCity = new TableColumn<>("Cidade");
    columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));

    TableColumn<Address, String> columnState = new TableColumn<>("Estado");
    columnState.setCellValueFactory(new PropertyValueFactory<>("state"));

    TableColumn<Address, String> columnCountry = new TableColumn<>("País");
    columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

    TableColumn<Address, String> columnZipCode = new TableColumn<>("CEP");
    columnZipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));

    table.getColumns().addAll(columnId, columnOwner, columnStreet, columnNumber, columnLine2, columnCityArea, columnCity,
        columnState, columnCountry, columnZipCode);

    table.setItems(addresses);
  }

  @Override
  public void add() {
    if (this.validate()) {
      int number = Integer.parseInt(this.number.get());

      String[] mergedOwner = owner.get().split(":");
      int owner = Integer.parseInt(mergedOwner[0]);

      Customer customer = CustomerBuilder.builder().addId(owner).get();

      Address address = AddressBuilder.builder()
          .addAddress(street.get(), number, cityArea.get(), city.get(), state.get(), country.get(), zipCode.get())
          .addLine2(line2.get())
          .addOwner(customer)
          .addOwnerString(this.owner.get())
          .get();

      if (dao.create(address)) {
        addresses.add(address);
        this.clear();
        new Alert(Alert.AlertType.INFORMATION, "Endereço adicionado com sucesso").show();
      } else {
        new Alert(Alert.AlertType.ERROR, "Erro ao inserir no banco de dados").show();
      }
    }
  }

  @Override
  public void search() {
    String[] mergedOwner = owner.get().split(":");

    List<Address> addresses = dao.list(mergedOwner[0]);
    this.addresses.clear();
    this.addresses.addAll(addresses);
  }

  public TableView<Address> getTable() {
    return table;
  }

  public ObservableList<String> getCustomersOptions() {
    return customers;
  }

  @Override
  public boolean validate() {
    try {
      if (owner.get() == null) {
        new Alert(Alert.AlertType.ERROR, "Cliente é obrigatório").show();
        return false;
      }

      if (street.get().equals("") || street.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Logradouro é obrigatório").show();
        return false;
      }

      if (number.get().equals("") || number.get().equals(null) || Integer.parseInt(number.get()) <= 0) {
        new Alert(Alert.AlertType.ERROR, "Número inválido").show();
        return false;
      }

      if (cityArea.get().equals("") || cityArea.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Bairro é obrigatório").show();
        return false;
      }

      if (city.get().equals("") || city.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Cidade é obrigatório").show();
        return false;
      }

      if (state.get().equals("") || state.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Estado é obrigatório").show();
        return false;
      }

      if (country.get().equals("") || country.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "País é obrigatório").show();
        return false;
      }

      if (zipCode.get().equals("") || zipCode.get().equals(null) || zipCode.get().length() > 9
          || zipCode.get().length() < 9) {
        new Alert(Alert.AlertType.ERROR, "CEP é inválido").show();
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
    street.set(null);
    number.set(null);
    line2.set(null);
    cityArea.set(null);
    city.set(null);
    state.set(null);
    country.set(null);
    zipCode.set(null);
    owner.set(null);
    id.set(null);
  }

  @Override
  public boolean update() {
    if (id.get() == null) {
      new Alert(Alert.AlertType.INFORMATION, "Id inválido").show();
      return false;
    }

    if (this.validate()) {
      int number = Integer.parseInt(this.number.get());

      String[] mergedOwner = owner.get().split(":");
      int owner = Integer.parseInt(mergedOwner[0]);

      Customer customer = customerDao.get(owner);

      Address address = AddressBuilder.builder()
              .addId(Integer.parseInt(id.get()))
              .addAddress(street.get(), number, cityArea.get(), city.get(), state.get(), country.get(), zipCode.get())
              .addLine2(line2.get())
              .addOwner(customer)
              .addOwnerString(this.owner.get())
              .get();

      dao.update(address);
      this.clear();

      this.addresses.clear();
      addresses.addAll(dao.all());

      new Alert(Alert.AlertType.INFORMATION, "Endereço atualizado com sucesso").show();

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


      int number = Integer.parseInt(this.number.get());

      String[] mergedOwner = owner.get().split(":");
      int owner = Integer.parseInt(mergedOwner[0]);

      Customer customer = customerDao.get(owner);

      Address address = AddressBuilder.builder()
              .addId(Integer.parseInt(id.get()))
              .addAddress(street.get(), number, cityArea.get(), city.get(), state.get(), country.get(), zipCode.get())
              .addLine2(line2.get())
              .addOwner(customer)
              .addOwnerString(this.owner.get())
              .get();

      dao.remove(address);
      this.clear();

      this.addresses.clear();
      addresses.addAll(dao.all());

      new Alert(Alert.AlertType.INFORMATION, "Endereço deletado com sucesso").show();

      return true;


  }

  @Override
  public boolean get() {
    if (id.get() == null) {
      new Alert(Alert.AlertType.INFORMATION, "Id inválido").show();
      return false;
    }

    Address address = dao.get(Integer.parseInt(id.get()));

    owner.set(address.getOwner().getId() + ":" + address.getOwner().getName());
    street.set(address.getStreet());
    number.set(Integer.toString(address.getNumber()));
    line2.set(address.getLine2());
    cityArea.set(address.getCityArea());
    city.set(address.getCity());
    state.set(address.getState());
    country.set(address.getCountry());
    zipCode.set(address.getZipCode());

    return true;
  }
}
