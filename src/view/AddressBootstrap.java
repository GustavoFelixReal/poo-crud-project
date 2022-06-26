package src.view;

import javafx.application.Application;
import javafx.stage.Stage;
import src.scenes.AddressScene;

public class AddressBootstrap extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    stage.setScene(new AddressScene().get());
    stage.setTitle("Endereços");
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch(AddressBootstrap.class, args);
  }

}
