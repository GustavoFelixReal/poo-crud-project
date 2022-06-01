package view;

import javafx.application.Application;
import javafx.stage.Stage;
import scenes.ProductScene;

public class ProductBootstrap extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    stage.setScene(new ProductScene().get());
    stage.setTitle("Produtos");
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch(ProductBootstrap.class, args);
  } 
}
