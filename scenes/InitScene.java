package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class InitScene implements Boundary {
  @Override
  public Scene getScene() {
    GridPane pane = new GridPane();
    Scene scene = new Scene(pane, 500, 500);

    pane.add(new Label("Teste"), 0, 0);

    return scene;
  }
}
