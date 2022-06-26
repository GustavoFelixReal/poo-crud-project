package src.scenes;

import src.controller.CreditCardController;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import src.util.DateConverter;
import src.util.LayoutStyle;

public class CreditCardScene implements IBoundary {
  private CreditCardController controller = new CreditCardController();

  private TextField txtId = new TextField();
  private TextField txtName = new TextField();
  private TextField txtNumber = new TextField();
  private TextField txtCountry = new TextField();
  private TextField txtExpiry = new TextField();
  private TextField txtCvv = new TextField();

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

    // Titular
    grid.add(new Label("* Titular"), 0, 2);
    grid.add(txtName, 0, 3);
    txtName.setPrefSize(300, 30);
    txtName.setStyle(LayoutStyle.textFieldStyle);

    // Número
    grid.add(new Label("* Número (16 dígitos)"), 0, 4);
    grid.add(txtNumber, 0, 5);
    txtNumber.setPrefSize(300, 30);
    txtNumber.setStyle(LayoutStyle.textFieldStyle);

    // País
    grid.add(new Label("* País"), 0, 6);
    grid.add(txtCountry, 0, 7);
    txtCountry.setPrefSize(300, 30);
    txtCountry.setStyle(LayoutStyle.textFieldStyle);

    // Expiração
    grid.add(new Label("* Expiração (dd/MM/yyyy)"), 0, 8);
    grid.add(txtExpiry, 0, 9);
    txtExpiry.setPrefSize(300, 30);
    txtExpiry.setStyle(LayoutStyle.textFieldStyle);

    // CVV
    grid.add(new Label("* CVV (min. 3/max. 4)"), 0, 10);
    grid.add(txtCvv, 0, 11);
    txtCvv.setPrefSize(300, 30);
    txtCvv.setStyle(LayoutStyle.textFieldStyle);

    // Id
    grid.add(new Label("ID"), 0, 12);
    grid.add(txtId, 0, 13);
    txtId.setPrefSize(300, 30);
    txtId.setStyle(LayoutStyle.textFieldStyle);

    // Button Group
    GridPane buttonGroup1 = new GridPane();
    GridPane.setMargin(buttonGroup1, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup1, 0, 14);

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
    grid.add(buttonGroup2, 0, 15);


    buttonGroup2.add(btnUpdateById, 0, 0);
    btnUpdateById.setStyle(LayoutStyle.buttonStyles);


    buttonGroup2.setHgap(5);

    // Button Group 3
    GridPane buttonGroup3 = new GridPane();
    GridPane.setMargin(buttonGroup3, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup3, 0, 15);
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
    Bindings.bindBidirectional(controller.nameProperty(), txtName.textProperty());
    Bindings.bindBidirectional(controller.numberProperty(), txtNumber.textProperty());
    Bindings.bindBidirectional(controller.countryProperty(), txtCountry.textProperty());
    Bindings.bindBidirectional(txtExpiry.textProperty(), controller.expiryProperty(),
        DateConverter.localDateConverter());
    Bindings.bindBidirectional(controller.cvvProperty(), txtCvv.textProperty());
    Bindings.bindBidirectional(controller.idProperty(), txtId.textProperty());

    // Criação da Scene
    Scene scene = new Scene(main, 700, 430);

    return scene;
  }
}
