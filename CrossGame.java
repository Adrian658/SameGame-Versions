import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;
import java.lang.RuntimeException;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.lang.Math;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.text.Font;

/** 
 * Represents the game CrossGame with maximum of six different colored buttons
 * @author Adrian F Guzman.
 */
public class CrossGame extends Application {
  
  /** Height of the game board(number of rows) */
  private int rows = 12;
  
  /** Width of the game board(number of columns) */
  private int columns = 12;
  
  /** Number of different colored tiles in the game */
  private int numberColors = 3;
  
  /** GridPane to graphically present the Buttons */
  private GridPane gridPane = new GridPane();
  
  /** ButtonList to store the buttons in the game */
  private Button[][] buttonList;
  
  /** Stores the row of the current button being checked */
  private int currentRow;
  
  /** Stores the column of the current button being checked */
  private int currentColumn;
  
  /** Stores the current button that was clicked */
  private Button clickedButton;
  
  /** Stores the color of the current button that was clicked */
  private Color clickedColor;
  
  /** Stores the number of buttons above the clicked button */
  private int buttonsAbove;
  
  /** Stores the number of buttons below the clicked button */
  private int buttonsBelow;
  
  /** Stores the number of buttons right of the clicked button */
  private int buttonsRight;
  
  /** Stores the number of buttons left of the clicked button */
  private int buttonsLeft;
  
  /** Keeps track of the players current score */
  private int score = 0;
  
