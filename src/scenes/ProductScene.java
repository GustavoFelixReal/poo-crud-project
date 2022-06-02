package scenes;

import controller.ProductController;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import util.LayoutStyle;

public class ProductScene implements IBoundary {
  private TextField txtName = new TextField();
  private TextField txtPrice = new TextField();
  private TextField txtManufacturer = new TextField();
  private TextArea txtDescription = new TextArea();
  private TextArea txtKeyFeatures = new TextArea();

  private Button btnAdd = new Button("Adicionar");
  private Button btnSearch = new Button("Pesquisar por nome");

  private ProductController controller = new ProductController();

  @Override
  public Scene get() {
    HBox main = new HBox();
    main.setPadding(LayoutStyle.layoutSpacing);

    GridPane container = new GridPane();
    GridPane grid = new GridPane();

    main.getChildren().add(container);
    container.add(grid, 0, 0);

    // Tabela
    HBox tableBox = new HBox();
    tableBox.getChildren().add(controller.getTable());
    container.add(tableBox, 1, 0);
    tableBox.setPadding(LayoutStyle.tableBoxSpacing);

    // Nome
    grid.add(new Label("* Nome"), 0, 0);
    grid.add(txtName, 0, 1);
    txtName.setPrefSize(300, 30);
    txtName.setStyle(LayoutStyle.textFieldStyle);

    // Preço
    grid.add(new Label("* Preço (decimal é .)"), 0, 2);
    grid.add(txtPrice, 0, 3);
    txtPrice.setPrefSize(300, 30);
    txtPrice.setStyle(LayoutStyle.textFieldStyle);

    // Fabricante
    grid.add(new Label("* Fabricante"), 0, 4);
    grid.add(txtManufacturer, 0, 5);
    txtManufacturer.setPrefSize(300, 30);
    txtManufacturer.setStyle(LayoutStyle.textFieldStyle);

    // Descrição
    grid.add(new Label("* Descrição"), 0, 6);
    grid.add(txtDescription, 0, 7);
    txtDescription.setPrefSize(300, 30);
    txtDescription.setStyle(LayoutStyle.textFieldStyle);

    // Ficha Técnica
    grid.add(new Label("Ficha Técnica"), 0, 8);
    grid.add(txtKeyFeatures, 0, 9);
    txtKeyFeatures.setPrefSize(300, 30);
    txtKeyFeatures.setStyle(LayoutStyle.textFieldStyle);

    // Button Group
    GridPane buttonGroup = new GridPane();
    GridPane.setMargin(buttonGroup, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup, 0, 14);

    buttonGroup.add(btnAdd, 0, 0);
    btnAdd.setStyle(LayoutStyle.buttonStyles);

    buttonGroup.add(btnSearch, 1, 0);
    btnSearch.setStyle(LayoutStyle.buttonStyles);

    buttonGroup.setHgap(5);

    // Button Actions
    btnAdd.setOnAction((e) -> controller.add());
    btnSearch.setOnAction((e) -> controller.search());

    // Listeners de Valores
    Bindings.bindBidirectional(controller.nameProperty(), txtName.textProperty());
    Bindings.bindBidirectional(controller.priceProperty(), txtPrice.textProperty());
    Bindings.bindBidirectional(controller.manufacturerProperty(), txtManufacturer.textProperty());
    Bindings.bindBidirectional(controller.descriptionProperty(), txtDescription.textProperty());
    Bindings.bindBidirectional(controller.keyFeaturesProperty(), txtKeyFeatures.textProperty());

    // Criação da Scene
    Scene scene = new Scene(main, 650, 400);

    return scene;
  }

}
