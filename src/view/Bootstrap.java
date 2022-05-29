package view;

import controller.ProductController;
import javafx.application.Application;
import javafx.stage.Stage;
import scenes.InitScene;

public class Bootstrap extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    System.out.println(new ProductController().all().toString());
    stage.setScene(new InitScene().getScene());
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch(Bootstrap.class, args);
  }
}