  /**
   * Overrides the start method of javafx to specify what happens when the application is started
   * @param primaryStage the primaryStage to initialize scene with
   * @throws Exception when input type is incorrect
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Cross Game");
    BorderPane pane = new BorderPane(); //to store the grid of buttons and the scoreboard
    handleArgs();
    buttonList = new Button[getRows()][getColumns()]; //assigning buttonList new array with specified dimensions
    /* iterates over all vacancies of buttonList, adds a new Button with a random color to each vacancy, 
     * , creates the eventHandler for the button, and sets the Button at index [i][j] to its respective index in the GridPane */
    for (int i = 0; i < getButtonList().length; i++) {
      for (int j = 0; j < getButtonList()[i].length; j++) {
        buttonList[i][j] = createButtonVisuals(new Button()); //assigning new button with viuals to the gridpane
        final Button button = buttonList[i][j]; //Declaring current button being analyzed
        getGridPane().add(button, j, i);
        /* Specifying what to do when the button is clicked according to the game rules */
        button.setOnAction(new EventHandler<ActionEvent>() {
          /* Overriding start method */
          public void handle(ActionEvent e) {
            Button b = (Button)e.getSource(); //storing the button that was clicked
            clickedButton = b;
            clickedColor = (Color)(((Shape)(b.getGraphic())).getFill()); //storing color of button clicked
            removeButtons(); //removing buttons
            shiftColumns(); //shifting columns
            score = updateScore(); //updating score
            resetVariables();
            pane.setTop(new Button("Your score is: " + updateScore())); //updating scoreboard
            //if the game has been won, display the final screen
            if (checkWon()) {
              Text text = new Text(10, 40, "YOU WON! Your Score was: " + score);
              text.setFont(new Font(30));
              Scene scene = new Scene(new Group(text));
              primaryStage.setTitle("Cross Game");
              primaryStage.setScene(scene);
              primaryStage.sizeToScene();
              primaryStage.show();
            }
          }
        });
      }
    }
    pane.setCenter(gridPane);
    pane.setTop(new Button("Your score is: 0"));
    Scene scene = new Scene(pane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  /**
   * Sets variables back to initial state after completing a move
   */
  public void resetVariables() {
    buttonsAbove = 0;
    buttonsBelow = 0;
    buttonsRight = 0;
    buttonsLeft = 0;
  }
  
  /**
   * Updates the score depending on how many buttons were removed at once
   * @return score the players score current score
   */
  public int updateScore() {
    double totalButtons = getButtonsBelow() + getButtonsAbove() + getButtonsRight() + getButtonsLeft(); //stores total number of buttons removed
    /* makes sure the score is not disproprotionately affected when totalButtons exceeds 5 due to exponential function */
    if (totalButtons > 5)
      totalButtons = 5.0 + (totalButtons * 0.1);
    /* if only two contiguous buttons, add one to the score */
    if (totalButtons == 1)
      this.score = this.score + 1;
    /* else add score exponentially proportionate to the amount of buttons removed */
    else
      this.score = this.score + (int)((Math.exp(totalButtons)) / 2);
    return score;
  }
  
  /**
   * Assigns a color from an array of possible colors to a button
   * @param b the button to be assigned a graphic
   */
  public Button createButtonVisuals(Button b) {
    /* if user enters number of colors greater than the amount of colors in the color array, sets numberColors to max amount of colors*/
    if (numberColors > 6)
      numberColors = 6;
    Color[] colorArray = new Color[6];
    colorArray[0] = Color.FIREBRICK;
    colorArray[1] = Color.DODGERBLUE;
    colorArray[2] = Color.FUCHSIA;
    colorArray[3] = Color.LAWNGREEN;
    colorArray[4] = Color.ORANGE;
    colorArray[5] = Color.YELLOW;
    Circle circle = new Circle(10); //new shape to fill with a random color
    circle.setFill(colorArray[(int)(Math.random() * getNumberColors())]);
    b.setGraphic(circle);
    return b;
  }
  
  /**
   * Finds the indices of the Button that was clicked and stores values in respective fields
   * @param b Button that was clicked
   */
  public void findButtonIndices(Button b) {
    //loops over array of buttons to find the button that was clicked and then stores the row and column of that button
    for (int h = 0; h < getRows(); h++) {
      for (int k = 0; k < getColumns(); k++) {
        //if the clicked button is equals to the button at the current indices h and k
        if (buttonList[h][k].equals(b)) {
          setCurrentRow(h);
          setCurrentColumn(k);
        }
      }
    }

  }
  
  /** 
   * Counts the number of contiguous buttons above the clicked button and stores these values in their respective fields
   */
  public void buttonsAbove() {
    int compareRow = (getCurrentRow() - 1); //variable to store the row of the button we want to compare the clicked button to
    Color changeToClickedColor; // variable to store the color of the button we want to compare the clicked button to
    /* if the row of the initial button we are comparing is a valid index in the specified array */
    if (compareRow >= 0) {
      changeToClickedColor = (Color)(((Shape)(buttonList[compareRow][getCurrentColumn()].getGraphic())).getFill());
      /* Counts the number of contiguous buttons above the button clicked and stores in buttonsAbove */
      while((compareRow >= 0) && (changeToClickedColor.equals(clickedColor))){
        buttonsAbove++;
        compareRow--;
        /* if the row of the next button we want to compare is a valid index in the specified array */
        if (compareRow != -1)
          changeToClickedColor = (Color)(((Shape)(buttonList[compareRow][getCurrentColumn()].getGraphic())).getFill());
      }
    }
  }
  
  /** 
   * Counts the number of contiguous buttons below the clicked button and stores these values in their respective fields
   */
  public void buttonsBelow() {
    int compareRow = (getCurrentRow() + 1); //variable to store the row of the button we want to compare the clicked button to
    Color changeToClickedColor; // variable to store the color of the button we want to compare the clicked button to
    /* if the row of the initial button we are comparing is a valid index in the specified array */
    if (compareRow < getRows()) {
      changeToClickedColor = (Color)(((Shape)(buttonList[compareRow][getCurrentColumn()].getGraphic())).getFill());
      /* Counts the number of contiguous buttons below the button clicked and stores in buttonsBelow */ 
      while((compareRow < getRows()) && (changeToClickedColor.equals(clickedColor))) {  
        buttonsBelow++;
        compareRow++;
        /* if the row of the next button we want to compare is a valid index in the specified array */
        if (compareRow != getRows())
          changeToClickedColor = (Color)(((Shape)(buttonList[compareRow][getCurrentColumn()].getGraphic())).getFill());
      }
    }
  }
  
  /**
   * Counts the number of contiguous buttons to the right of the button clicked and stores these values in their respective fields
   */
  public void buttonsRight() {
    int compareColumn = (getCurrentColumn() + 1); //variable to store the column of the button we want to compare the clicked button to
    Color changeToClickedColor; // variable to store the color of the button we want to compare the clicked button to
    /* if the column of the initial button we are comparing is a valid index in the specified array */
    if (compareColumn < getColumns()) {
      changeToClickedColor = (Color)(((Shape)(buttonList[getCurrentRow()][compareColumn].getGraphic())).getFill());
      /* Counts the number of contiguous buttons to the right of the button clicked and stores in buttonsRight */
      while ((compareColumn < getColumns()) && (changeToClickedColor.equals(clickedColor))){
        buttonsRight++;
        compareColumn++;
        /* if the column of the next button we want to compare is a valid index in the specified array */
        if (compareColumn != getColumns())
          changeToClickedColor = (Color)(((Shape)(buttonList[getCurrentRow()][compareColumn].getGraphic())).getFill());
      }
    }
  }
  
  /**
   * Counts the number of contiguous buttons to the left of the button clicked and stores these values in their respective fields
   */
  public void buttonsLeft() {
    int compareColumn = (getCurrentColumn() - 1); //variable to store the column of the button we want to compare the clicked button to
    Color changeToClickedColor; // variable to store the color of the button we want to compare the clicked button to
    /* if the column of the initial button we are comparing is a valid index in the specified array */
    if (compareColumn >= 0) {
      changeToClickedColor = (Color)(((Shape)(buttonList[getCurrentRow()][compareColumn].getGraphic())).getFill());
      /* Counts the number of contiguous buttons to the left of the button clicked and stores in buttonsLeft */
      while((compareColumn >= 0) && (changeToClickedColor.equals(clickedColor))){
        buttonsLeft++;
        compareColumn--;
        /* if the column of the next button we want to compare is a valid index in the specified array */
        if (compareColumn != -1)
          changeToClickedColor = (Color)(((Shape)(buttonList[getCurrentRow()][compareColumn].getGraphic())).getFill());
      }
    }
  }
  
  /**
   * Method that calls above, below, right, and left button counting methods
   */
  public void countButtons() {
    buttonsAbove();
    buttonsBelow();
    buttonsLeft();
    buttonsRight();
  }
  
  /**
   * Removes one contiguous button and drops all buttons above it down one
   * @param row row of Button to drop all buttons above
   * @param column columnc of Button to drop all buttons above
   * @param buttonsBelow number of contiguous buttons below the clicked button
   */
  public void dropSingleColumn(int row, int column, int buttonsBelow) {
    int lowestContigRow = row + buttonsBelow; //row of the lowest contiguous button
    int changeToGraphic = lowestContigRow - 1; //row of the button to change the color of the current button to
    int i = 0;
    /* while the changeToGraphic is a valid index in the board, set the current button color to the button color at changeToGraphic */
    while (changeToGraphic - i >= 0) {
      buttonList[lowestContigRow - i][column].setGraphic(buttonList[changeToGraphic - i][column].getGraphic());
      i = i + 1;
    }
    /* if the button to be checked is the top button, set its color to gray */
    if (changeToGraphic - i < 0) {
      Circle circle = new Circle(10);
      circle.setFill(Color.LIGHTGRAY);
      buttonList[0][column].setGraphic(circle);
    }
  }
  
  /**
   * Removes all contiguous buttons in a column and drops all buttons above removed buttons down
   * by calling dropSingleColumn with the current row and column of the button clicked
   */
  public void removeVerticalContigButtons() {
    /* stores the amount of contiguous buttons of the same color as the clicked buttons color in the same column */
    int verticalChange = buttonsAbove + buttonsBelow + 1;  
    /* if there are contiguous buttons with clicked button in same column */
    if (verticalChange > 1) {
      /* drops a column the same amount of times as there are buttons to remove from the clicked buttons column */
      for (int i = 0; i < verticalChange; i++) {
        this.dropSingleColumn(getCurrentRow(), getCurrentColumn(), getButtonsBelow());
      }
    }
  }
  
  /**
   * Removes all contiguous buttons in a row (except for in the column the clicked button is in because
   * this button was already removed by removeVerticalContigButtons) and drops all buttons above removed buttons down
   * by calling dropSingleColumn
   */
  public void removeHorizontalContigButtons() {
    /* stores the amount of contiguous buttons of the same color as the clicked buttons color in the same row */
    int horizontalChange = buttonsRight + buttonsLeft + 1;
    int leftMostButtonColumn = getCurrentColumn() - buttonsLeft; // stores the column of the leftmost contiguous button with the clicked button
    /* if there are contiguous buttons with the clicked button in same row */
    if (horizontalChange > 1) {
      /* removes each contiguous button on either side of the clicked button's row */
      for (int i = 0; i < horizontalChange; i++) {
        /* ensures that the button to be removed is not the clicked button
         * because removeVerticalContigButtons already removes the clicked button */
        if (leftMostButtonColumn + i != getCurrentColumn()) {
          /* passes in zero because when dropping columns left and right of the clicked button, don't want to take into account 
           * buttons below clicked button */
          this.dropSingleColumn(getCurrentRow(), leftMostButtonColumn + i, 0);
        }
        else {}
      }
    }
    /* stores the amount of contiguous buttons of the same color as the clicked buttons color in the same column */
    int verticalChange = buttonsAbove + buttonsBelow + 1;
    /* handles the case if there are no contiguous buttons above or below the clicked button, where removeVerticalContigButtons would not be called
     * so the clicked button would not be removed, and there are contiguous buttons on the left or right of the clicked button */
    if (verticalChange <= 1 && horizontalChange > 1)
      this.dropSingleColumn(getCurrentRow(), getCurrentColumn(), getButtonsBelow());
  }
  
  /**
   * Uses various helper methods to remove contiguous buttons with the clicked button according to the game rules
   */
  public void removeButtons() {
    /* ensures that if the user clicks on an empty button, nothing is done */
    if (clickedColor != Color.LIGHTGRAY) {
      findButtonIndices(clickedButton);
      countButtons();
      removeVerticalContigButtons();
      removeHorizontalContigButtons();
    }
  }
  
  /**
   * Checks for empty columns and shifts columns to the right of the empty column to the left
   */
  public void shiftColumns() {
    int i = 0; //keeps track of current column to check
    /* loops through each column of the board */
    while (i < getColumns() - 1) {
      /* if the column being checked is empty */
      if (columnEmpty(i)) {
        int j = 0; // keeps track of rows remaining after the current row
        /* starts at current column and loops through the remaining columns except the last and removes each one */
        while (i + j < getColumns() - 1) {
          removeSingleColumn(i + j);
          j++;
        }
        setColumnEmpty(getColumns() - 1); //sets the last column empty after removing a column
        /* if the all columns to the right of the column being checked are empty, iterates i to check the next column */
        if (columnsRight(i)) {
          i++;
        }
      }
      /* condition to ensure that more than two columns can be removed at the same time */
      else 
        i++;
    }
  }
  
  /**
   * Checks if the rest of the columns to the right of the specified column are empty
   * @param column column to check
   * @return columnsRight if all columns right of the specified column are empty
   */
  public boolean columnsRight(int column) {
    boolean columnsRight = true; //stores if all the columns right of the specified column are empty
    /* loops over columns to the right of the specified columns */
    for (int i = column + 1; i < getColumns(); i++) {
      /* if the column is not empty sets boolean to false */
      if (!columnEmpty(i))
        columnsRight = false;
    }
    return columnsRight;
  }
  
  /**
   * For each button in the specified column, copies the color of the button in the column right of it
   * @param columnRemove the column number to remove
   */
  public void removeSingleColumn(int columnRemove) {
    /* loops through each row of the column and sets the button at that row's color to
     * the color of the button to the right of it*/
    for (int i = 0; i < getRows(); i++)
      ((Shape)(buttonList[i][columnRemove].getGraphic())).setFill(((Color)(((Shape)(buttonList[i][columnRemove + 1].getGraphic())).getFill())));
  }
  
  /**
   * Checks if the specified column is empty
   * @param column column to check if empty
   * @return emptyColumn boolean specifying if the column is empty
   */
  public boolean columnEmpty(int column) {
    boolean emptyColumn = true; //boolean to store if the column is empty
    /* loops through each row of the column */
    for (int i = 0; i < getRows(); i++) {
      /* if the buttons color is not grey, means the column is not empty so sets column empty to false
       * else checks the next row */
      if ( !(((Color)(((Shape)(buttonList[i][column].getGraphic())).getFill())).equals(Color.LIGHTGRAY)) )
        emptyColumn = false;
    }
    return emptyColumn;
  }
  
  /**
   * Sets a column to grey cirles indicating it is empty
   * @param column column to set as empty
   */
  public void setColumnEmpty(int column) {
    /* loops through each row of the column */
    for (int i = 0; i < getRows(); i++)
      /* sets the buttons color to grey */
      ((Shape)(buttonList[i][column].getGraphic())).setFill(Color.LIGHTGRAY);
  }
  
  /**
   * Checks if the game is won (all buttons are grey)
   * @return won specifies if the game is won
   */
  public boolean checkWon() {
    boolean won = true;
    /* loops through the array anc checks if all buttons are gray and if they are means the game is won */
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {
        if ( !((Color)(((Shape)buttonList[i][j].getGraphic()).getFill())).equals(Color.LIGHTGRAY))
          won = false;
      }
    }
    return won;
  }
  
