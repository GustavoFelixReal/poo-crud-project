package view;

import javafx.application.Application;
import javafx.stage.Stage;
import scenes.InitScene;

public class Bootstrap extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    stage.setScene(new InitScene().get());
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch(Bootstrap.class, args);
  }
}