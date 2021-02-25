import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.io.*;


public class GuiPacman extends Application
{
  private String outputBoard; // The filename for where to save the Board
  private Board board; // The Game Board

  // Fill colors to choose
  private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);

  /** Add your own Instance Variables here */
  GridPane pane = new GridPane();
  BorderPane header = new BorderPane();
  Text score = new Text();
  StackPane gamepane = new StackPane();
  BorderPane gameover = new BorderPane();
  String direction = "";
  public static int ghostNum = 0;



  


  /* 
   * Name:      start
   * Purpose:   Start and keep the game running.
   * Parameter: 
   * Return:    
   */
  @Override
  public void start(Stage primaryStage)
  {
    // Process Arguments and Initialize the Game Board
    processArgs(getParameters().getRaw().toArray(new String[0]));

    header.setStyle("-fx-background-color: rgb(192, 192, 192)");

    pane.setAlignment(Pos.CENTER);
    // Set the padding of the pane. 
    pane.setPadding(new Insets(11.5,12.5,13.5,14.5));     
    pane.setHgap(5.5); 
    pane.setVgap(5.5);
    pane.setStyle("-fx-background-color: rgb(192, 192, 192)");

    score.setText("Score: " + " " + board.getScore());
    score.setFont(Font.font("Courier New", 16));
    header.setCenter(score);
    
    Text title = new Text();
    title.setText("Pac-Man");
    title.setFont(Font.font("Courier New", 32));
    header.setLeft(title);

    for(int row = 0; row < board.getBoardSize(); row++)
    {
      for(int col = 0; col < board.getBoardSize(); col++)
      { 
        Tile toAdd = new Tile(board.getGrid()[row][col]);
        pane.add(toAdd.getNode(), col  , row);
      }
    }

    BorderPane border = new BorderPane();
    border.setTop(header);
    border.setCenter(pane);

    gamepane.getChildren().addAll(border, gameover);

    Scene scene = new Scene(gamepane,1000,1000); //scene that needs to be displayed
    primaryStage.setTitle("GuiPacman"); //title of the window(primary stage)
    scene.setOnKeyPressed(new myKeyHandler());
    primaryStage.setScene(scene); // set what scene to show inside the window
    primaryStage.show();


  }



  /** Add your own Instance Methods Here */

  /*
   * Name:       myKeyHandler
   *
   * Purpose:     
   *
   *
   */
  private class myKeyHandler implements EventHandler<KeyEvent> {

   /* 
    * Name:      handle
    * Purpose:   handle the KeyEvent of user's input.
    * Parameter: 
    * Return:    
    */
    @Override
    public void handle (KeyEvent e) {

       
      if(!board.isGameOver())
      {
        if(e.getCode().equals(KeyCode.UP) || 
           e.getCode().equals(KeyCode.W))
        {
          if ( board.canMove(Direction.UP) ) 
          {
            board.move(Direction.UP);
            System.out.println("Moving up");
            board.refreshGrid();
            pane.getChildren().clear();
            direction = "up";
            score.setText("Score: " + " " + board.getScore());          
            for(int row = 0; row < board.getBoardSize(); row++)
            {
              for(int col = 0; col < board.getBoardSize(); col++)
              { 
                Tile toAdd = new Tile(board.getGrid()[row][col]);
                pane.add(toAdd.getNode(), col, row);
              }
            }
            gameIsOver();
          }
        }

        else if(e.getCode().equals(KeyCode.DOWN) || 
                e.getCode().equals(KeyCode.S))
        {
          if ( board.canMove(Direction.DOWN) ) 
          {
            board.move(Direction.DOWN);
            System.out.println("Moving down");
            board.refreshGrid();
            pane.getChildren().clear();
            direction = "down";
            score.setText("Score: " + " " + board.getScore());          
            for(int row = 0; row < board.getBoardSize(); row++)
            {
              for(int col = 0; col < board.getBoardSize(); col++)
              { 
                Tile toAdd = new Tile(board.getGrid()[row][col]);
                pane.add(toAdd.getNode(), col  , row);
              }
            }
            gameIsOver();
          }
        }

        else if(e.getCode().equals(KeyCode.LEFT) || 
                e.getCode().equals(KeyCode.A))
        {
          if ( board.canMove(Direction.LEFT) ) 
          {
            board.move(Direction.LEFT);
            System.out.println("Moving left");
            board.refreshGrid();
            pane.getChildren().clear();
            direction = "left";
            score.setText("Score: " + " " + board.getScore());      
            for(int row = 0; row < board.getBoardSize(); row++)
            {
              for(int col = 0; col < board.getBoardSize(); col++)
              { 
                Tile toAdd = new Tile(board.getGrid()[row][col]);
                pane.add(toAdd.getNode(), col  , row);
              }
            }
            gameIsOver();
          }
        }

        else if(e.getCode().equals(KeyCode.RIGHT) || 
                e.getCode().equals(KeyCode.D))
        {
          if ( board.canMove(Direction.RIGHT) ) 
          {
            board.move(Direction.RIGHT);
            System.out.println("Moving right");
            board.refreshGrid();
            pane.getChildren().clear();
            score.setText("Score: " + " " + board.getScore());
            for(int row = 0; row < board.getBoardSize(); row++)
            {
              for(int col = 0; col < board.getBoardSize(); col++)
              { 
                Tile toAdd = new Tile(board.getGrid()[row][col]);
                pane.add(toAdd.getNode(), col  , row);
              }
            }
            gameIsOver();
          }
        }

        else if(e.getCode().equals(KeyCode.Q))
        {
          try
          {
          board.saveBoard(outputBoard);
          System.out.println("Saving file in " + outputBoard);
          }
          catch(IOException ex)
          {
            System.out.println("Save Failed");
          }
          System.exit(0);
        }

        else
        {
          System.out.println("Invalid move!");
        }      
      }
    }


    /* 
     * Name:      gameIsOver
     * Purpose:   Check if the game is over and show the gameover board.
     * Parameter: 
     * Return:    
     */
    private void gameIsOver() {
      if(board.isGameOver())
      {
        gameover.setStyle("-fx-background-color: rgba(246, 246, 246, 0.5)");
        Text end = new Text();
        end.setText("GAME OVER!");
        end.setFont(Font.font("Helvetica", 72));
        end.setFill(Color.RED);
        gameover.setCenter(end);
      }
    }
  } // End of Inner Class myKeyHandler.



  /*
   * Name:        Tile
   *
   * Purpose:     This class tile helps to make the tiles in the board 
   *              presented using JavaFX. Whenever a tile is needed,
   *              the constructor taking one char parameter is called
   *              and create certain ImageView fit to the char representation
   *              of the tile.
   * 
   *
   */
  private class Tile {


    private ImageView repr;   // This field is for the Rectangle of tile.
 
    /* 
     * Constructor
     *
     * Purpose:   
     * Parameter: 
     *
     */
    public Tile(char tileAppearance) {
      Image image;
      if(tileAppearance == 'P')
      {
        //create an image object
        image = new Image("image/pacman_right.png");
        repr = new ImageView(image);
        if(direction.equals("up"))
        {
          repr.setRotate(270);
        }
        if(direction.equals("down"))
        {
          repr.setRotate(90);
        }
        if(direction.equals("left"))
        {
          repr.setRotate(180);
        }
 
      }

      if(tileAppearance == 'G')
      {
        switch (ghostNum)
        {
          case 0: image = new Image("image/blinky_left.png");
                  repr = new ImageView(image); 
                  ghostNum++;
                  break;
          case 1: image = new Image("image/pinky_left.png"); 
                  repr = new ImageView(image);
                  ghostNum++;
                  break;
          case 2: image = new Image("image/clyde_up.png"); 
                  repr = new ImageView(image);
                  ghostNum++;
                  break;
          case 3: image = new Image("image/inky_down.png");
                  repr = new ImageView(image); 
                  ghostNum -= 3;
                  break;
        }        
      }

      if(tileAppearance == '*')
      {
        image = new Image("image/dot_uneaten.png");
        repr = new ImageView(image);
 
      }

      if(tileAppearance == ' ')
      {
        image = new Image("image/dot_eaten.png");
        repr = new ImageView(image);
      }

      if(tileAppearance == 'X')
      {
        image = new Image("image/pacman_dead.png");
        repr = new ImageView(image);
      }

      if(tileAppearance == 'C')
      {
        image = new Image("image/cherry.png");
        repr = new ImageView(image);
      }

      if(board.getBoardSize() >= 13)
      {
        repr.setFitWidth(30);  //set the width and height 
        repr.setFitHeight(30);
      }
      else
      {
      repr.setFitWidth(50);  //set the width and height 
      repr.setFitHeight(50);
      }





    }


    public ImageView getNode() {
      return repr;
    }

  }  // End of Inner class Tile

  public static void main(String[] args)
  {
    System.out.println("Welcome to Pac-Man!");

    GuiPacman pacman = new GuiPacman();
    pacman.processArgs(args);
    Application.launch(args);
  }




  /** DO NOT EDIT BELOW */

  // The method used to process the command line arguments
  private void processArgs(String[] args)
  {
    String inputBoard = null;   // The filename for where to load the Board
    int boardSize = 0;          // The Size of the Board

    // Arguments must come in pairs
    if((args.length % 2) != 0)
    {
      printUsage();
      System.exit(-1);
    }

    // Process all the arguments 
    for(int i = 0; i < args.length; i += 2)
    {
      if(args[i].equals("-i"))
      {   // We are processing the argument that specifies
        // the input file to be used to set the board
        inputBoard = args[i + 1];
      }
      else if(args[i].equals("-o"))
      {   // We are processing the argument that specifies
        // the output file to be used to save the board
        outputBoard = args[i + 1];
      }
      else if(args[i].equals("-s"))
      {   // We are processing the argument that specifies
        // the size of the Board
        boardSize = Integer.parseInt(args[i + 1]);
      }
      else
      {   // Incorrect Argument 
        printUsage();
        System.exit(-1);
      }
    }

    // Set the default output file if none specified
    if(outputBoard == null)
      outputBoard = "Pac-Man.board";
    // Set the default Board size if none specified or less than 2
    if(boardSize < 3)
      boardSize = 10;

    // Initialize the Game Board
    try{
      if(inputBoard != null)
        board = new Board(inputBoard);
      else
        board = new Board(boardSize);
    }
    catch (Exception e)
    {
      System.out.println(e.getClass().getName() + " was thrown while creating a " +
          "Board from file " + inputBoard);
      System.out.println("Either your Board(String, Random) " +
          "Constructor is broken or the file isn't " +
          "formated correctly");
      System.exit(-1);
    }
  }

  // Print the Usage Message 
  private static void printUsage()
  {
    System.out.println("GuiPacman");
    System.out.println("Usage:  GuiPacman [-i|o file ...]");
    System.out.println();
    System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
    System.out.println();
    System.out.println("  -i [file]  -> Specifies a Pacman board that should be loaded");
    System.out.println();
    System.out.println("  -o [file]  -> Specifies a file that should be used to save the Pac-Man board");
    System.out.println("                If none specified then the default \"Pac-Man.board\" file will be used");
    System.out.println("  -s [size]  -> Specifies the size of the Pac-Man board if an input file hasn't been");
    System.out.println("                specified.  If both -s and -i are used, then the size of the board");
    System.out.println("                will be determined by the input file. The default size is 10.");
  }
}