  /**
   * Takes the users input or lack of input and sets the dimension/number of colors in the game to the input or default values
   * @throws Exception when input type is incorrect
   */
  public void handleArgs() throws Exception {
    /* handles the command line arguments entered by the user */
    try {
      /* if the user eneters values for height, width, and number of colors, sets the corresponding field values */
      if (this.getParameters().getRaw().size() == 3) {
        setRows(Integer.parseInt(this.getParameters().getRaw().get(0)));
        setColumns(Integer.parseInt(this.getParameters().getRaw().get(1)));
        setNumberColors(Integer.parseInt(this.getParameters().getRaw().get(2)));
      }
      /* if the user enters a value for number of colors and value n for a nxn dimension grid, sets the corresponding field values */
      else if (this.getParameters().getRaw().size() == 2) {
        setRows(Integer.parseInt(this.getParameters().getRaw().get(0)));
        setColumns(Integer.parseInt(this.getParameters().getRaw().get(0)));
        setNumberColors(Integer.parseInt(this.getParameters().getRaw().get(1)));
      }
    }
    /* if the user enters invalid input, creates a board with standard value */
    catch (RuntimeException e) {
      System.out.println("You did not specify dimension or number colors so the program will execute using a 12x12 board with 3 colors");
      this.setRows(12);
      this.setColumns(12);
      this.setNumberColors(3);
    }
  }
  
