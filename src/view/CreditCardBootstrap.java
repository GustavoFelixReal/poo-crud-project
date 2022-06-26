package src.view;

import javafx.application.Application;
import javafx.stage.Stage;
import src.scenes.CreditCardScene;

public class CreditCardBootstrap extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    stage.setScene(new CreditCardScene().get());
    stage.setTitle("Cartões de Crédito");
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch(CreditCardBootstrap.class, args);
  }
}
