package scenes;

import java.time.format.DateTimeFormatter;

import controller.CreditCardController;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.LocalDateStringConverter;

public class CreditCardScene implements IBoundary {
  private CreditCardController controller = new CreditCardController();

  private Insets layoutSpacing = new Insets(10, 20, 10, 20);
  private Insets buttonGroupSpacing = new Insets(10, 0, 10, 0);
  private Insets tableBoxSpacing = new Insets(0, 10, 0, 10);

  private TextField txtName = new TextField();
  private TextField txtNumber = new TextField();
  private TextField txtCountry = new TextField();
  private TextField txtExpiry = new TextField();
  private TextField txtCvv = new TextField();

  private String textFieldStyle = "-fx-font: 14 arial;";

  private ChoiceBox<String> cbOwner = new ChoiceBox<>(controller.getCustomersOptions());

  private Button btnAdd = new Button("Adicionar");
  private Button btnSearch = new Button("Pesquisar por cliente");
  private String buttonStyles = "-fx-padding: 10;";

  @Override
  public Scene get() {
    HBox main = new HBox();
    main.setPadding(layoutSpacing);

    GridPane container = new GridPane();
    GridPane grid = new GridPane();

    main.getChildren().add(container);
    container.add(grid, 0, 0);

    // Tabela
    HBox tableBox = new HBox();
    tableBox.getChildren().add(controller.getTable());
    container.add(tableBox, 1, 0);
    tableBox.setPadding(tableBoxSpacing);

    // Titular
    grid.add(new Label("Titular"), 0, 0);
    grid.add(cbOwner, 0, 1);
    cbOwner.setPrefSize(300, 30);
    cbOwner.setStyle(textFieldStyle);

    // Número
    grid.add(new Label("Número"), 0, 2);
    grid.add(txtNumber, 0, 3);
    txtNumber.setPrefSize(300, 30);
    txtNumber.setStyle(textFieldStyle);

    // País
    grid.add(new Label("País"), 0, 4);
    grid.add(txtCountry, 0, 5);
    txtCountry.setPrefSize(300, 30);
    txtCountry.setStyle(textFieldStyle);

    // Expiração
    grid.add(new Label("Expiração"), 0, 6);
    grid.add(txtExpiry, 0, 7);
    txtExpiry.setPrefSize(300, 30);
    txtExpiry.setStyle(textFieldStyle);

    // CVV
    grid.add(new Label("CVV"), 0, 8);
    grid.add(txtCvv, 0, 9);
    txtCvv.setPrefSize(300, 30);
    txtCvv.setStyle(textFieldStyle);

    // Button Group
    GridPane buttonGroup = new GridPane();
    GridPane.setMargin(buttonGroup, buttonGroupSpacing);
    grid.add(buttonGroup, 0, 10);

    buttonGroup.add(btnAdd, 0, 0);
    btnAdd.setStyle(buttonStyles);

    buttonGroup.add(btnSearch, 1, 0);
    btnSearch.setStyle(buttonStyles);

    // Button Actions
    btnAdd.setOnAction((e) -> controller.add());
    btnSearch.setOnAction((e) -> controller.search());

    // Data Formatters
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDateStringConverter ldc = new LocalDateStringConverter(formatter, null);

    // Listeners de Valores
    Bindings.bindBidirectional(controller.ownerProperty(), cbOwner.valueProperty());
    Bindings.bindBidirectional(controller.nameProperty(), txtName.textProperty());
    Bindings.bindBidirectional(controller.numberProperty(), txtNumber.textProperty());
    Bindings.bindBidirectional(controller.countryProperty(), txtCountry.textProperty());
    Bindings.bindBidirectional(txtExpiry.textProperty(), controller.expiryProperty(), ldc);
    Bindings.bindBidirectional(controller.cvvProperty(), txtCvv.textProperty());

    // Criação da Scene
    Scene scene = new Scene(main, 1000, 500);

    return scene;
  }
}
