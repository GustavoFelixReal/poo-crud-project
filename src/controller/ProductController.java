package src.controller;

import java.util.List;

import src.database.ProductDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import src.model.Customer;
import src.model.CustomerBuilder;
import src.model.Product;
import src.model.ProductBuilder;
import src.util.HibernateUtil;

public class ProductController implements IController<Product> {

  private ProductDao dao = new ProductDao(HibernateUtil.getSessionFactory());

  private ObservableList<Product> products = FXCollections.observableArrayList();

  private StringProperty id = new SimpleStringProperty();
  private StringProperty name = new SimpleStringProperty();
  private StringProperty price = new SimpleStringProperty();
  private StringProperty manufacturer = new SimpleStringProperty();
  private StringProperty description = new SimpleStringProperty();
  private StringProperty keyFeatures = new SimpleStringProperty();

  private TableView<Product> table = new TableView<>();

  public StringProperty idProperty() {
    return id;
  }

  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty priceProperty() {
    return price;
  }

  public StringProperty manufacturerProperty() {
    return manufacturer;
  }

  public StringProperty descriptionProperty() {
    return description;
  }

  public StringProperty keyFeaturesProperty() {
    return keyFeatures;
  }

  @SuppressWarnings("unchecked")
  public ProductController() {
    products.addAll(dao.all());

    TableColumn<Product, String> columnId = new TableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Product, String> columnName = new TableColumn<>("Nome");
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Product, Double> columnPrice = new TableColumn<>("Preço");
    columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Product, String> columnManufacturer = new TableColumn<>("Fabricante");
    columnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

    TableColumn<Product, String> columnDescription = new TableColumn<>("Descrição");
    columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    TableColumn<Product, String> columnKeyFeatures = new TableColumn<>("Informações Técnicas");
    columnKeyFeatures.setCellValueFactory(new PropertyValueFactory<>("keyFeatures"));

    table.getColumns().addAll(columnId, columnName, columnPrice, columnManufacturer, columnDescription, columnKeyFeatures);

    table.setItems(products);
  }

  @Override
  public void add() {
    if (this.validate()) {
      Product product = ProductBuilder.builder()
          .addName(name.get())
          .addPrice(Double.parseDouble(price.get()))
          .addManufacturer(manufacturer.get())
          .addDescription(description.get())
          .addKeyFeatures(keyFeatures.get())
          .get();

      if (dao.create(product)) {
        products.add(product);
        this.clear();
        new Alert(Alert.AlertType.INFORMATION, "Produto adicionado com sucesso").show();
      } else {
        new Alert(Alert.AlertType.ERROR, "Erro ao inserir no banco de dados").show();
      }
    }
  }

  @Override
  public void search() {
    List<Product> products = dao.list(name.get());
    this.products.clear();
    this.products.addAll(products);
  }

  public TableView<Product> getTable() {
    return table;
  }

  @Override
  public boolean validate() {
    try {
      if (name.get().equals("") || name.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Nome é obrigatório").show();
        return false;
      }

      if (Double.parseDouble(price.get()) <= 0) {
        new Alert(Alert.AlertType.ERROR, "Preço é inválido").show();
        return false;
      }

      if (manufacturer.get().equals("") || name.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Fabricante é obrigatório").show();
        return false;
      }

      if (description.get().equals("") || description.get().equals(null)) {
        new Alert(Alert.AlertType.ERROR, "Descrição é obrigatório").show();
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
    id.set(null);
    name.set(null);
    price.set(null);
    manufacturer.set(null);
    description.set(null);
    keyFeatures.set(null);
  }

  @Override
  public boolean update() {
    if (id.get() == null) {
      new Alert(Alert.AlertType.INFORMATION, "Id inválido").show();
      return false;
    }

    if (this.validate()) {
      Product product = ProductBuilder.builder()
              .addId(Integer.parseInt(id.get()))
              .addName(name.get())
              .addPrice(Double.parseDouble(price.get()))
              .addDescription(description.get())
              .addManufacturer(manufacturer.get())
              .addKeyFeatures(keyFeatures.get())
              .get();

      dao.update(product);
      this.clear();

      this.products.clear();
      products.addAll(dao.all());

      new Alert(Alert.AlertType.INFORMATION, "Produto atualizado com sucesso").show();

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
      Product product = ProductBuilder.builder()
              .addId(Integer.parseInt(id.get()))
              .addName(name.get())
              .addPrice(Double.parseDouble(price.get()))
              .addDescription(description.get())
              .addManufacturer(manufacturer.get())
              .addKeyFeatures(keyFeatures.get())
              .get();

      dao.remove(product);
      this.clear();

      this.products.clear();
      products.addAll(dao.all());

      new Alert(Alert.AlertType.INFORMATION, "Produto deletado com sucesso").show();

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

    Product product = dao.get(Integer.parseInt(id.get()));

    name.set(product.getName());
    price.set(Double.toString(product.getPrice()));
    manufacturer.set(product.getManufacturer());
    description.set(product.getDescription());
    keyFeatures.set(product.getKeyFeatures());

    return true;
  }
}
