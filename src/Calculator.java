import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Calculator extends Application {

    private final IntegerProperty value = new SimpleIntegerProperty();


    class NumberButtonHandler implements EventHandler<ActionEvent> {
        private final int number ;
        NumberButtonHandler(int number) {
            this.number = number ;
        }
        @Override
        public void handle(ActionEvent event) {
            value.set(value.get() * 10 + number);
        }

    }

    @Override
    public void start(Stage primaryStage) {

        GridPane grid = createGrid();

        for (int n = 1; n<10; n++) {
            Button button = createNumberButton(n);
            int row = (n-1) / 3;
            int col = (n-1) % 3 ;
            grid.add(button, col, 2 - row);
        }

        Button zeroButton = createNumberButton(0);
        grid.add(zeroButton, 1, 3);

        Button clearButton = createButton("C");

        // without lambdas:

//        clearButton.setOnAction(
//            new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    value.set(0);
//                }
//            }
//        );

        // with lambdas:
        clearButton.setOnAction(event -> value.set(0));

        grid.add(clearButton, 2, 3);

        TextField displayField = createDisplayField();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setTop(displayField);
        root.setCenter(grid);

        Scene scene = new Scene(root, 365, 300);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

     private Button createNumberButton(int number) {
        Button button = createButton(Integer.toString(number));
        button.setOnAction(new NumberButtonHandler(number));
        return button ;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        GridPane.setFillHeight(button, true);
        GridPane.setFillWidth(button, true);
        GridPane.setHgrow(button, Priority.ALWAYS);
        GridPane.setVgrow(button, Priority.ALWAYS);
        return button ;
    }


    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        return grid;
    }

    private TextField createDisplayField() {
        TextField displayField = new TextField();
        displayField.textProperty().bind(Bindings.format("%d", value));
        displayField.setEditable(false);
        displayField.setAlignment(Pos.CENTER_RIGHT);
        return displayField;
    }

    public static void main(String[] args) {
        launch(args);
    }
}