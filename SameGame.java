import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;
import javafx.scene.control.TextArea;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.lang.RuntimeException;
import java.lang.Math;
import javafx.scene.control.Label;

/** 
 * Represents the game CrossGame with maximum of six different colored buttons
 * @author Adrian F Guzman.
 */
public class SameGame extends Application {
  
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
  
  /** Two dimensional boolean list to indicate if this index has been visited in buttonList */
  private boolean[][] boolList;
  
  /** Stores the row of the current button being checked */
  private int currentRow;
  
  /** Stores the column of the current button being checked */
  private int currentColumn;
  
  /** Stores the current button that was clicked */
  private Button clickedButton;
  
  /** Stores the color of the current button that was clicked */
  private Color clickedColor;
  
  /** Stores the number of buttons removed at once by clicking a single button*/
  private int buttonsRemovedAtOnce = 0;
  
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
    handleArgs(); //handle command line arguments
    buttonList = new Button[getRows()][getColumns()]; //assigning buttonList new array with specified dimensions
    boolList = new boolean[getRows()][getColumns()]; //assignin booleanList new array with same dimensions as buttonList
    /* iterates over all vacancies of buttonList, adds a new Button with a random color to each vacancy, 
     * creates the eventHandler for the button, and sets the Button at index [i][j] to its respective index in the GridPane */
    for (int i = 0; i < getButtonList().length; i++) {
      for (int j = 0; j < getButtonList()[i].length; j++) {
        buttonList[i][j] = createButtonVisuals(new Button()); //assigning new button with visuals to the buttonList
        boolList[i][j] = false; //iniitializing each value in booleanList as false
        final Button button = buttonList[i][j]; //Declaring current button being analyzed
        getGridPane().add(button, j, i); //assigning current button to the GridPane
        /* Specifying what to do when the button is clicked according to the game rules */
        button.setOnAction(new EventHandler<ActionEvent>() {
          /* Overriding handle method */
          public void handle(ActionEvent e) {
            Button b = (Button)e.getSource(); //storing the button that was clicked
            clickedButton = b;
            clickedColor = (Color)(((Shape)(b.getGraphic())).getFill()); //storing color of button clicked
            playSameGame(pane, primaryStage); //play SameGame 
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
   * Creates and displays a screen informing the user they have completed the game and what their score was
   * @param primaryStage primaryStage created by Application
   */
  public void setWon(Stage primaryStage) {
    Text text = new Text(10, 40, "YOU WON! Your Score was: " + score);
    text.setFont(new Font(30));
    Scene scene = new Scene(new Group(text));
    primaryStage.setTitle("Cross Game");
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }
  
  /**
   * Resets specified variables to their initial values
   */
  public void resetVariables() {
    for (int i = 0; i < getButtonList().length; i++) {
      for (int j = 0; j < getButtonList()[i].length; j++) {
        getBoolList()[i][j] = false;
      }
    }
    setButtonsRemovedAtOnce(0);
  }
  
  /**
   * Updates the score depending on how many buttons were removed at once
   * @return score the players current score
   */
  public void updateScore(BorderPane pane) {
    /* if only two contiguous buttons, add one to the score */
    if (getButtonsRemovedAtOnce() == 1)
      addScore(1);
    /* else add score exponentially proportionate to the amount of buttons removed */
    else
      addScore((int)(Math.pow(3, getButtonsRemovedAtOnce()) / Math.pow(2, getButtonsRemovedAtOnce())));
    pane.setTop(new Button("Your score is: " + getScore())); //updating scoreboard
  }
  
  /**
   * Assigns a color from an array of possible colors to a button
   * @param b the button to be assigned a graphic
   */
  public Button createButtonVisuals(Button b) {
    /* if user enters number of colors greater than the amount of colors in the color array, sets numberColors to max amount of colors*/
    if (getNumberColors() > 6)
      setNumberColors(6);
    Color[] colorArray = new Color[6];
    colorArray[0] = Color.RED;
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
   * Returns whether there is a button of the same color below the button at the specified row and column
   * @param row row of the button specified button
   * @param column column of the specified button
   * @return boolean if there is a button of the same color below the button at the specified row and column
   */
  public boolean contigBelow(int row, int column) {
    int rowToCompare = row + 1;
    /* if button is not at the bottom most row and the colors of the buttons match */
    if (rowToCompare < getRows() && ((Color)(((Shape)(buttonList[rowToCompare][column].getGraphic())).getFill())).equals(getClickedColor()))
      return true;
    else
      return false;
  }
  
  /**
   * Returns whether there is a button of the same color left of the button at the specified row and column
   * @param row row of the button specified button
   * @param column column of the specified button
   * @return boolean if there is a button of the same color left of the button at the specified row and column
   */
  public boolean contigLeft(int row, int column) {
    int columnToCompare = column - 1;
    /* if button is not at the left most column and the colors of the buttons match */
    if (columnToCompare >= 0 && ((Color)(((Shape)(buttonList[row][columnToCompare].getGraphic())).getFill())).equals(getClickedColor()))
      return true;
    else
      return false;
  }
  
  /**
   * Returns whether there is a button of the same color above the button at the specified row and column
   * @param row row of the button specified button
   * @param column column of the specified button
   * @return boolean if there is a button of the same color above the button at the specified row and column
   */
  public boolean contigAbove(int row, int column) {
    int rowToCompare = row - 1;
    /* if button is not at the top most row and the colors of the buttons match */
    if (rowToCompare >= 0 && ((Color)(((Shape)(buttonList[rowToCompare][column].getGraphic())).getFill())).equals(getClickedColor()))
      return true;
    else
      return false;
  }
  
  /**
   * Returns whether there is a button of the same color right of the button at the specified row and column
   * @param row row of the button specified button
   * @param column column of the specified button
   * @return boolean if there is a button of the same color right of the button at the specified row and column
   */
  public boolean contigRight(int row, int column) {
    int columnToCompare = column + 1;
    /* if button is not at the right most column and the colors of the buttons match */
    if (columnToCompare < getColumns() && ((Color)(((Shape)(buttonList[row][columnToCompare].getGraphic())).getFill())).equals(getClickedColor()))
      return true;
    else
      return false;
  }
  
  /*
   * Finds the grouped contiguous buttons with the same color as the clicked button and changes their colors to gray
   * Using recursion
   * @param row row of button being checked
   * @param column column of button being checked
   */
  public void findRemovable(int row, int column) {
    /* if there is a button of the same color below and that button has not been checked already */
    if (contigBelow(row, column) && boolListValue(row + 1, column) == false) {
      getBoolList()[row][column] = true;
      findRemovable(row + 1, column);
    }
    /* if there is a button of the same color to the left and that button has not been checked already */
    if (contigLeft(row, column) && boolListValue(row, column - 1) == false) {
      getBoolList()[row][column] = true;
      findRemovable(row, column - 1);
    }
    /* if there is a button of the same color above and that button has not been checked already */
    if (contigAbove(row, column) && boolListValue(row - 1, column) == false) {
      getBoolList()[row][column] = true;
      findRemovable(row - 1, column);
    }
    /* if there is a button of the same color to the right and that button has not been checked already */
    if (contigRight(row, column) && boolListValue(row, column + 1) == false) {
      getBoolList()[row][column] = true;
      findRemovable(row, column + 1);
    }
    /* changes the color of the button specified to gray, indicating it is empty */
    ((Shape)(buttonList[row][column].getGraphic())).setFill(Color.LIGHTGRAY);
    changeButtonsRemovedAtOnce();
  }
  
  /**
   * Returns the value of the boolean at the specified row and column in boolList
   * @param row row of boolList to return
   * @param column column of boolean to return
   * @return boolean if the indice has been visited by findRemovable recursive method
   */
  public boolean boolListValue(int row, int column) {
    if (row >= 0 && row < getRows()) { //if the row is a valid row in the list
      if (column >= 0 && column < getColumns()) //if the column is a valid column in the list
        return getBoolList()[row][column];
      return true;
    }
    else
      return true;
  }
  
  /**
   * Iterates through the board, removes empty buttons, and shifts any non-empty buttons above them down
   */
  public void removeButtons() {
    /* Iterates through all rows and columns of the board */
    for (int rowChecking = getRows() - 1; rowChecking >= 0; rowChecking--) {
      for (int columnChecking = 0; columnChecking < getColumns(); columnChecking++) {
        /* If the button being analyzed is empty drop buttons of that column */
        if ((((Shape)(buttonList[rowChecking][columnChecking].getGraphic())).getFill()).equals(Color.LIGHTGRAY)) {
          dropColumn(rowChecking, columnChecking, findEmptyContigVertical(rowChecking, columnChecking));
        }
      }
    }
  }
  
  /**
   * Finds the number of contiguous empty buttons above the specified empty button
   * @param row row of the empty button
   * @param column column of the empty button
   * @return contigVertical number of contiguous empty buttons above the specified empty button
   */
  public int findEmptyContigVertical(int row, int column) {
    int contigVertical = 1; //stores contiguous empty buttons above specified empty button
    int rowChecking = row - 1; //stores row of button to check if empty
    /* loops through rows above specified row until a non-empty button or the top of the grid is reached */
    while (rowChecking >= 0 && ((((Shape)(buttonList[rowChecking][column].getGraphic())).getFill()).equals(Color.LIGHTGRAY))) {
      contigVertical++;
      rowChecking--;
    }
    return contigVertical;
  }
  
  /**
   * Drops down non-empty buttons that are above empty buttons, if any
   * @param row row of empty button to drop non-empty buttons that are above 
   * @param column column of empty button to drop non-empty buttons that are above
   * @param contigEmpty number of contiguous empty buttons above the specified smpety button
   */
  public void dropColumn(int row, int column, int contigEmpty) {
    /* copies the color of the non-empty button above specified button corresponding to amount of contiguous empty buttons */
    while (row - contigEmpty >= 0) {
      ((Shape)(buttonList[row][column].getGraphic())).setFill(((Shape)(buttonList[row - contigEmpty][column].getGraphic())).getFill());
      row--;
    }
    /* Empties the buttons at the top of the column that were copied */
    for (int i = 0; i < contigEmpty; i++) {
      ((Shape)(buttonList[i][column].getGraphic())).setFill(Color.LIGHTGRAY);
    }
  }
  
  /**
   * Calls the various helper methods to play the SameGame
   * @param pane pane holding the gridPane and score board
   * @param primaryStage primaryStage specified by Application
   */
  public void playSameGame(BorderPane pane,Stage primaryStage) {
    findButtonIndices(getClickedButton());
    if (anyContiguous()) { //ensures that nothing is done if a button is clicked with no contiguous buttons around it
      findRemovable(getCurrentRow(), getCurrentColumn());
      removeButtons();
      shiftColumns();
      updateScore(pane);
      resetVariables();
      if (checkWon()) { //if the game has been won, display the final screen
        setWon(primaryStage);
      }
    }
  }
  
  /**
   * Finds if the button clicked has any contiguous buttons around it
   */
  public boolean anyContiguous() {
    if (!contigAbove(getCurrentRow(), getCurrentColumn()) && !contigLeft(getCurrentRow(), getCurrentColumn()) 
          && !contigBelow(getCurrentRow(), getCurrentColumn()) && !contigRight(getCurrentRow(), getCurrentColumn()))
      return false;
    else
      return true;
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
   * Main method to launch the program
   * @param args dimensions and number of colors on the board
   */
  public static void main(String[] args) {
    Application.launch(args);
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
   * Returns the button that was clicked
   * @return clickedButton the button that was clicked
   */
  public Button getClickedButton() {
    return clickedButton;
  }
  
  /**
   * Returns the boolean list
   * @return booList the list of booleans
   */
  public boolean[][] getBoolList() {
    return boolList;
  }
  
  /**
   * Returns number of buttons removed by one clicked button
   * @return buttonsRemovedAtOnce number of buttons removed by one clicked button
   */
  public int getButtonsRemovedAtOnce() {
    return this.buttonsRemovedAtOnce;
  }
  
  /**
   * Increases the field buttonsRemovedAtOnce by one
   */
  public void changeButtonsRemovedAtOnce() {
    buttonsRemovedAtOnce++;
  }
  
  /**
   * Sets the buttons removed at once
   * @param buttons buttons removed at once
   */
  public void setButtonsRemovedAtOnce(int buttons) {
    this.buttonsRemovedAtOnce = buttons;
  }
  
  /**
   * Returns the players current score
   * @return score the players current score
   */
  public int getScore() {
    return this.score;
  }
  
  /**
   * Adds the specified score to the players current score
   * @param score the score to add to the players current score
   */
  public void addScore(int score) {
    this.score += score;
  }
  
}