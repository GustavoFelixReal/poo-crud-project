package controller;

import java.util.List;

import database.AddressDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Address;
import model.AddressBuilder;

public class AddressController implements IController<Address> {
  private AddressDao dao = new AddressDao(con);

  private ObservableList<String> customers = FXCollections.observableArrayList();

  private ObservableList<Address> addresses = FXCollections.observableArrayList();

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

    TableColumn<Address, String> columnOwner = new TableColumn<>("Cliente");
    columnOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));

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

    table.getColumns().addAll(columnOwner, columnStreet, columnNumber, columnLine2, columnCityArea, columnCity,
        columnState, columnCountry, columnZipCode);

    table.setItems(addresses);
  }

  @Override
  public void add() {
    if (this.validate()) {
      int number = Integer.parseInt(this.number.get());

      Address address = AddressBuilder.builder()
          .addAddress(street.get(), number, cityArea.get(), city.get(), state.get(), country.get(), zipCode.get())
          .addLine2(line2.get())
          .addOwner(owner.get())
          .get();

      if (dao.create(address)) {
        addresses.add(address);
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
}
