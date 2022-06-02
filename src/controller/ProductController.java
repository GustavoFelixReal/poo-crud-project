package controller;

import java.util.List;

import database.ProductDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product;
import model.ProductBuilder;

public class ProductController implements IController<Product> {

  private ProductDao dao = new ProductDao(con);

  private ObservableList<Product> products = FXCollections.observableArrayList();

  private StringProperty name = new SimpleStringProperty();
  private StringProperty price = new SimpleStringProperty();
  private StringProperty manufacturer = new SimpleStringProperty();
  private StringProperty description = new SimpleStringProperty();
  private StringProperty keyFeatures = new SimpleStringProperty();

  private TableView<Product> table = new TableView<>();

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

    table.getColumns().addAll(columnName, columnPrice, columnManufacturer, columnDescription, columnKeyFeatures);

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
    name.set("");
    price.set("");
    manufacturer.set("");
    description.set("");
    keyFeatures.set("");
  }
}
