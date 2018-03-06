import org.junit.*;
import static org.junit.Assert.*;
import java.lang.Thread;
import java.lang.String;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import java.lang.Math;

/* IMPORTANT: Sometimes when testing the JUnit, for some reason the main method does not call properly and an initializer error occurs.
 * If this happens, clicking reset or waiting a few minutes and trying again should work. Just want to make sure that you
 * know my tests run and are correct. Thanks. */

/**
 * Tests all testable methods in CrossGame class
 * @author Adrian F Guzman
 */
public class CrossGameTester {
  
  /**
   * Tests the method findButtonIndices
   */
  @Test
  public void testFindButtonIndices() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    
    /* test for first, middle, last ROW index */
    Button[][] buttonList = new Button[3][1];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[2][0] = new Button();
    game.setButtonList(buttonList);
    game.setRows(3);
    game.setColumns(1);
    game.findButtonIndices(buttonList[0][0]);
    assertEquals("Current row of specified button is 0", 0, game.getCurrentRow());
    assertEquals("Current column of button specified is 0", 0, game.getCurrentColumn());
    game.findButtonIndices(buttonList[1][0]);
    assertEquals("Current row of specified button is 1", 1, game.getCurrentRow());
    assertEquals("Current column of button specified is 0", 0, game.getCurrentColumn());
    game.findButtonIndices(buttonList[2][0]);
    assertEquals("Current row of specified button is 2", 2, game.getCurrentRow());
    assertEquals("Current column of button specified is 0", 0, game.getCurrentColumn());
    