  /**
   * Returns the number of rows of the board
   * @return rows the number of rows of the board
   */
  public int getRows() {
    return rows;
  }
  
  /**
   * Sets the number of rows of the board
   * @param rows number of rows
   */
  public void setRows(int rows) {
    this.rows = rows;
  }
  
  /**
   * Returns the number of columns of the board
   * @return columns the number of columns of the board
   */
  public int getColumns() {
    return columns;
  }
  
  /**
   * Sets the number of columns of the board
   * @param columns number of columns
   */
  public void setColumns(int columns) {
    this.columns = columns;
  }
  
  /**
   * Returns the number of different button colors on the board
   * @return numberColors the number of different button colors on the board
   */
  public int getNumberColors() {
    return numberColors;
  }
  
  /**
   * Sets the number of colors on the board
   * @param colors number of colors
   */
  public void setNumberColors(int colors) {
    this.numberColors = colors;
  }
  
  /**
   * Returns the gridPane displaying buttons
   * @return gridPane the girdPane displaying buttons
   */
  public GridPane getGridPane() {
    return gridPane;
  }
  
  /**
   * Sets gridPane to the specified gridPane
   * @param gridPane the gridPane to change to
   */
  public void setGridPane(GridPane gridPane) {
    this.gridPane = gridPane;
  }
  
