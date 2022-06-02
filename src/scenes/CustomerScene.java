package scenes;

import controller.CustomerController;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import util.DateConverter;
import util.LayoutStyle;

public class CustomerScene implements IBoundary {
  private TextField txtName = new TextField();
  private TextField txtEmail = new TextField();
  private TextField txtCpf = new TextField();
  private TextField txtRg = new TextField();
  private TextField txtBirth = new TextField();
  private TextField txtCellPhone = new TextField();

  private ChoiceBox<String> cbGender = new ChoiceBox<>(FXCollections.observableArrayList("M", "F"));

  private Button btnAdd = new Button("Adicionar");
  private Button btnSearch = new Button("Pesquisar por nome");

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
    Bindings.bindBidirectional(controller.emailProperty(), txtEmail.textProperty());
    Bindings.bindBidirectional(controller.cpfProperty(), txtCpf.textProperty());
    Bindings.bindBidirectional(controller.rgProperty(), txtRg.textProperty());
    Bindings.bindBidirectional(controller.genderProperty(), cbGender.valueProperty());
    Bindings.bindBidirectional(txtBirth.textProperty(), controller.birthProperty(), DateConverter.localDateConverter());
    Bindings.bindBidirectional(controller.cellPhoneProperty(), txtCellPhone.textProperty());

    // Criação da Scene
    Scene scene = new Scene(main, 800, 400);

    return scene;
  }

}
