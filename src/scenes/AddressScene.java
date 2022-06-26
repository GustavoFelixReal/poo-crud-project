package src.scenes;

import src.controller.AddressController;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import src.util.LayoutStyle;

public class AddressScene implements IBoundary {
  private AddressController controller = new AddressController();

  private TextField txtId = new TextField();
  private TextField txtStreet = new TextField();
  private TextField txtNumber = new TextField();
  private TextField txtLine2 = new TextField();
  private TextField txtCityArea = new TextField();
  private TextField txtCity = new TextField();
  private TextField txtState = new TextField();
  private TextField txtCountry = new TextField();
  private TextField txtZipCode = new TextField();

  private ChoiceBox<String> cbOwner = new ChoiceBox<>(controller.getCustomersOptions());

  private Button btnAdd = new Button("Adicionar");
  private Button btnSearch = new Button("Pesquisar por cliente");
  private Button btnUpdateById = new Button("Alterar por ID");
  private Button btnUpdate = new Button("Atualizar");
  private Button btnDelete = new Button("Excluir");
  private Button btnBack = new Button("Voltar");

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

    // Cliente
    grid.add(new Label("* Cliente"), 0, 0);
    grid.add(cbOwner, 0, 1);
    cbOwner.setPrefSize(300, 30);
    cbOwner.setStyle(LayoutStyle.textFieldStyle);

    // Logradouro
    grid.add(new Label("* Logradouro"), 0, 2);
    grid.add(txtStreet, 0, 3);
    txtStreet.setPrefSize(300, 30);
    txtStreet.setStyle(LayoutStyle.textFieldStyle);

    // Número
    grid.add(new Label("* Número"), 0, 4);
    grid.add(txtNumber, 0, 5);
    txtNumber.setPrefSize(300, 30);
    txtNumber.setStyle(LayoutStyle.textFieldStyle);

    // Complemento
    grid.add(new Label("Complemento"), 0, 6);
    grid.add(txtLine2, 0, 7);
    txtLine2.setPrefSize(300, 30);
    txtLine2.setStyle(LayoutStyle.textFieldStyle);

    // Bairro
    grid.add(new Label("* Bairro"), 0, 8);
    grid.add(txtCityArea, 0, 9);
    txtCityArea.setPrefSize(300, 30);
    txtCityArea.setStyle(LayoutStyle.textFieldStyle);

    // Cidade
    grid.add(new Label("* Cidade"), 0, 10);
    grid.add(txtCity, 0, 11);
    txtCity.setPrefSize(300, 30);
    txtCity.setStyle(LayoutStyle.textFieldStyle);

    // Estado
    grid.add(new Label("* Estado"), 0, 12);
    grid.add(txtState, 0, 13);
    txtState.setPrefSize(300, 30);
    txtState.setStyle(LayoutStyle.textFieldStyle);

    // País
    grid.add(new Label("* País"), 0, 14);
    grid.add(txtCountry, 0, 15);
    txtCountry.setPrefSize(300, 30);
    txtCountry.setStyle(LayoutStyle.textFieldStyle);

    // CEP
    grid.add(new Label("* CEP (00000-000)"), 0, 16);
    grid.add(txtZipCode, 0, 17);
    txtZipCode.setPrefSize(300, 30);
    txtZipCode.setStyle(LayoutStyle.textFieldStyle);

    // Id
    grid.add(new Label("ID"), 0, 18);
    grid.add(txtId, 0, 19);
    txtId.setPrefSize(300, 30);
    txtId.setStyle(LayoutStyle.textFieldStyle);

    // Button Group
    GridPane buttonGroup1 = new GridPane();
    GridPane.setMargin(buttonGroup1, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup1, 0, 20);

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
    grid.add(buttonGroup2, 0, 21);


    buttonGroup2.add(btnUpdateById, 0, 0);
    btnUpdateById.setStyle(LayoutStyle.buttonStyles);


    buttonGroup2.setHgap(5);

    // Button Group 3
    GridPane buttonGroup3 = new GridPane();
    GridPane.setMargin(buttonGroup3, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup3, 0, 21);
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
    Bindings.bindBidirectional(controller.ownerProperty(), cbOwner.valueProperty());
    Bindings.bindBidirectional(controller.streetProperty(), txtStreet.textProperty());
    Bindings.bindBidirectional(controller.numberProperty(), txtNumber.textProperty());
    Bindings.bindBidirectional(controller.line2Property(), txtLine2.textProperty());
    Bindings.bindBidirectional(controller.cityAreaProperty(), txtCityArea.textProperty());
    Bindings.bindBidirectional(controller.cityProperty(), txtCity.textProperty());
    Bindings.bindBidirectional(controller.stateProperty(), txtState.textProperty());
    Bindings.bindBidirectional(controller.countryProperty(), txtCountry.textProperty());
    Bindings.bindBidirectional(controller.zipCodeProperty(), txtZipCode.textProperty());
    Bindings.bindBidirectional(controller.idProperty(), txtId.textProperty());

    // Criação da Scene
    Scene scene = new Scene(main, 1000, 560);

    return scene;
  }
}
