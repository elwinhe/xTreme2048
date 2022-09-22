import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    private Button[][] btns = new Button[4][4];

    @Override
    public void start(Stage primaryStage) {

        initBtnsArray();
        Group root = new Group();

        root.getChildren().add(getGrid());
        Scene scene = new Scene(root, 101, 101);

        primaryStage.setTitle("2048");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Pane getGrid() {
        int i = 0;
        int j = 0;
        GridPane gridPane = new GridPane();
        for(i = 0; i<4; i++) {
        	for (j = 0; j<4; j++) {
                gridPane.add(btns[i][j], i*(i+(int)btns.length), j);
        	}
            // do something with your button
            // maybe add an EventListener or something
           
        }
        return gridPane;
    }

    private void initBtnsArray() {
        for(int i = 0; i < btns.length; i++) {
        	for(int j = 0; j < btns.length; j++) { 
            btns[i][j] = new Button("   ");
        	}
        }
    }
}