package src.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import src.database.CreditCardDao;
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
import src.database.CustomerDao;
import src.model.*;
import src.util.DateConverter;
import src.util.HibernateUtil;

public class CreditCardController implements IController<CreditCard> {
  private CreditCardDao dao = new CreditCardDao(HibernateUtil.getSessionFactory());
  private CustomerDao customerDao = new CustomerDao(HibernateUtil.getSessionFactory());

  private ObservableList<String> customers = FXCollections.observableArrayList();

  private ObservableList<CreditCard> creditCards = FXCollections.observableArrayList();

  private StringProperty id = new SimpleStringProperty();
  private StringProperty owner = new SimpleStringProperty();
  private StringProperty name = new SimpleStringProperty();
  private StringProperty number = new SimpleStringProperty();
  private StringProperty country = new SimpleStringProperty();
  private ObjectProperty<LocalDate> expiry = new SimpleObjectProperty<>();
  private StringProperty cvv = new SimpleStringProperty();

  private TableView<CreditCard> table = new TableView<>();

  public StringProperty idProperty() {
    return id;
  }

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

    TableColumn<CreditCard, String> columnId = new TableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<CreditCard, String> columnOwner = new TableColumn<>("Cliente");
    columnOwner.setCellValueFactory(new PropertyValueFactory<>("ownerConcat"));

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

    table.getColumns().addAll(columnId, columnOwner, columnName, columnNumber, columnCountry, columnExpiry, columnCvv);

    table.setItems(creditCards);
  }

  @Override
  public void add() {
    if (this.validate()) {
      String[] mergedOwner = owner.get().split(":");
      int owner = Integer.parseInt(mergedOwner[0]);

      Customer customer = CustomerBuilder.builder().addId(owner).get();

      CreditCard creditCard = CreditCardBuilder.builder()
          .addCreditCard(name.get(), number.get(), country.get(),
              expiry.get(), cvv.get())
          .addOwner(customer)
          .get();

      if (dao.create(creditCard)) {
        creditCards.add(creditCard);
        this.clear();
        new Alert(Alert.AlertType.INFORMATION, "Cartão de Crédito adicionado com sucesso").show();
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

      if (name.get().equals("") || name.get() == null) {
        new Alert(Alert.AlertType.ERROR, "Titular é obrigatório").show();
        return false;
      }

      if (number.get().equals("") || number.get() == null || Long.parseLong(number.get()) <= 0
          || number.get().length() > 16 || number.get().length() < 16) {
        new Alert(Alert.AlertType.ERROR, "Número é inválido").show();
        return false;
      }

      if (country.get().equals("") || country.get() == null) {
        new Alert(Alert.AlertType.ERROR, "País é obrigatório").show();
        return false;
      }

      if (DateConverter.isValid(expiry.get().toString()) || expiry.get() == null) {
        new Alert(Alert.AlertType.ERROR, "Expiração é inválida").show();
        return false;
      }

      if (cvv.get().equals("") || cvv.get() == null || cvv.get().length() > 4 || cvv.get().length() < 3) {
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
    id.set(null);
    owner.set(null);
    name.set(null);
    number.set(null);
    country.set(null);
    expiry.set(null);
    cvv.set(null);
  }

  @Override
  public boolean update() {
    if (id.get() == null) {
      new Alert(Alert.AlertType.INFORMATION, "Id inválido").show();
      return false;
    }

    if (this.validate()) {
      String[] mergedOwner = owner.get().split(":");
      int owner = Integer.parseInt(mergedOwner[0]);

      Customer customer = customerDao.get(owner);

      CreditCard creditCard = CreditCardBuilder.builder()
              .addId(Integer.parseInt(id.get()))
              .addCreditCard(name.get(), number.get(), country.get(),
                      expiry.get(), cvv.get())
              .addOwner(customer)
              .get();

      System.out.println(creditCard);

      dao.update(creditCard);
      this.clear();

      this.creditCards.clear();
      creditCards.addAll(dao.all());

      new Alert(Alert.AlertType.INFORMATION, "Cartão de crédito atualizado com sucesso").show();

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

    if (this.validate()) {
      String[] mergedOwner = owner.get().split(":");
      int owner = Integer.parseInt(mergedOwner[0]);

      Customer customer = customerDao.get(owner);

      CreditCard creditCard = CreditCardBuilder.builder()
              .addId(Integer.parseInt(id.get()))
              .addCreditCard(name.get(), number.get(), country.get(),
                      expiry.get(), cvv.get())
              .addOwner(customer)
              .get();

      dao.remove(creditCard);
      this.clear();

      this.creditCards.clear();
      creditCards.addAll(dao.all());

      new Alert(Alert.AlertType.INFORMATION, "Cartão de crédito deletado com sucesso").show();

      return true;
    }

    return false;
  }

  @Override
  public boolean get() {
    if (id.get() == null) {
      new Alert(Alert.AlertType.INFORMATION, "Id inválido").show();
      return false;
    }

    CreditCard creditCard = dao.get(Integer.parseInt(id.get()));

    owner.set(creditCard.getOwner().getId() + ":" + creditCard.getOwner().getName());
    name.set(creditCard.getOwner().getName());
    number.set(creditCard.getNumber());
    country.set(creditCard.getCountry());
    expiry.set(creditCard.getExpiry());
    cvv.set(creditCard.getCvv());

    return true;
  }

}
