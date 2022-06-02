package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import database.CreditCardDao;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CreditCard;
import model.CreditCardBuilder;
import util.DateConverter;

public class CreditCardController implements IController<CreditCard> {
  private CreditCardDao dao = new CreditCardDao(con);

  private ObservableList<String> customers = FXCollections.observableArrayList();

  private ObservableList<CreditCard> creditCards = FXCollections.observableArrayList();

  private StringProperty owner = new SimpleStringProperty();
  private StringProperty name = new SimpleStringProperty();
  private StringProperty number = new SimpleStringProperty();
  private StringProperty country = new SimpleStringProperty();
  private ObjectProperty<LocalDate> expiry = new SimpleObjectProperty<>();
  private StringProperty cvv = new SimpleStringProperty();

  private TableView<CreditCard> table = new TableView<>();

  public StringProperty ownerProperty() {
    return owner;
  }

  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty numberProperty() {
    return number;
  }

  public StringProperty countryProperty() {
    return country;
  }

  public ObjectProperty<LocalDate> expiryProperty() {
    return expiry;
  }

  public StringProperty cvvProperty() {
    return cvv;
  }

  @SuppressWarnings("unchecked")
  public CreditCardController() {
    creditCards.addAll(dao.all());
    customers.addAll(dao.allCustomers());

    TableColumn<CreditCard, String> columnOwner = new TableColumn<>("Cliente");
    columnOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));

    TableColumn<CreditCard, String> columnName = new TableColumn<>("Titular");
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<CreditCard, String> columnNumber = new TableColumn<>("Número");
    columnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));

    TableColumn<CreditCard, String> columnCountry = new TableColumn<>("País");
    columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

    TableColumn<CreditCard, String> columnExpiry = new TableColumn<>("Expiração");
    columnExpiry.setCellValueFactory((item) -> {
      LocalDate dt = item.getValue().getExpiry();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
      return new ReadOnlyStringWrapper(dt.format(formatter));
    });

    TableColumn<CreditCard, String> columnCvv = new TableColumn<>("CVV");
    columnCvv.setCellValueFactory(new PropertyValueFactory<>("cvv"));

    table.getColumns().addAll(columnOwner, columnNumber, columnCountry, columnExpiry, columnCvv);

    table.setItems(creditCards);
  }

  @Override
  public void add() {
    if (this.validate()) {
      CreditCard creditCard = CreditCardBuilder.builder()
          .addCreditCard(name.get(), number.get(), country.get(),
              expiry.get(), cvv.get())
          .addOwner(owner.get())
          .get();

      if (dao.create(creditCard)) {
        creditCards.add(creditCard);
        this.clear();
      } else {
        new Alert(Alert.AlertType.ERROR, "Erro ao inserir no banco de dados").show();
      }
    }
  }

  @Override
  public void search() {
    String[] mergedOwner = owner.get().split(":");

    List<CreditCard> creditCards = dao.list(mergedOwner[0]);
    this.creditCards.clear();
    this.creditCards.addAll(creditCards);
  }

  public TableView<CreditCard> getTable() {
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

      if (name.get().equals("") || name.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Titular é obrigatório").show();
        return false;
      }

      if (number.get().equals("") || number.get().equals(null) || Long.parseLong(number.get()) <= 0
          || number.get().length() > 16 || number.get().length() < 16) {
        new Alert(Alert.AlertType.ERROR, "Número é inválido").show();
        return false;
      }

      if (country.get().equals("") || country.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "País é obrigatório").show();
        return false;
      }

      if (DateConverter.isValid(expiry.get().toString()) || expiry.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Expiração é inválida").show();
        return false;
      }

      if (cvv.get().equals("") || cvv.get().equals(null) || cvv.get().length() > 4 || cvv.get().length() < 3) {
        new Alert(Alert.AlertType.ERROR, "CVV é obrigatório").show();
        return false;
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      new Alert(Alert.AlertType.ERROR, "Campos inválidos").show();
      return false;
    }
  }

  @Override
  public void clear() {
    owner.set(null);
    name.set("");
    number.set("");
    country.set("");
    expiry.set(null);
    cvv.set("");
  }

}
