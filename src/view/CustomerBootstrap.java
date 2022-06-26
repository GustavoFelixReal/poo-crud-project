package src.view;

import javafx.application.Application;
import javafx.stage.Stage;
import src.scenes.CustomerScene;

public class CustomerBootstrap extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    stage.setScene(new CustomerScene().get());
    stage.setResizable(false);
    stage.setTitle("Cliente");
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch(CustomerBootstrap.class, args);
  }
}