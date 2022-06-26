package src.scenes;

import src.controller.ProductController;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import src.util.LayoutStyle;

public class ProductScene implements IBoundary {
  private TextField txtId = new TextField();
  private TextField txtName = new TextField();
  private TextField txtPrice = new TextField();
  private TextField txtManufacturer = new TextField();
  private TextArea txtDescription = new TextArea();
  private TextArea txtKeyFeatures = new TextArea();

  private Button btnAdd = new Button("Adicionar");
  private Button btnSearch = new Button("Pesquisar por nome");
  private Button btnUpdateById = new Button("Alterar por ID");
  private Button btnUpdate = new Button("Atualizar");
  private Button btnDelete = new Button("Excluir");
  private Button btnBack = new Button("Voltar");

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

    // Id
    grid.add(new Label("ID"), 0, 10);
    grid.add(txtId, 0, 11);
    txtId.setPrefSize(300, 30);
    txtId.setStyle(LayoutStyle.textFieldStyle);

    // Button Group
    GridPane buttonGroup1 = new GridPane();
    GridPane.setMargin(buttonGroup1, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup1, 0, 12);

    buttonGroup1.add(btnAdd, 0, 0);
    btnAdd.setStyle(LayoutStyle.buttonStyles);

    buttonGroup1.add(btnSearch, 1, 0);
    btnSearch.setStyle(LayoutStyle.buttonStyles);

    buttonGroup1.setHgap(5);

    // Button Actions
    btnAdd.setOnAction((e) -> controller.add());
    btnSearch.setOnAction((e) -> controller.search());

    // Button Group 2
    GridPane buttonGroup2 = new GridPane();
    GridPane.setMargin(buttonGroup2, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup2, 0, 13);


    buttonGroup2.add(btnUpdateById, 0, 0);
    btnUpdateById.setStyle(LayoutStyle.buttonStyles);


    buttonGroup2.setHgap(5);

    // Button Group 3
    GridPane buttonGroup3 = new GridPane();
    GridPane.setMargin(buttonGroup3, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup3, 0, 13);
    buttonGroup3.setVisible(false);

    buttonGroup3.add(btnUpdate, 0, 0);
    btnUpdate.setStyle(LayoutStyle.buttonStyles);

    buttonGroup3.add(btnDelete, 1, 0);
    btnDelete.setStyle(LayoutStyle.buttonStyles);

    buttonGroup3.add(btnBack, 2, 0);
    btnBack.setStyle(LayoutStyle.buttonStyles);

    buttonGroup3.setHgap(5);

    // Button Actions

    btnUpdateById.setOnAction((e) -> {
      if (controller.get()) {
        txtId.setEditable(false);
        buttonGroup1.setVisible(false);
        buttonGroup2.setVisible(false);
        buttonGroup3.setVisible(true);
      }
    });

    btnUpdate.setOnAction((e) -> {
      if (controller.update()) {
        txtId.setEditable(true);
        buttonGroup1.setVisible(true);
        buttonGroup2.setVisible(true);
        buttonGroup3.setVisible(false);
      }
    });

    btnDelete.setOnAction((e) -> {
      if (controller.delete()) {
        txtId.setEditable(true);
        buttonGroup1.setVisible(true);
        buttonGroup2.setVisible(true);
        buttonGroup3.setVisible(false);
      }
    });

    btnBack.setOnAction((e) -> {
      controller.clear();
      txtId.setEditable(true);
      buttonGroup1.setVisible(true);
      buttonGroup2.setVisible(true);
      buttonGroup3.setVisible(false);
    });

    // Listeners de Valores
    Bindings.bindBidirectional(controller.nameProperty(), txtName.textProperty());
    Bindings.bindBidirectional(controller.priceProperty(), txtPrice.textProperty());
    Bindings.bindBidirectional(controller.manufacturerProperty(), txtManufacturer.textProperty());
    Bindings.bindBidirectional(controller.descriptionProperty(), txtDescription.textProperty());
    Bindings.bindBidirectional(controller.keyFeaturesProperty(), txtKeyFeatures.textProperty());
    Bindings.bindBidirectional(controller.idProperty(), txtId.textProperty());

    // Criação da Scene
    Scene scene = new Scene(main, 650, 440);

    return scene;
  }

}
