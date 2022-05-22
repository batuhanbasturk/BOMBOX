//Firat Bakici 150120029
//Batuhan basturk 150119035

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Game extends Application
{
	public static GridPane pane;			//the pane of game(tiles) which is binded to gamemanager's
	public static MenuBar menuUI;			//the top menu that contains in-game setting UI
	
	public static Label bombLabel = new Label("  Normal");
	public static Label levelLabel = new Label("  " + Game_Manager.currentLevel);
	public static ImageView reactionImage = new ImageView(Image_Manager.huhImage);		//a reaction image that changes when scores are different
	public static Label highScore = new Label(String.valueOf(Game_Manager.highScore));
	public static Label highScoreLabel = new Label("High Score: ");
	public static Label bottomLabel = new Label(Tile.infoStr.toString());;
	public static Label score = new Label(String.valueOf(Tile.score));
	public static Label scoreLabel = new Label("Score: ");
	//labels for information of scores,highscore,level type,level
	
	public static BorderPane borderPaneMAIN;		//the main pane of the game that contains menu,infoUI,game,bottom label
	public static BorderPane bottomPane;			//bottom pane to hold infoStr label and clickable textflow of next game
	
	public static boolean isGameOver;				//a boolean value to check if game is over and reach it from other classes
	public static boolean isBombClicked;			//a boolean value to check if game is over by clicking bomb
	public static TextFlow loadGameTF = new TextFlow();
	public static TextFlow newGameTF = new TextFlow();
	public static TextFlow settingsGameTF = new TextFlow();
	public static TextFlow creditsTF = new TextFlow();
	//textflows for the top menuUI
	
	public static Stage loadStage;
	public static Stage gameStage;
	public static Stage mainmenuStage;
	public static Stage newGameStage;
	public static Stage creditsStage;
	public static Stage settingsStage;
	//stages kept in field in case if needed from other classes
	
	public void start(Stage primaryStage) throws Exception 
	{
		pane = Game_Manager.generateLevel("level1");				//generate a default level in order to don't get exception
		gameStage = new Stage();
		menuUI = Game_Manager.menuUI();
		
		bottomPane = new BorderPane();
		bottomPane.setLeft(bottomLabel);							//set bottompane's left the bottomLabel that contains infoStr
		
		borderPaneMAIN = new BorderPane();
		borderPaneMAIN.setBottom(bottomPane);;
	    borderPaneMAIN.setCenter(pane);
	    BorderPane infoUI = setInfoUI();
	    borderPaneMAIN.setTop(infoUI);
	    //adjust the main borderpane
	    
	    pane.prefHeightProperty().addListener((obs, oldVal, newVal) -> {gameStage.setHeight(newVal.doubleValue() + 62);
	    });
		pane.prefWidthProperty().addListener((obs, oldVal, newVal) -> {gameStage.setWidth(newVal.doubleValue()+3);});
		//add event which increases the heigth and width to game pane in case of if tile height or width changes
		
		Scene scene = new Scene(borderPaneMAIN);					//set scene of game stage
		
		gameStage.setTitle("BOMBOX");
		gameStage.setScene(scene);
		gameStage.resizableProperty().setValue(false);				//disable resizable property of game stage
		gameStage.setOnCloseRequest(k -> mainmenuStage.show());		//if game stage is closed, go back to main menu stage
		
		mainMenu();													//arrange the mainmenu fields and stage
		mainmenuStage.show();										//show mainmenu stage first
		mainmenuStage.resizableProperty().setValue(false);			//disable resizable property of mainmenu stage
	}
	public static void mainMenu()
	{
		Text newGameText = new Text("New Game");
		Text loadGameText = new Text("Load Game");
		Text settingsGameText = new Text("Settings");
		Text creditsText = new Text("Credits");
		newGameText.setFont(Font.font("Harlow Solid Italic", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		loadGameText.setFont(Font.font("Harlow Solid Italic", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		settingsGameText.setFont(Font.font("Harlow Solid Italic", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		creditsText.setFont(Font.font("Harlow Solid Italic", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		//set fonts and texts of info pane
		
		BorderPane startPane = new BorderPane();
		startPane.setPrefSize(900,500);
		
		ImageView iv = new ImageView(Image_Manager.mainMenuPic);
		HBox temp = new HBox();							//temporary HBox pane to set size
		temp.getChildren().add(iv);
		temp.setPrefSize(650, 500);
		//set image of left pane of main menu stage
		startPane.setLeft(temp);
		
		BorderPane rightPane = new BorderPane();
		ImageView iv2 = new ImageView(Image_Manager.logoPic);
		HBox temp2 = new HBox();						//temporary HBox pane to set size
		temp2.getChildren().add(iv2);
		temp2.setPrefSize(250, 200);
		//set the game's logo on the top of right :)
		
		GridPane gridPane = new GridPane();
		gridPane.setPrefSize(250, 300);				//adjust the rightpane's settings that below logo
		
		newGameTF.getChildren().add(newGameText);
		newGameTF.setStyle("-fx-background-color: #FFD6FF");
		newGameTF.setPadding(new Insets(35,70,35,75));
		//adjust the display of newGame tab
		newGameTF.setOnMouseClicked(e -> {
			newGameStage = new Stage();
			try {
				Scene sc = new Scene(new LevelPane());
				newGameStage.setScene(sc);
				newGameStage.show();
				newGameStage.setOnCloseRequest(k -> mainmenuStage.show());
				mainmenuStage.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//add event to open level pane when new game clicked, and close the main menu
		
		loadGameTF.getChildren().add(loadGameText);
		loadGameTF.setStyle("-fx-background-color: #E7C6FF");
		loadGameTF.setPadding(new Insets(35,70,35,75));
		//adjust the display of loadGame tab
		loadGameTF.setOnMouseClicked(e -> {
			loadStage = new Stage();
			try {
				Scene sc = new Scene(new LoadPane());
				loadStage.setScene(sc);
				loadStage.show();
				loadStage.setOnCloseRequest(k -> mainmenuStage.show());
				mainmenuStage.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//add event to open load pane when load game clicked, and close the main menu
		
		settingsGameTF.getChildren().add(settingsGameText);
		settingsGameTF.setStyle("-fx-background-color: #C8B6FF");
		settingsGameTF.setPadding(new Insets(35,100,35,85));
		//adjust the display of settingsGame tab
		settingsGameTF.setOnMouseClicked(e -> {
			settingsStage = new Stage();
			try {
				Scene sc = new Scene(new SettingsPane());
				settingsStage.setScene(sc);
				settingsStage.show();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//add event to open settings pane when settings clicked, and close the main menu
		
		creditsTF.getChildren().add(creditsText);
		creditsTF.setStyle("-fx-background-color: #B9C0FF");
		creditsTF.setPadding(new Insets(35,100,35,90));
		//adjust the display of credits tab
		creditsTF.setOnMouseClicked(e -> {
			creditsStage = new Stage();
			try {
				Scene sc = new Scene(new CreditsPane());
				creditsStage.setScene(sc);
				creditsStage.show();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//add event to open credits pane when credits clicked, and close the main menu
		
		gridPane.add(newGameTF, 0, 0);
		gridPane.add(loadGameTF, 0, 1);
		gridPane.add(settingsGameTF, 0, 2);
		gridPane.add(creditsTF, 0, 3);
		//add the set textflows to gridpane
		
		rightPane.setTop(temp2);
		rightPane.setBottom(gridPane);
		//adjust the rightpane
		
		startPane.setRight(rightPane);
		//add the rightpane to mainpane's right
		
		Scene scene = new Scene(startPane);
		Stage stage = new Stage();
		stage.setScene(scene);
		mainmenuStage = stage;
		//set scene and adjust the field's mainmenustage
	}
	public static BorderPane setInfoUI()
	{
		levelLabel.setFont(Font.font("Bahnschrift Semibold", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
		bombLabel.setFont(Font.font("Bahnschrift Semibold", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 12));
		bombLabel.setTextFill(Color.web("#9C3033"));
		highScoreLabel.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 17));
		highScore.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 17));
		highScore.setTextFill(Color.web("#C88D8F"));
		scoreLabel.setFont(Font.font("Bahnschrift Semibold", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
		score.setFont(Font.font("Bahnschrift Semibold", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
		//set fonts of infoUI
		
		BorderPane infoRightPane = new BorderPane();			//a borderpane to hold infoUI's right side
		HBox temp = new HBox();									//temporary HBox to keep highScore and label together
		temp.getChildren().add(highScoreLabel);
		temp.getChildren().add(highScore);
		HBox temp2 = new HBox();								//temporary HBox to keep score and label together
		temp2.getChildren().add(scoreLabel);
		temp2.getChildren().add(score);
		infoRightPane.setCenter(temp);							//add temporary HBoxes
		infoRightPane.setBottom(temp2); 

		
		BorderPane infoLeftPane = new BorderPane();				//a borderpane to hold infoUI's left side
		infoLeftPane.setTop(bombLabel);							//add bomb label to top of left
		infoLeftPane.setBottom(levelLabel);						//add level label to bottom of left
		
		BorderPane infoPane = new BorderPane();					//main info pane to combine the temporary borderpanes	
		infoPane.setLeft(infoLeftPane);
		infoPane.setCenter(reactionImage);						//set center reaction image that changes with the score values
		infoPane.setRight(infoRightPane);
		infoPane.setStyle("-fx-background-color: #D2DEDF");
		
		BorderPane borderPaneTop = new BorderPane();			//main TOP pane to hold infoUI and menuUI
		borderPaneTop.setBottom(infoPane);						//set topPane's bottom infoUI
		borderPaneTop.setTop(menuUI);							//set topPane's top menuUI
		borderPaneTop.setPrefHeight(30);
		
		
		return borderPaneTop;
	}
	public static boolean isGameOver()
	{
		//check whether game is over or not by checking the tiles and if bomb clicked
		
		if (isBombClicked)
			return true;
		int temp = 0;
		for (int i = 0; i < Game_Manager.tileH; i++)
		{
			for (int j = 0; j < Game_Manager.tileW; j++)
			{
				if (Game_Manager.tile[i][j] instanceof MirrorTile || Game_Manager.tile[i][j] instanceof WoodTile) 
					temp++;
			}
		}
		if (temp == 0)
			return true;
		
		return false;
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
