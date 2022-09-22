import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Group;

	/**
	 * Creates a game of 2048 for the user to play
	 * Includes a random number generator that generates on open tiles
	 * Includes a custom tile selection prompted to the user when program is run, catches non-int characters, with the minimum tile at 3x3
	 * buttons on the corners shift tiles diagonally towards the selected corner
	 * buttons on edges shift tiles towards that edge
	 * @Elwin
	 */
public class SlidingTiles extends Application {
	
	//stores the array of buttons that represents the screen
	private Button[][] buttons;
	private int[][] values;
		
	//x and y values for starting grid size
	private static int xAxis;
	private static int yAxis;
	
	  /**
	   * Creates the JavaFX application with four buttons
	   * @param primaryStage the main window
	   */
	@Override
	public void start(Stage primaryStage) {
		
		buttons = new Button[xAxis][yAxis]; //button array initialized after user selection
		values = new int[xAxis][yAxis]; //int array that store button metadata
		
		startingArray();
	    Group g1 = new Group();
	    
	    g1.getChildren().add(getGrid());
	    Scene scene = new Scene(g1, 30*xAxis, 25*yAxis);
	    
	    primaryStage.setTitle("2048  xTreme");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
		
		/* Button registry EventHandler for up command**/
	    ClickActionTop handlerTop = new ClickActionTop();
	    for(int i = 1; i<buttons.length-1; i++) {
	    	buttons[i][0].setOnAction(handlerTop);
	    }
		
		/* Button registry EventHandlers left command**/
	    ClickActionLeft handlerLeft = new ClickActionLeft();
	    for(int j = 1; j<buttons[0].length-1; j++) {
	    	buttons[0][j].setOnAction(handlerLeft);
	    }
		
		/* Button EventHandlers right command**/
	    ClickActionRight handlerRight = new ClickActionRight();
	    for(int j = 1; j<buttons[0].length-1; j++) {
	    	buttons[buttons.length-1][j].setOnAction(handlerRight);
	    }
		
		/* Button EventHandlers down command**/
	    ClickActionBottom handlerLow = new ClickActionBottom();
	    for(int i = 1; i<buttons.length-1; i++) {
	    	buttons[i][buttons[0].length-1].setOnAction(handlerLow);
	    }
		
		/** Button EventHandlers upper-left diagonal command**/
		buttons[0][0].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("active up left");
				upLeftShift();
		    }
		});
		
		/** Button EventHandlers upper-right command**/
		buttons[xAxis-1][0].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("active up right");
				upRightShift();
		    }
		});
		
		/** Button EventHandlers lower-right command**/
		buttons[xAxis-1][yAxis-1].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("active down right");
				downRightShift();
		    }
		});
		
		/** Button EventHandlers lower-left command**/
		buttons[0][yAxis-1].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("active down left ");
				downLeftShift();
			}
		});
	}
    
	//grid layout for the buttons
	public Pane getGrid() {
		GridPane grid = new GridPane();
		for(int i = 0; i < buttons.length; i++) {
			for (int j = 0; j< buttons[0].length; j++) {
				grid.add(buttons[i][j], i *(i + (int)buttons.length), j);
	        }
		}
	    return grid;
	}
	
	// method generates a random number between specified range
	public int getRandomNumber (int min, int max) {
		return (int) ((Math.random() * (max-min)) + min);
	}
	
	//first initialized to position the buttons, also includes a random first tile generation
	public void startingArray() {
		int startX = getRandomNumber(0, buttons.length);
		int startY = getRandomNumber(0, buttons[0].length);
        for(int i = 0; i < buttons.length; i++) {
        	for(int j = 0; j < buttons[0].length; j++) { 
        		buttons[i][j] = new Button("    ");
        	}
        }
        //chooses a random button with the starting value
        buttons[startX][startY].setText(" 1 ");
        values[startX][startY] = 1;
	}
	
	//method to generate new array numbers after a shift
	public void generate() {
		int x = getRandomNumber(0, buttons.length);
		int y = getRandomNumber(0, buttons[0].length);
		//loop keeps generating arrays until it finds a empty space.
		while (!buttons[x][y].toString().contains("    ")) {
			x = getRandomNumber(0, buttons.length);
			y = getRandomNumber (0, buttons[0].length);
		}
		buttons[x][y].setText(" 1 ");
		values[x][y] = 1;
	}
	
	//method to perform the down shift
	public void downShift() {
		int storage;
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length - 1; j++) {
				if (values[i][j + 1] == 0 && values[i][j] != 0) {
					values[i][j + 1] = values[i][j];
					buttons[i][j + 1].setText(" " + values[i][j]+ " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				} else if (values[i][j + 1] == values[i][j] && values[i][j] != 0) {
					storage = values[i][j + 1] + values[i][j];
					values[i][j + 1] = values[i][j + 1] + values[i][j];
					buttons[i][j + 1].setText(" " + storage + " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				}
			}
	    }
		generate();
	}
	
	//method to perform the up shift
	public void upShift() {
		int storage;
		for (int i = buttons.length - 1; i >= 0; i--) {
			for (int j = buttons[i].length - 1; j > 0; j--) {
				if (values[i][j - 1] == 0 && values[i][j] != 0) {
					values[i][j - 1] = values[i][j];
					buttons[i][j - 1].setText(" " + values[i][j]+ " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				} else if (values[i][j - 1] == values[i][j] && values[i][j - 1] != 0) {
					storage = values[i][j - 1] + values[i][j];
					values[i][j - 1] = values[i][j - 1] + values[i][j];
					buttons[i][j - 1].setText(" " + storage + " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				}
			}
	    }
		generate();
	}
	
	//method to perform the right shift
	public void rightShift() {
		int storage;
		for (int i = 0; i < buttons.length - 1; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (values[i+1][j] == 0 && values[i][j] != 0) {
					values[i+1][j] = values[i][j];
					buttons[i+1][j].setText(" " + values[i][j]+ " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				} else if (values[i+1][j] == values[i][j] && values[i][j] != 0) {
					storage = values[i+1][j] + values[i][j];
					values[i+1][j] = values[i+1][j] + values[i][j];
					buttons[i+1][j].setText(" " + storage + " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				}
			}
	    }
		generate();
	}
	
	//method to perform the left shift
	public void leftShift() {
		int storage;
		for (int i = buttons.length - 1; i > 0; i--) {
			for (int j = buttons[i].length - 1; j >= 0; j--) {
				if (values[i - 1][j] == 0 && values[i][j] != 0) {
					values[i - 1][j] = values[i][j];
					buttons[i - 1][j].setText(" " + values[i][j]+ " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				} else if (values[i - 1][j] == values[i][j] && values[i - 1][j] != 0) {
					storage = values[i - 1][j] + values[i][j];
					values[i - 1][j] = values[i - 1][j] + values[i][j];
					buttons[i - 1][j].setText(" " + storage + " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				}
			}
	    }
		generate();
	
	}
	
	/**method performs the diagonal down right shift*/
	public void downRightShift() {
		int storage;
		for (int i = 0; i < buttons.length - 1; i++) {
			for (int j = 0; j < buttons[i].length-1; j++) {
				if (values[i+1][j+1] == 0 && values[i][j] != 0) {
					values[i+1][j+1] = values[i][j];
					buttons[i+1][j+1].setText(" " + values[i][j]+ " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				} else if (values[i+1][j+1] == values[i][j] && values[i][j] != 0) {
					storage = values[i+1][j+1] + values[i][j];
					values[i+1][j+1] = values[i+1][j+1] + values[i][j];
					buttons[i+1][j+1].setText(" " + storage + " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				}
			}
	    }
		generate();
	}
	
	/**method performs the diagonal down left shift */
	public void downLeftShift() {
		int storage;
		for (int i = buttons.length - 1; i > 0; i--) {
			for (int j = 0; j < buttons[i].length-1; j++) {
				if (values[i-1][j+1] == 0 && values[i][j] != 0) {
					values[i-1][j+1] = values[i][j];
					buttons[i-1][j+1].setText(" " + values[i][j]+ " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				} else if (values[i-1][j+1] == values[i][j] && values[i][j] != 0) {
					storage = values[i-1][j+1] + values[i][j];
					values[i-1][j+1] = values[i-1][j+1] + values[i][j];
					buttons[i-1][j+1].setText(" " + storage + " ");
					values[i][j] = 0;
					buttons[i][j].setText("    ");
				}
			}
	    }
		generate();
	}
	
	/** method performs the diagonal up left shift */
		public void upLeftShift() {
			int storage;
			for (int i = buttons.length - 1; i > 0; i--) {
				for (int j = buttons[i].length - 1; j > 0; j--) {
					if (values[i - 1][j - 1] == 0 && values[i][j] != 0) {
						values[i - 1][j - 1] = values[i][j];
						buttons[i - 1][j - 1].setText(" " + values[i][j]+ " ");
						values[i][j] = 0;
						buttons[i][j].setText("    ");
					} else if (values[i - 1][j - 1] == values[i][j] && values[i - 1][j - 1] != 0) {
						storage = values[i - 1][j - 1] + values[i][j];
						values[i - 1][j - 1] = values[i - 1][j] + values[i][j];
						buttons[i - 1][j - 1].setText(" " + storage + " ");
						values[i][j] = 0;
						buttons[i][j].setText("    ");
					}
				}
		    }
			generate();
		}
		
		/** performs the diagonal up right shift */
		public void upRightShift() {
			int storage;
			for (int i = 0; i < buttons.length - 1; i++) {
				for (int j = buttons[i].length - 1; j > 0; j--) {
					if (values[i + 1][j - 1] == 0 && values[i][j] != 0) {
						values[i + 1][j - 1] = values[i][j];
						buttons[i + 1][j - 1].setText(" " + values[i][j]+ " ");
						values[i][j] = 0;
						buttons[i][j].setText("    ");
					} else if (values[i + 1][j - 1] == values[i][j] && values[i + 1][j - 1] != 0) {
						storage = values[i + 1][j - 1] + values[i][j];
						values[i + 1][j - 1] = values[i + 1][j - 1] + values[i][j];
						buttons[i + 1][j - 1].setText(" " + storage + " ");
						values[i][j] = 0;
						buttons[i][j].setText("    ");
					}
				}
		    }
			generate();
		}
		
	//Main
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int x = 4;
		int y = 4;
		//try to catch the errors to prevent crash
		try {
			System.out.println("Welcome to Elwin's version of 2048");
			System.out.println("To start, enter your preferred dimensions (x and y separated by a space): ");
			x = scan.nextInt();
			y = scan.nextInt();
		}
		//catches non-int digits, which would then run the default 4x4 game
		catch(InputMismatchException e) {
			System.out.println("Invalid input, default settings selected.");
			scan.close();		
		}
		if(x > 2 && y > 2) {
			xAxis = x;
			yAxis = y;
		}
		else {
			System.out.println("Invalid input: integer must be greater than 3, default settings 4x4 selected.");
			xAxis = 4;
			yAxis = 4;
		}
		Application.launch(args);
		scan.close();

	}
	
	/** A class to handle the top row buttons being clicked*/
	public class ClickActionTop implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			System.out.println("upper levels");
			upShift();
		}
	}
	
	/** A class to handle a button click on the left column*/
	public class ClickActionLeft implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			System.out.println("left levels");
			leftShift();
		}
	}
	
	/** A class to handle button clicks on the right column */
	public class ClickActionRight implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			System.out.println("right levels");
			rightShift();
		}
	}
	
	/** A class to handle button clicks on the bottom row */
	public class ClickActionBottom implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			System.out.println("lower levels");
			downShift();
		}
	}
}