  /**
   * Returns the buttonList array containing buttons
   * @return buttonList the array containing the buttons on the board
   */
  public Button[][] getButtonList() {
    return buttonList;
  }
  
  /**
   * Sets the buttonList to the specified list of buttons
   * @param buttonList the list of buttons
   */
  public void setButtonList(Button[][] buttonList) {
    this.buttonList = buttonList;
  }
  
  /**
   * Returns the row of the current button being checked
   * @return currentRow row of current button
   */
  public int getCurrentRow() {
    return currentRow;
  }
  
  /**
   * Returns the column of the current button being checked
   * @return currentColumn column of current button
   */
  public int getCurrentColumn() {
    return currentColumn;
  }
  
   /**
    * Changes the value of currentRow field
    * @param row current row of button
    */
  public void setCurrentRow(int row) {
    this.currentRow = row;
  }
  
  /**
   * Changes the value of currentColumn field
   * @param column current column of button
   */
  public void setCurrentColumn(int column) {
    this.currentColumn = column;
  }
  
  /**
   * Returns the buttons above the current button
   * @return buttonsAbove buttons above the current button
   */
  public int getButtonsAbove() {
    return buttonsAbove;
  }
  
  /**
   * Returns the buttons below the current button
   * @return buttonsBelow buttons below the current button
   */
  public int getButtonsBelow() {
    return buttonsBelow;
  }
  
