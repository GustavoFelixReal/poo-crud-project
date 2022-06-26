package src.scenes;

import javafx.geometry.Orientation;
import javafx.scene.control.*;
import src.controller.CustomerController;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import src.util.DateConverter;
import src.util.LayoutStyle;

public class CustomerScene implements IBoundary {
  private TextField txtId = new TextField();
  private TextField txtName = new TextField();
  private TextField txtEmail = new TextField();
  private TextField txtCpf = new TextField();
  private TextField txtRg = new TextField();
  private TextField txtBirth = new TextField();
  private TextField txtCellPhone = new TextField();

  private ChoiceBox<String> cbGender = new ChoiceBox<>(FXCollections.observableArrayList("M", "F"));

  private Button btnAdd = new Button("Adicionar");
  private Button btnSearch = new Button("Listar por nome");
  private Button btnUpdateById = new Button("Alterar por ID");
  private Button btnUpdate = new Button("Atualizar");
  private Button btnDelete = new Button("Excluir");
  private Button btnBack = new Button("Voltar");

  private CustomerController controller = new CustomerController();

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

    // Nome Completo
    grid.add(new Label("* Nome completo"), 0, 0);
    grid.add(txtName, 0, 1);
    txtName.setPrefSize(300, 30);
    txtName.setStyle(LayoutStyle.textFieldStyle);

    // Email
    grid.add(new Label(" * Email"), 0, 2);
    grid.add(txtEmail, 0, 3);
    txtEmail.setPrefSize(300, 30);
    txtEmail.setStyle(LayoutStyle.textFieldStyle);

    // CPF
    grid.add(new Label("* CPF (Sem dígitos)"), 0, 4);
    grid.add(txtCpf, 0, 5);
    txtCpf.setPrefSize(300, 30);
    txtCpf.setStyle(LayoutStyle.textFieldStyle);

    // RG
    grid.add(new Label("* RG (Sem dígitos)"), 0, 6);
    grid.add(txtRg, 0, 7);
    txtRg.setPrefSize(300, 30);
    txtRg.setStyle(LayoutStyle.textFieldStyle);

    // Sexo
    grid.add(new Label("* Sexo"), 0, 8);
    grid.add(cbGender, 0, 9);
    cbGender.setPrefSize(300, 30);
    cbGender.setStyle(LayoutStyle.textFieldStyle);

    // Data de Nascimento
    grid.add(new Label("* Data de nascimento (dd/MM/yyyy)"), 0, 10);
    grid.add(txtBirth, 0, 11);
    txtBirth.setPrefSize(300, 30);
    txtBirth.setStyle(LayoutStyle.textFieldStyle);

    // Celular
    grid.add(new Label("* Celular"), 0, 12);
    grid.add(txtCellPhone, 0, 13);
    txtCellPhone.setPrefSize(300, 30);
    txtCellPhone.setStyle(LayoutStyle.textFieldStyle);

    // Id
    grid.add(new Label("ID"), 0, 14);
    grid.add(txtId, 0, 15);
    txtId.setPrefSize(300, 30);
    txtId.setStyle(LayoutStyle.textFieldStyle);

    // Button Group 1
    GridPane buttonGroup1 = new GridPane();
    GridPane.setMargin(buttonGroup1, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup1, 0, 16);

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
    grid.add(buttonGroup2, 0, 17);


    buttonGroup2.add(btnUpdateById, 0, 0);
    btnUpdateById.setStyle(LayoutStyle.buttonStyles);


    buttonGroup2.setHgap(5);

    // Button Group 3
    GridPane buttonGroup3 = new GridPane();
    GridPane.setMargin(buttonGroup3, LayoutStyle.buttonGroupSpacing);
    grid.add(buttonGroup3, 0, 17);
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
    Bindings.bindBidirectional(controller.emailProperty(), txtEmail.textProperty());
    Bindings.bindBidirectional(controller.cpfProperty(), txtCpf.textProperty());
    Bindings.bindBidirectional(controller.rgProperty(), txtRg.textProperty());
    Bindings.bindBidirectional(controller.genderProperty(), cbGender.valueProperty());
    Bindings.bindBidirectional(txtBirth.textProperty(), controller.birthProperty(), DateConverter.localDateConverter());
    Bindings.bindBidirectional(controller.cellPhoneProperty(), txtCellPhone.textProperty());
    Bindings.bindBidirectional(controller.idProperty(), txtId.textProperty());

    // Criação da Scene
    Scene scene = new Scene(main, 800, 470);

    return scene;
  }

}