    /* test for first, middle, last COLUMN index */
    Button[][] buttonList1 = new Button[1][3];
    buttonList1[0][0] = new Button();
    buttonList1[0][1] = new Button();
    buttonList1[0][2] = new Button();
    game.setButtonList(buttonList1);
    game.setRows(1);
    game.setColumns(3);
    game.findButtonIndices(buttonList1[0][0]);
    assertEquals("Current row of specified button is 0", 0, game.getCurrentRow());
    assertEquals("Current column of button specified is 0", 0, game.getCurrentColumn());
    game.findButtonIndices(buttonList1[0][1]);
    assertEquals("Current row of specified button is 0", 0, game.getCurrentRow());
    assertEquals("Current column of button specified is 1", 1, game.getCurrentColumn());
    game.findButtonIndices(buttonList1[0][2]);
    assertEquals("Current row of specified button is 0", 0, game.getCurrentRow());
    assertEquals("Current column of button specified is 2", 2, game.getCurrentColumn());
  }
  
  /**
   * Tests the method resetVariables
   */
  @Test
  public void testResetVariables() {
    
    CrossGame game = new CrossGame();
    game.setButtonsAbove(5);
    game.setButtonsBelow(6);
    game.setButtonsRight(7);
    game.setButtonsLeft(8);
    game.resetVariables();
    assertEquals("All variables should be 0", 0, game.getButtonsAbove());
    assertEquals("All variables should be 0", 0, game.getButtonsBelow());
    assertEquals("All variables should be 0", 0, game.getButtonsRight());
    assertEquals("All variables should be 0", 0, game.getButtonsLeft());
  }
  
  /** 
   * Tests the updateScore method 
   */
  @Test
  public void testUpdateScore() {
    CrossGame game = new CrossGame();
    game.setButtonsBelow(1);
    assertEquals("Test for totalButtons = 1, returns score of 1", 1, game.updateScore());
    game.setButtonsRight(2);
    assertEquals("Test for totalButtons != 1, returns score of 11", 11, game.updateScore());
    game.setButtonsLeft(7);
    assertEquals("Test for totalButtons > 5, changes totalButtons to 6 and returns score of 212", 212, game.updateScore()); 
  }
  
  /**
   * Tests the method buttonsAbove
   */
  @Test
  public void testButtonsAbove() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and initializing bounds */
    Button[][] buttonList = new Button[4][1];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[2][0] = new Button();
    buttonList[3][0] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[2][0].setGraphic(new Circle(10));
    buttonList[3][0].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[3][0].getGraphic())).setFill(Color.DODGERBLUE);
    game.setButtonList(buttonList);
    game.setRows(4);
    game.setColumns(1);
    
    /* Test 0 buttons above specified button in array */
    game.setClickedColor(Color.DODGERBLUE);
    game.setCurrentRow(3);
    game.setCurrentColumn(0);
    game.buttonsAbove();
    assertEquals("There are 0 contiguous buttons above the button at the specified indices ", 0, game.getButtonsAbove());
    game.resetVariables();
    
    /* Test 1 button above specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(1);
    game.setCurrentColumn(0);
    game.buttonsAbove();
    assertEquals("There is 1 contiguous buttons above the button at specified indices ", 1, game.getButtonsAbove());
    game.resetVariables();
    
    /* Test many buttons above specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(2);
    game.setCurrentColumn(0);
    game.buttonsAbove();
    assertEquals("There are 2 contiguous buttons above the button at the specified indices ", 2, game.getButtonsAbove());
    game.resetVariables();
    
  }
  
  /**
   * Tests buttonsBelow method
   */
  @Test
  public void testButtonsBelow() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array */
    Button[][] buttonList = new Button[4][1];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[2][0] = new Button();
    buttonList[3][0] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[2][0].setGraphic(new Circle(10));
    buttonList[3][0].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[3][0].getGraphic())).setFill(Color.DODGERBLUE);
    game.setButtonList(buttonList);
    game.setRows(4);
    game.setColumns(1);
    
    /* Test 0 buttons below specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(2);
    game.setCurrentColumn(0);
    game.buttonsBelow();
    assertEquals("There are 0 contiguous buttons below the button at the specified indices ", 0, game.getButtonsBelow());
    game.resetVariables();
    
    /* Test 1 button below specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(1);
    game.setCurrentColumn(0);
    game.buttonsBelow();
    assertEquals("There is 1 contiguous button below the button at specified indices ", 1, game.getButtonsBelow());
    game.resetVariables();
    
    /* Test many buttons below specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.buttonsBelow();
    assertEquals("There are 2 contiguous buttons below the button at the specified indices ", 2, game.getButtonsBelow());
    game.resetVariables();
    
  }
  
  /**
   * Tests buttonsRight method
   */
  @Test
  public void testButtonsRight() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array */
    Button[][] buttonList = new Button[1][4];
    buttonList[0][0] = new Button();
    buttonList[0][1] = new Button();
    buttonList[0][2] = new Button();
    buttonList[0][3] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[0][1].setGraphic(new Circle(10));
    buttonList[0][2].setGraphic(new Circle(10));
    buttonList[0][3].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.FIREBRICK);
    game.setButtonList(buttonList);
    game.setRows(1);
    game.setColumns(4);
    
    /* Test 0 buttons right of specified button in array */
    game.setClickedColor(Color.DODGERBLUE);
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.buttonsRight();
    assertEquals("There are 0 contiguous buttons right of the button at the specified indices ", 0, game.getButtonsRight());
    game.resetVariables();
    
    /* Test 1 button right of specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(0);
    game.setCurrentColumn(2);
    game.buttonsRight();
    assertEquals("There is 1 contiguous button right of the button at specified indices ", 1, game.getButtonsRight());
    game.resetVariables();
    
    /* Test many buttons right of specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(0);
    game.setCurrentColumn(1);
    game.buttonsRight();
    assertEquals("There are 2 contiguous buttons right of the button at the specified indices ", 2, game.getButtonsRight());
    game.resetVariables();
    
  }
  
  /** 
   * Tests buttonsLeft method
   */
  @Test
  public void testButtonsLeft() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array */
    Button[][] buttonList = new Button[1][4];
    buttonList[0][0] = new Button();
    buttonList[0][1] = new Button();
    buttonList[0][2] = new Button();
    buttonList[0][3] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[0][1].setGraphic(new Circle(10));
    buttonList[0][2].setGraphic(new Circle(10));
    buttonList[0][3].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.FIREBRICK);
    game.setButtonList(buttonList);
    game.setRows(1);
    game.setColumns(4);
    
    /* Test 0 buttons left of specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(0);
    game.setCurrentColumn(1);
    game.buttonsLeft();
    assertEquals("There are 0 contiguous buttons left of the button at the specified indices ", 0, game.getButtonsLeft());
    game.resetVariables();
    
    /* Test 1 button left of specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(0);
    game.setCurrentColumn(2);
    game.buttonsLeft();
    assertEquals("There is 1 contiguous button left of the button at specified indices ", 1, game.getButtonsLeft());
    game.resetVariables();
    
    /* Test many buttons left of specified button in array */
    game.setClickedColor(Color.FIREBRICK);
    game.setCurrentRow(0);
    game.setCurrentColumn(3);
    game.buttonsLeft();
    assertEquals("There are 2 contiguous buttons left of the button at the specified indices ", 2, game.getButtonsLeft());
    game.resetVariables();
    
  }
  
  /**
   * Tests the method countButtons
   */
  @Test
  public void testCountButtons() {
    /* Since we already tested the methods called in this method, 
     * and this method does not perform any other actions other than calling these methods,
     * we only need to test that the methods were called with correct values being stored */
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array */
    Button[][] buttonList = new Button[1][1];
    buttonList[0][0] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.DODGERBLUE);
    game.setButtonList(buttonList);
    game.setRows(1);
    game.setColumns(1);
    /* Specifying values of the button to analyze */
    game.setClickedColor(Color.DODGERBLUE);
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.countButtons();
    /* Checking if each method set their respective fields to 0 */
    assertEquals("There are 0 contiguous buttons left of the button at the specified indices ", 0, game.getButtonsLeft());
    assertEquals("There are 0 contiguous buttons right of the button at the specified indices ", 0, game.getButtonsRight());
    assertEquals("There are 0 contiguous buttons above the button at the specified indices ", 0, game.getButtonsAbove());
    assertEquals("There are 0 contiguous buttons below the button at the specified indices ", 0, game.getButtonsBelow());
    game.resetVariables();
  }
  
  /**
   * Tests the method dropSingleColumn
   */
  @Test
  public void testDropSingleColumn() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[3][1];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[2][0] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[2][0].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.YELLOW);
    game.setButtonList(buttonList);
    game.setRows(3);
    game.setColumns(1);
    
    /* Test for dropping 0 buttons because the specified button is at the top */
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.dropSingleColumn(0, 0, 0);
    assertArrayEquals("The specified button is at the top of the array grid, so no buttons are dropped, and does not enter if statement because index of" + 
                 "button to check is not valid", buttonList, game.getButtonList());
    game.resetVariables();
    
    /* Test for dropping 1 button because the specified button is in the middle */
    game.setCurrentRow(1);
    game.dropSingleColumn(1, 0, 0);
    assertEquals("The specified button is second from the top of the array grid, so one button is dropped, and does enter if statement because" + 
                 "index of button to check, or button above current button, is at the top. Top button should be gray", Color.LIGHTGRAY, 
                      (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("Button at index specified should recieve color of button above it, which is LIGHTGRAY", Color.LIGHTGRAY, 
                      (Color)(((Shape)(game.getButtonList()[1][0].getGraphic())).getFill()));
    game.resetVariables();
    
    /* Test for dropping many buttons because the specified button is at the bottom of the grid, so drops all buttons above it */
    game.setCurrentRow(2);
    game.setButtonList(buttonList);
    game.dropSingleColumn(2, 0, 0);
    ((Shape)(game.getButtonList()[1][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(game.getButtonList()[2][0].getGraphic())).setFill(Color.DODGERBLUE);
    assertEquals("The specified button is at the bottom of the grid, so all button's above it are dropped, and enter's if statement in second loop when " + 
                 "checking index of button at the top. Top button should be gray.",
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("The specified button is at the bottom of the grid, so all button's above it are dropped, and enter's if statement in second loop when " + 
                 "checking index of button at the top. Middle button should be FIREBRICK",
                 Color.FIREBRICK, (Color)(((Shape)(game.getButtonList()[1][0].getGraphic())).getFill()));
    assertEquals("The specified button is at the bottom of the grid, so all button's above it are dropped, and enter's if statement in second loop when " + 
                 "checking index of button at the top. Last button should be DODGERBLUE.",
                 Color.DODGERBLUE, (Color)(((Shape)(game.getButtonList()[2][0].getGraphic())).getFill()));
    game.resetVariables();
  }
  
  /**
   * Tests the method removeVerticalContigButtons
   */
  @Test
  public void testRemoveVerticalContigButtons() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[3][1];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[2][0] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[2][0].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.DODGERBLUE);
    game.setButtonList(buttonList);
    game.setRows(3);
    game.setColumns(1);
    
    /* Test for removing 0 buttons in a column */
    game.setCurrentRow(2);
    game.setCurrentColumn(0);
    game.setButtonsAbove(0);
    game.setButtonsBelow(0);
    game.removeVerticalContigButtons();
    assertArrayEquals("There are no vertically contiguous buttons, so the loop is not entered and therefore nothing is done to the column", 
                 buttonList, game.getButtonList());
    game.resetVariables();
    
    /* Cannot test for removing one button because the game rules do not allow one button at a time to be removed */
    
    /* Test for removing many buttons in a column, at the first, middle, and last positions */
    game.setCurrentRow(1);
    game.setCurrentColumn(0);
    game.setButtonsAbove(1);
    game.setButtonsBelow(1);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.FIREBRICK);
    game.removeVerticalContigButtons();
    assertEquals("All buttons are vertically contiguous, so the loop is entered and all the colors are dropped. Top color should be gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("All buttons are vertically contiguous, so the loop is entered and all the colors are dropped. Middle button should be gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[1][0].getGraphic())).getFill()));
    assertEquals("All buttons are vertically contiguous, so the loop is entered and all the colors are dropped. Bottom button should be gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[2][0].getGraphic())).getFill()));
    game.resetVariables();
    
  }
  
  /*
   * Tests the removeHorizontalContigButtons
   */
  @Test
  public void testRemoveHorizontalContigButtons() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[1][4];
    buttonList[0][0] = new Button();
    buttonList[0][1] = new Button();
    buttonList[0][2] = new Button();
    buttonList[0][3] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[0][1].setGraphic(new Circle(10));
    buttonList[0][2].setGraphic(new Circle(10));
    buttonList[0][3].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.DODGERBLUE);
    game.setButtonList(buttonList);
    game.setRows(1);
    game.setColumns(4);
    
    /* Tests for removing 0 buttons in a row */
    game.setCurrentRow(3);
    game.setCurrentColumn(0);
    game.setButtonsLeft(0);
    game.setButtonsRight(0);
    game.removeHorizontalContigButtons();
    assertArrayEquals("There are no horizontally contiguous buttons, so neither of the loops are entered and therefore nothing is done to the column", 
                 buttonList, game.getButtonList());
    game.resetVariables();
    
    /* Cannot test for removing one button in row because the game's rules do not allow one button at a time to be removed */
    
    /* Tests for removing many buttons in a row where buttons are first, middle, and last */
    game.setCurrentRow(0);
    game.setCurrentColumn(1);
    game.setButtonsLeft(1);
    game.setButtonsRight(2);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.FIREBRICK);
    game.removeHorizontalContigButtons();
    assertEquals("All horizontal buttons are contiguous, so loop is entered, if statement is entered because column being checked is not equal to" + 
                 "the current button, so leftmost button is set to gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("All horizontal buttons are contiguous, so loop is entered but if statement is not because the row being checked is equal to current row" +
                 "so nothing is done to the current column. However, the if statement outside of the loop is entered because there is no vertical change" +
                 "so this button is removed as well and should be gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
    assertEquals("All horizontal buttons are contiguous, so loop is entered, if statement is entered because column being checked is not equal to" + 
                 "the current button, so button at column index 2 is set to gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][2].getGraphic())).getFill()));
    assertEquals("All horizontal buttons are contiguous, so loop is entered, if statement is entered because column being checked is not equal to" + 
                 "the current button, so button at column index 3 is set to gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][3].getGraphic())).getFill()));
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.FIREBRICK);
    game.setButtonsAbove(1);
    game.removeHorizontalContigButtons();
    assertEquals("All horizontal buttons are contiguous, so loop is entered but if statement is not because the row being checked is equal to current row" +
                 "so nothing is done to the current column. And, the if statement outside of the loop is not entered because there is vertical change" +
                 "so the button at the current column should not be removed, meaning it should remain FIREBRICK", 
                 Color.FIREBRICK, (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
  }
  
  /**
   * Tests the method removeButtons
   */
  @Test
  public void testRemoveButtons() {
    
    /* We already tested the methods called, so we just need to test the conditional statement */
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[4][1];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[2][0] = new Button();
    buttonList[3][0] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[2][0].setGraphic(new Circle(10));
    buttonList[3][0].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.YELLOW);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.YELLOW);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[3][0].getGraphic())).setFill(Color.LIGHTGRAY);
    game.setButtonList(buttonList);
    game.setRows(4);
    game.setColumns(1);
    
    /* Test if button clicked is gray */
    game.setCurrentRow(3);
    game.setCurrentColumn(0);
    game.setClickedColor(Color.LIGHTGRAY);
    game.removeButtons();
    assertArrayEquals("The button clicked had color gray, so nothing happens", buttonList, game.getButtonList());
    
    /* Test if button clicked is not gray */
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.setClickedColor(Color.YELLOW);
    game.removeButtons();
    assertEquals("Top two buttons are contiguous so they are removed so every button is color gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("Top two buttons are contiguous so they are removed so every button is color gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[1][0].getGraphic())).getFill()));
    assertEquals("Top two buttons are contiguous so they are removed so every button is color gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[2][0].getGraphic())).getFill()));
    assertEquals("Top two buttons are contiguous so they are removed so every button is color gray", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[3][0].getGraphic())).getFill()));
  }
  
  /**
   * Test shiftColumns method
   */
  @Test
  public void testShiftColumns() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[1][4];
    buttonList[0][0] = new Button();
    buttonList[0][1] = new Button();
    buttonList[0][2] = new Button();
    buttonList[0][3] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[0][1].setGraphic(new Circle(10));
    buttonList[0][2].setGraphic(new Circle(10));
    buttonList[0][3].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.DODGERBLUE);
    game.setButtonList(buttonList);
    game.setRows(1);
    game.setColumns(4);
    
    /* Test for removing no columns */
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.shiftColumns();
    assertEquals("No columns were empty so iterated through all rows and never entered if statement, and colors remain the same", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("No columns were empty so iterated through all rows and never entered if statement, and colors remain the same", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
    assertEquals("No columns were empty so iterated through all rows and never entered if statement, and colors remain the same", Color.FIREBRICK, 
                 (Color)(((Shape)(game.getButtonList()[0][2].getGraphic())).getFill()));
    assertEquals("No columns were empty so iterated through all rows and never entered if statement, and colors remain the same", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][3].getGraphic())).getFill()));
    
    
    /* Test for removing one column where column is the first column */
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.LIGHTGRAY);
    game.shiftColumns();
    assertEquals("First column empty so all columns shift to the left one. Left column is now DODGERBLUE", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("First column empty so all columns shift to the left one. Second column is now FIREBRICK", Color.FIREBRICK, 
                 (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
    assertEquals("First column empty so all columns shift to the left one. Third column is now DODGERBLUE", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][2].getGraphic())).getFill()));
    assertEquals("First column empty so all columns shift to the left one. Last column is now LIGHTGRAY", Color.LIGHTGRAY, 
                 (Color)(((Shape)(game.getButtonList()[0][3].getGraphic())).getFill()));
    game.resetVariables();
    
    /* Test for removing one column where column is in the middle */
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.DODGERBLUE);
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.shiftColumns();
    assertEquals("Second column empty so this column is not affected and remain DODGERBLUE", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("Second column empty, so rest of columns enter loop and shift left one. Second column is now FIREBRICK", Color.FIREBRICK, 
                 (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
    assertEquals("Second column empty, so rest of columns enter loop and shift left one. Third column is now DODGERBLUE", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][2].getGraphic())).getFill()));
    assertEquals("Second column empty, so rest of columns enter loop and shift left one. Last column is now LIGHTGRAY", Color.LIGHTGRAY, 
                 (Color)(((Shape)(game.getButtonList()[0][3].getGraphic())).getFill()));
    game.resetVariables();
    
    /* Test for removing one column where column is last column */
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.LIGHTGRAY);
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.shiftColumns();
    assertEquals("Last column is empty, so no other columns are shifted and color remains  DODGERBLUE", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("Last column is empty, so no other columns are shifted and color remains  DODGERBLUE", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
    assertEquals("Last column is empty, so no other columns are shifted and color remains  FIREBRICK. Else loop is entered because this column rechecked" + 
                 "itself and was empty", Color.FIREBRICK, (Color)(((Shape)(game.getButtonList()[0][2].getGraphic())).getFill()));
    assertEquals("Last column is empty, so no other columns are shifted and color remains  LIGHTGRAY", Color.LIGHTGRAY, 
                 (Color)(((Shape)(game.getButtonList()[0][3].getGraphic())).getFill()));
    game.resetVariables();
    
    /* Test for removing many empty columns */
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.YELLOW);
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    game.shiftColumns();
    assertEquals("Middle two columns removed so this column is not affected and remains DODGERBLUE", Color.DODGERBLUE, 
                 (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("Middle two columns removed so this column gets the next non-gray color in its row, which is YELLOW", Color.YELLOW, 
                 (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
    assertEquals("Middle two columns removed and last column shifted over two, so this column is empty", Color.LIGHTGRAY, 
                 (Color)(((Shape)(game.getButtonList()[0][2].getGraphic())).getFill()));
    assertEquals("Middle two columns removed and last column shifted over two, so this column is empty", Color.LIGHTGRAY, 
                 (Color)(((Shape)(game.getButtonList()[0][3].getGraphic())).getFill()));
    game.resetVariables();
    
  }
  
  /**
   * Test the columnsRight method
   */
  @Test
  public void testColumnsRight() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[1][4];
    buttonList[0][0] = new Button();
    buttonList[0][1] = new Button();
    buttonList[0][2] = new Button();
    buttonList[0][3] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[0][1].setGraphic(new Circle(10));
    buttonList[0][2].setGraphic(new Circle(10));
    buttonList[0][3].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.LIGHTGRAY);
    game.setButtonList(buttonList);
    game.setRows(1);
    game.setColumns(4);
    
    /* Test if all columns right of specified column are empty when empty column is first */
    game.setCurrentRow(0);
    game.setCurrentColumn(0);
    assertEquals("", true, game.columnsRight(0));
    
    /* Test if all columns right of specified column are empty when empty column is in the middle */
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    assertEquals("", true, game.columnsRight(1));
    
    /* Test if all columns right of specified column are empty when empty column is last */
    assertEquals("", true, game.columnsRight(3));
    
    /* Test if all columns right of the specified column are NOT empty, so the if statement is entered and columnsRight = false */
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][2].getGraphic())).setFill(Color.YELLOW);
    ((Shape)(buttonList[0][3].getGraphic())).setFill(Color.LIGHTGRAY);
    assertEquals("", false, game.columnsRight(1));
    
  }
  
  /**
   * Tests method removeSingleColumn
   */
  @Test
  public void testRemovesSingleColumn() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[2][2];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[0][1] = new Button();
    buttonList[1][1] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[0][1].setGraphic(new Circle(10));
    buttonList[1][1].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.YELLOW);
    ((Shape)(buttonList[1][1].getGraphic())).setFill(Color.FIREBRICK);
    game.setButtonList(buttonList);
    game.setColumns(2);
    
    /* Test for removing buttons when there are 0 rows */
    game.setRows(0);
    game.removeSingleColumn(0);
    /* Does nothing because the loop is not entered and the method exits */
    
    /* Test for removing buttons when there is 1 row */
    game.setRows(1);
    game.removeSingleColumn(0);
    assertEquals("Both columns should have the same color", 
                 (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()) , 
                 (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
    
    /* Test for removing buttons when there are many rows */
    game.setRows(2);
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.DODGERBLUE);
    game.removeSingleColumn(0);
    assertEquals("Buttons in first column should now be the same color as the buttons in the second column", 
                 (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()) , 
                 (Color)(((Shape)(game.getButtonList()[0][1].getGraphic())).getFill()));
    assertEquals("Buttons in first column should now be the same color as the buttons in the second column", 
                 (Color)(((Shape)(game.getButtonList()[1][0].getGraphic())).getFill()) , 
                 (Color)(((Shape)(game.getButtonList()[1][1].getGraphic())).getFill()));
    
    /* Test for removing buttons when the specified column is the last column */
    /* Calling game.removeSingleColumn(0) will throw arrayIndexOutOfBoundsException because tries to copy from a column that does not exist.
     * However this method is only called by the shiftColumns method, which only calls this method in a loop where the condition (see line 368 in CrossGame)
     * ensures that the loop will never be entered when checking the last column of the grid, so we do not need to worry about testing for the 
     * removing the last column */
    
  }
  
  /**
   * Tests the method columnEmpty
   */
  @Test
  public void testColumnEmpty() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[3][1];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[2][0] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[2][0].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.YELLOW);
    game.setButtonList(buttonList);
    game.setColumns(1);
    game.setRows(3);
    
    /* Test if the column is empty when it is not empty */
    assertEquals("Column is not empty so should return false", false, game.columnEmpty(0));
    
    /* Test if the column is empty when it is empty */
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.LIGHTGRAY);
    assertEquals("Column is empty so should return true", true, game.columnEmpty(0));
    
  }
  
  /**
   * Tests the method setColumnEmpty
   */
  @Test
  public void testSetColumnEmpty() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[4][1];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[2][0] = new Button();
    buttonList[3][0] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[2][0].setGraphic(new Circle(10));
    buttonList[3][0].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.YELLOW);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.YELLOW);
    ((Shape)(buttonList[2][0].getGraphic())).setFill(Color.YELLOW);
    ((Shape)(buttonList[3][0].getGraphic())).setFill(Color.YELLOW);
    game.setButtonList(buttonList);
    game.setColumns(1);
    
    /* Test for removing all buttons when there are zero rows */
    game.setRows(0);
    game.setColumnEmpty(0);
    /* Nothing happens because does not enter the loop and method ends */
    
    /* Test for removing all buttons when there is one row */
    game.setRows(1);
    game.setColumnEmpty(0);
    assertEquals("Only one row, so row is set to gray", Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    
    /* Test for removing all buttons when there are many rows */
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.YELLOW);
    game.setRows(4);
    game.setColumnEmpty(0);
    assertEquals("Column initially all yellow, now all buttons should be LIGHTGRAY", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[0][0].getGraphic())).getFill()));
    assertEquals("Column initially all yellow, now all buttons should be LIGHTGRAY", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[1][0].getGraphic())).getFill()));
    assertEquals("Column initially all yellow, now all buttons should be LIGHTGRAY", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[2][0].getGraphic())).getFill()));
    assertEquals("Column initially all yellow, now all buttons should be LIGHTGRAY", 
                 Color.LIGHTGRAY, (Color)(((Shape)(game.getButtonList()[3][0].getGraphic())).getFill()));
    
  }
  
  /**
   * Tests method checkWon for extra credit assignment
   */
  @Test
  public void testCheckWon() {
    
    CrossGame game = new CrossGame();
    game.main(new String[0]);
    /* Creating custom button array and specifies dimensions */
    Button[][] buttonList = new Button[2][2];
    buttonList[0][0] = new Button();
    buttonList[1][0] = new Button();
    buttonList[0][1] = new Button();
    buttonList[1][1] = new Button();
    buttonList[0][0].setGraphic(new Circle(10));
    buttonList[1][0].setGraphic(new Circle(10));
    buttonList[0][1].setGraphic(new Circle(10));
    buttonList[1][1].setGraphic(new Circle(10));
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.LIGHTGRAY);
    ((Shape)(buttonList[1][1].getGraphic())).setFill(Color.LIGHTGRAY);
    game.setButtonList(buttonList);
    game.setColumns(2);
    game.setRows(2);
    
    /* Test if board is empty and won is true */
    assertEquals("board is empty so should return true", true, game.checkWon());
    
    /* Test if board is not empty and won is false */
    ((Shape)(buttonList[0][0].getGraphic())).setFill(Color.FIREBRICK);
    ((Shape)(buttonList[1][0].getGraphic())).setFill(Color.DODGERBLUE);
    ((Shape)(buttonList[0][1].getGraphic())).setFill(Color.YELLOW);
    ((Shape)(buttonList[1][1].getGraphic())).setFill(Color.FIREBRICK);
    assertEquals("board is not empty so should return false", false, game.checkWon());
    
  }
  
  /** 
   * Tests the getRows and setRows methods 
   */
  @Test
  public void testGetRowsSetRows() {
    CrossGame game = new CrossGame();
    assertEquals("Rows is initialized as 12", 12, game.getRows());
    game.setRows(7);
    assertEquals("Rows has been changed to 7 by setRows", 7, game.getRows());
  }
  
  /** Tests the getColumns and setColumns methods */
  @Test
  public void testGetColumnsSetColumns() {
    CrossGame game = new CrossGame();
    assertEquals("Columns is initialized as 12", 12, game.getColumns());
    game.setColumns(7);
    assertEquals("Columns has been changed to 7 by setColumns", 7, game.getColumns());
  }
  
  /** Tests the getNumberColors and setNumberColors methods */
  @Test
  public void testGetNumberColorsSetNumberColors() {
    CrossGame game = new CrossGame();
    assertEquals("Number of colors is initialized as 3", 3, game.getNumberColors());
    game.setNumberColors(5);
    assertEquals("Number of colors has been changed to 5 by setNumberColors", 5, game.getNumberColors());
  }
  
  /**
   * Method to test getButtonList and setButtonList methods
   */
  @Test
  public void testGetButtonListSetButtonList() {
    CrossGame game = new CrossGame();
    Button[][] buttonList = new Button[2][2];
    game.setButtonList(buttonList);
    assertArrayEquals(buttonList, game.getButtonList());
  }
  
  /** Tests the getCurrentRow and setCurrentRow methods */
  @Test
  public void testGetCurrentRowSetCurrentRow() {
    CrossGame game = new CrossGame();
    game.setCurrentRow(7);
    assertEquals("Current row has been changed to 7 by setCurrentRow", 7, game.getCurrentRow());
  }
  
  /** Tests the getCurrentColumn and setCurrentColumn methods */
  @Test
  public void testGetCurrentColumnSetCurrentColumn() {
    CrossGame game = new CrossGame();
    game.setCurrentColumn(3);
    assertEquals("Current column has been changed to 3 by setCurrentColumn", 3, game.getCurrentColumn());
  }
  
  /** Tests the getButtonsAbove and setButtonsAbove methods */
  @Test
  public void testGetButtonsAboveSetButtonsAbove() {
    CrossGame game = new CrossGame();
    game.setButtonsAbove(6);
    assertEquals("Buttons above has been changed to 6 by setButtonsAbove", 6, game.getButtonsAbove());
  }
  
  /** Tests the getButtonsBelow and setButtonsBelow methods */
  @Test
  public void testGetButtonsBelowSetButtonsBelow() {
    CrossGame game = new CrossGame();
    game.setButtonsBelow(5);
    assertEquals("Buttons below has been changed to 6 by setButtonsBelow", 5, game.getButtonsBelow());
  }
  
  /** Tests the getButtonsLeft and setButtonsLeft methods */
  @Test
  public void testGetButtonsLeftSetButtonsLeft() {
    CrossGame game = new CrossGame();
    game.setButtonsLeft(9);
    assertEquals("Buttons left has been changed to 6 by setButtonsLeft", 9, game.getButtonsLeft());
  }
  
  /** Tests the getButtonsRight and setButtonsRight methods */
  @Test
  public void testGetButtonsRightSetButtonsRight() {
    CrossGame game = new CrossGame();
    game.setButtonsRight(9);
    assertEquals("Buttons right has been changed to 6 by setButtonsRight", 9, game.getButtonsRight());
  }
  
  /** Tests the getClickedColor and setClickedColor methods */
  @Test
  public void testGetClickedColorSetClickedColor() {
    CrossGame game = new CrossGame();
    game.setClickedColor(Color.BLUE);
    assertEquals("Button color was changed to blue so returns blue", Color.BLUE, game.getClickedColor());
  }
}