  /**
   * Returns the buttons right of the current button
   * @return buttonsRight buttons right of the current button
   */
  public int getButtonsRight() {
    return buttonsRight;
  }
  
  /**
   * Returns the buttons left of the current button
   * @return buttonsAbove buttons left of the current button
   */
  public int getButtonsLeft() {
    return buttonsLeft;
  }
  
  /**
   * Returns the buttons above the current button
   * @return buttonsAbove buttons above the current button
   */
  public void setButtonsAbove(int above) {
    this.buttonsAbove = above;
  }
  
  /**
   * Returns the buttons above the current button
   * @return buttonsAbove buttons above the current button
   */
  public void setButtonsBelow(int below) {
    this.buttonsBelow = below;
  }
  
  /**
   * Returns the buttons above the current button
   * @return buttonsAbove buttons above the current button
   */
  public void setButtonsRight(int right) {
    this.buttonsRight = right;
  }
  
  /**
   * Returns the buttons above the current button
   * @return buttonsAbove buttons above the current button
   */
  public void setButtonsLeft(int left) {
    this.buttonsLeft = left;
  }
  
  /**
   * Returns the clickedColor color
   * @return clickedColor color of the clicked button
   */
  public Color getClickedColor() {
    return clickedColor;
  }
  
  /**
   * Sets the clickedColor variable
   * @param color the color to se clickedColor to
   */
  public void setClickedColor(Color color) {
    this.clickedColor = color;
  }
    
  /**
   * Main method to launch the program
   * @param args dimensions and number of colors on the board
   */
  public static void main(String[] args) {
    /* Create thread so Application will start when main method is called */
    Thread thread = new Thread() {
      public void run() {
        launchApp(args);
      }
    };
    thread.start();
  }
  
  /**
   * Method to launch javafx Application
   * @param args dimensions and number of colors on the board
   */
  public static void launchApp(String[] args) {
    Application.launch(args);
  }
  
}