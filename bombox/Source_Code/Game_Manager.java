//Firat Bakici 150120029
//Batuhan basturk 150119035

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game_Manager 
{

	public static String currentFileName; //created for to check whether it's loaded from save or not
	public static String currentLevel = "level1"; //a default value in order to don't get exception
	public static int tileW = 10; //while loading saves/levels with greater than 10 height/rows, gets updated in methods
	public static int tileH = 10;
	public static String levelType = "normal"; //a default value in order to don't get exception
	public static int highScore = 0;
	
	private static String[] settingTitles = {"New Game","Load Game","Save Game","Tile Color","About Us"}; //in-game settings list
	//binded to Game.pane
	public static GridPane pane = new GridPane();
	public static Tile[][] tile = new Tile[10][10]; //tiles of game's pane
	
	private static Pane fieldPane; // the right side pane of settingUI
	private static BorderPane borderPaneUI; //the general settingUI's pane in order to keep organized
	private static ListView<String> lv; //in-game settings listview
	
	//generate level with given "level" value
	public static GridPane generateLevel(String level) throws Exception
	{
		Game.isGameOver = false;
		Game.isBombClicked = false;
		//since if the game is over you can't save, set their value to false when loading a level/save
		
		pane.getChildren().clear();
		//pane is not just 10x10, so the array that comes with below method generateLevelArray may contain 15x20 pane
		//to handle it, clear the pane
		
		ArrayList<String> k = generateLevelArray(level);
		//call the generateLevelArray to get tiles from file
		
		for (int i = 0; i < k.size()-2; i+=3)
		{
			switch(k.get(i))
			{
				case "Empty":{
					pane.add(tile[Integer.parseInt(k.get(i+1))][Integer.parseInt(k.get(i+2))] = new EmptyTile(), Integer.parseInt(k.get(i+2)), Integer.parseInt(k.get(i+1)));
					break;
					}
				case "Mirror":{
					pane.add(tile[Integer.parseInt(k.get(i+1))][Integer.parseInt(k.get(i+2))] = new MirrorTile(), Integer.parseInt(k.get(i+2)), Integer.parseInt(k.get(i+1)));
					break;
					}
				case "Wood":{
					pane.add(tile[Integer.parseInt(k.get(i+1))][Integer.parseInt(k.get(i+2))] = new WoodTile(), Integer.parseInt(k.get(i+2)), Integer.parseInt(k.get(i+1)));
					break;
					}
				default:{
					pane.add(tile[Integer.parseInt(k.get(i+1))][Integer.parseInt(k.get(i+2))] = new BombTile(), Integer.parseInt(k.get(i+2)), Integer.parseInt(k.get(i+1)));
					break;
					}
			}
		}
		// fill the pane while initalizing tiles
		
		fillBlanks(tileH,tileW);
		//fill the blank tile's with emptytile
		
		return pane;
	}
	
	//Overload for saves
	public static void generateLevel(ArrayList<String> save) throws Exception
	{
		Game.isGameOver = false;
		Game.isBombClicked = false;
		//since if the game is over you can't save, set their value to false when loading a level/save
		
		pane.getChildren().clear();
		//pane is not just 10x10, so the array that comes with below method generateLevelArray may contain 15x20 pane
		//to handle it, clear the pane
		
		ArrayList<String> k = save;
		for (int i = 0; i < k.size()-2; i+=3)
		{
			switch(k.get(i))
			{
				case "Empty":{
					pane.add(tile[Integer.parseInt(k.get(i+1))][Integer.parseInt(k.get(i+2))] = new EmptyTile(), Integer.parseInt(k.get(i+2)), Integer.parseInt(k.get(i+1)));
					break;
					}
				case "Mirror":{
					pane.add(tile[Integer.parseInt(k.get(i+1))][Integer.parseInt(k.get(i+2))] = new MirrorTile(), Integer.parseInt(k.get(i+2)), Integer.parseInt(k.get(i+1)));
					break;
					}
				case "Wood":{
					pane.add(tile[Integer.parseInt(k.get(i+1))][Integer.parseInt(k.get(i+2))] = new WoodTile(), Integer.parseInt(k.get(i+2)), Integer.parseInt(k.get(i+1)));
					break;
					}
				default:{
					pane.add(tile[Integer.parseInt(k.get(i+1))][Integer.parseInt(k.get(i+2))] = new BombTile(), Integer.parseInt(k.get(i+2)), Integer.parseInt(k.get(i+1)));
					break;
					}
			}
		}
		// fill the pane while initalizing tiles
		
		fillBlanks(tileH,tileW);
		//fill the blank tile's with emptytile
	}
	
	public static void fillBlanks(int o, int p)
	{
		//fill the wmpty tiles with wallTile
		for (int i = 0; i < o; i++)
			for (int j = 0; j < p; j++)
			{
				if(tile[i][j] == null)
				pane.add(tile[i][j] = new WallTile(), j, i);
			}
		
	}
	public static ArrayList<String> generateLevelArray(String level) throws Exception
	{
		//generate arraylist for level files
		
		if (level.startsWith("level"))
		{
			try(Scanner scanner = new Scanner(new File("resources\\levels\\" + level + ".txt")))
			{
				if(!scanner.hasNext())
				{
					throw new Exception("An error has occupied. Check files..");
				}
				currentFileName = level;
				currentLevel = level;
				//update the level and fileName
				
				ArrayList<String> parts = new ArrayList<String>();
				//a temporary arrayList to return
				
				while(scanner.hasNext())
				{
					String checker = scanner.nextLine();
					if(checker.length() == 0)
						continue;							//if the line is empty, skip
					if (Character.isDigit(checker.charAt(0)))
					{
						arrangeFields(checker);				//adjust tile height/width and leveltype(bomb-normal)
						continue;
					}
					if(checker.length() >= 9 && checker.substring(0,9).equals("highscore"))
					{
						highScore = Integer.parseInt(checker.substring(9));
						Game.highScore.setText(String.valueOf(highScore));		//update highscore and highscore label of game
						continue;
					}
					if(checker.contains("done"))
					{
						continue;
					}
					String[] temp = checker.split(",");
					parts.add(temp[0]);
					parts.add(temp[1]);					//if the line contains such an order like Empty,3,5 , split and add to arrayList
					parts.add(temp[2]);
				}
				//if there is no highscore in current level file, set it to 0 with getLevelHighScore
				//and update highscore label of game's
				highScore = Integer.parseInt(getLevelHighScore(currentLevel));
				Game.highScore.setText(String.valueOf(highScore));
				//reset tiles in order to arrange pane again
				//set game's level label
				Game.levelLabel.setText("  " + currentLevel);
				//update level label of game's
				tile = new Tile[tileH][tileW];
				//clear the tiles in order to generate again
				return parts;
			}
			
		}
		return null;
	}
	public static void arrangeFields(String line)
	{
		//adjust tile height/width and leveltype(bomb-normal)
		tileH = Integer.parseInt(line.substring(0,line.indexOf("x")));
		tileW = Integer.parseInt(line.substring(line.indexOf("x")+1,line.indexOf("-")));
		levelType = line.substring(line.indexOf("-")+1);
	}
	public static void inGameSettings(int index)
	{
		// set in-game setting's pane-UI
		// given index is been used to choose a default value when clicked on menu items
		
		lv = new ListView<>(FXCollections.observableArrayList(settingTitles));
		lv.setPrefSize(200, 150);
		lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//set title's of listview - adjust the size of left pane(list) - and make the user choose just 1 option
		
		fieldPane = new BorderPane();
		borderPaneUI = new BorderPane();
		borderPaneUI.setLeft(new ScrollPane(lv));
		borderPaneUI.setRight(fieldPane);
		lv.getSelectionModel().select(index);
		listViewArrangement();
		
		lv.getSelectionModel().selectedItemProperty().addListener(k ->
			{
				System.out.println(lv.getSelectionModel().getSelectedItem());
				listViewArrangement();
		    }
		);
		//adjusts to set the right pane(fieldPane) 
		
		//adjust in-game setting's stage
		Scene scene = new Scene(borderPaneUI);
		Stage stageIGUI = new Stage();
		stageIGUI.setMinHeight(500);
		stageIGUI.setMinWidth(650);
		stageIGUI.setScene(scene);
		stageIGUI.show();
		
	}
	
	// in order to keep organized the above method 
	private static void listViewArrangement()
	{
		// arrange the right pane of in-game setting's borderpane
		// clear the fieldPane(right pane) before setting it.
		
		if(lv.getSelectionModel().getSelectedItem() == "New Game")
		{
			fieldPane.getChildren().clear();
			try {
				((BorderPane) fieldPane).setCenter(new LevelPane());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(lv.getSelectionModel().getSelectedItem() == "Load Game")
		{
			fieldPane.getChildren().clear();
			try {
				((BorderPane) fieldPane).setCenter(new LoadPane());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(lv.getSelectionModel().getSelectedItem() == "Save Game" )
		{
			fieldPane.getChildren().clear();
			try {
				((BorderPane) fieldPane).setCenter(new SavePane());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if(lv.getSelectionModel().getSelectedItem() == "About Us" )
		{
			fieldPane.getChildren().clear();
			((BorderPane) fieldPane).setCenter(new CreditsPane());
		}
		if(lv.getSelectionModel().getSelectedItem() == "Tile Color" )
		{
			fieldPane.getChildren().clear();
			((BorderPane) fieldPane).setCenter(new SettingsPane());
		}
	}
	public static MenuBar menuUI()
	{
		//creating a menuBar to get a shortcut to access in-game settings
		
		Menu fileMenu = new Menu("File");

		MenuItem saveGameMenuItem = new MenuItem("Save Game");
		MenuItem newGameMenuItem = new MenuItem("New Game");
		MenuItem loadGameMenuItem = new MenuItem("Load Game");
		MenuItem exitMenuItem = new MenuItem("Exit");

		//set menuitem's and single menu's events (choose default values)
		
		newGameMenuItem.setOnAction(e -> Game_Manager.inGameSettings(0));
		saveGameMenuItem.setOnAction(e -> Game_Manager.inGameSettings(2));
		loadGameMenuItem.setOnAction(e -> Game_Manager.inGameSettings(1));
		exitMenuItem.setOnAction(e -> {
			Game.gameStage.close();
			Game.mainmenuStage.show();
		});
		
		Menu settingsMenu = new Menu();
		Label label2 = new Label("Settings");
		settingsMenu.setGraphic(label2);
		label2.setOnMouseClicked(e -> Game_Manager.inGameSettings(3));
		Menu creditsMenu = new Menu();
		Label label = new Label("About us");
		creditsMenu.setGraphic(label);
		label.setOnMouseClicked(e -> Game_Manager.inGameSettings(4));
		
		//set menuitem's and single menu's events (choose default values)
		
		fileMenu.getItems().add(newGameMenuItem);
		fileMenu.getItems().add(new SeparatorMenuItem());
		fileMenu.getItems().add(saveGameMenuItem);
		fileMenu.getItems().add(loadGameMenuItem);
		fileMenu.getItems().add(new SeparatorMenuItem());
		fileMenu.getItems().add(exitMenuItem);
		// give a better look to top pane(menuBar)
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, settingsMenu, creditsMenu); //add the menu's 
		
		return menuBar;
	}
	public static void saveLevel(String levelName)
	{
		//when the game ended, save the level with the default tiles and updated highscore
		
		File file = new File("resources\\levels\\" + levelName + ".txt");	//find the given level
		ArrayList<String> tempParts = new ArrayList<>();	//create a temporary arrayList to print it later below
		currentFileName = file.getName().substring(0, file.getName().indexOf(".txt"));
		currentLevel = file.getName().substring(0, file.getName().indexOf(".txt"));
		
		try(Scanner scanner = new Scanner(file);)		//create a scanner to read the given text file
		{
			while(scanner.hasNext())
			{
				String checker = scanner.nextLine();
				if(checker.length() == 0)
					continue;							   //skip if the line is empty
				if (Character.isDigit(checker.charAt(0)))
				{
					arrangeFields(checker);                //update the fields in order to print it later below
					continue;
				}
				if (checker.contains("highscore"))
				{
					continue;							   //since we'll update it, skip it
				}
				if (checker.contains("done"))
				{
					continue;
				}
				String[] temp = checker.split(",");
				tempParts.add(temp[0]);
				tempParts.add(temp[1]);					   //since we don't want to change level tiles, keep it safe in arraylist
				tempParts.add(temp[2]);
			}
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try(PrintWriter pw = new PrintWriter(file))
		{
			
			pw.println(tileH + "x" + tileW + "-" + levelType);		//print the updated fields.
			pw.println("highscore" + highScore);					//print the updated values(highscore)
			pw.println("done");
			pw.println();
			
			for (int i = 0; i < tempParts.size()-2; i+=3)
			{	
				//print the tiles written in temporary arraylist
				switch(tempParts.get(i))
				{
					case "Empty":{
						pw.print("Empty," + tempParts.get(i+1) + "," + tempParts.get(i+2) + "\n");
						break;
						}
					case "Mirror":{
						pw.print("Mirror," + tempParts.get(i+1) + "," + tempParts.get(i+2) + "\n");
						break;
						}
					case "Wood":{
						pw.print("Wood," + tempParts.get(i+1) + "," + tempParts.get(i+2) + "\n");
						break;
						}
					default:{
						pw.print("Bomb," + tempParts.get(i+1) + "," + tempParts.get(i+2) + "\n");
						break;
						}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("hata");
		}
	}
	
	//overload for loaded save files
	public static void saveLevel(String levelName, int highscore)
	{
		//when the game ended that loaded from the save, save the level with the default tiles and updated highscore
		
		File file = new File("resources\\levels\\" + levelName + ".txt");  	 //find the given level
		ArrayList<String> tempParts = new ArrayList<>();			//create a temporary arrayList to print it later below
		currentFileName = file.getName().substring(0, file.getName().indexOf(".txt"));
		currentLevel = file.getName().substring(0, file.getName().indexOf(".txt"));
		
		try(Scanner scanner = new Scanner(file);) 			//create a scanner to read the given text file
		{
			while(scanner.hasNext())
			{
				String checker = scanner.nextLine();
				if(checker.length() == 0)
					continue;								//skip if the line is empty
				if (Character.isDigit(checker.charAt(0)))
				{
					arrangeFields(checker);					//update the fields in order to print it later below
					continue;
				}
				if (checker.contains("highscore"))
				{
					continue;								//since we'll update it, skip it
				}
				if (checker.contains("done"))
				{
					continue;
				}
				String[] temp = checker.split(",");
				tempParts.add(temp[0]);
				tempParts.add(temp[1]);						//since we don't want to change level tiles, keep it safe in arraylist
				tempParts.add(temp[2]);
			}
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try(PrintWriter pw = new PrintWriter(file))
		{
			
			pw.println(tileH + "x" + tileW + "-" + levelType);		//print the updated fields.
			pw.println("highscore" + highScore);				    //print the updated values(highscore)
			pw.println("done");
			pw.println();
			
			for (int i = 0; i < tempParts.size()-2; i+=3)
			{
				//print the tiles written in temporary arraylist
				switch(tempParts.get(i))
				{
					case "Empty":{
						pw.print("Empty," + tempParts.get(i+1) + "," + tempParts.get(i+2) + "\n");
						break;
						}
					case "Mirror":{
						pw.print("Mirror," + tempParts.get(i+1) + "," + tempParts.get(i+2) + "\n");
						break;
						}
					case "Wood":{
						pw.print("Wood," + tempParts.get(i+1) + "," + tempParts.get(i+2) + "\n");
						break;
						}
					default:{
						pw.print("Bomb," + tempParts.get(i+1) + "," + tempParts.get(i+2) + "\n");
						break;
						}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("hata");
		}
	}
	public static void saveGame(String saveName)
	{
		//choose save file with given name(saveName)
		
		try(PrintWriter pw = new PrintWriter(new File("resources\\saves\\" + saveName + ".txt"));)
		{
			pw.println(currentLevel);									//print the level that keep in fields
			pw.println(tileH + "x" + tileW + "-" + levelType);			//print the fields that been arranged
			pw.println("score" + Tile.score);							//print the score in order to continue from the last score
			pw.println();
			for (int i = 0; i < tile.length; i++)
				for (int j = 0; j < tile[0].length; j++)
				{
					//check the tiles and print the left ones
					//Tile type, rowIndex, columnIndex
					if (tile[i][j] instanceof EmptyTile)
					{
						pw.println("Empty," + i + "," + j);
					}
					else if (tile[i][j] instanceof MirrorTile)
					{
						pw.println("Mirror," + i + "," + j);
					}
					else if (tile[i][j] instanceof WoodTile)
					{
						pw.println("Wood," + i + "," + j);
					}
					else if (tile[i][j] instanceof BombTile)
					{
						pw.println("Bomb," + i + "," + j);
					}
				}
		}
		catch(Exception e)
		{
			System.out.println("hata");
		}
	}
	public static ArrayList<String> loadGenerateSaveArray(String save) throws Exception
	{
		//generate an arraylist of the save when loaded with the given save parameter
		
		try(Scanner scanner = new Scanner(new File("resources\\saves\\" + save + ".txt")))
		{
			currentFileName = save;				//update the currentFileName in order to understand that it is not default level
			if(!scanner.hasNext())
			{
				throw new Exception("An error has occupied. Check files..");
			}
			ArrayList<String> parts = new ArrayList<String>();
			while(scanner.hasNext())
			{
				String checker = scanner.nextLine();
				if(checker.length() == 0)
					continue;							//skip if the line is empty
				if (checker.startsWith("level"))
				{
					currentLevel = checker;				//update the level
					continue;
				}
				if (Character.isDigit(checker.charAt(0)))
				{
					arrangeFields(checker);				//adjust tile height/width and leveltype(bomb-normal)
					continue;
				}
				if(checker.length() >= 5 && checker.substring(0,5).equals("score"))
				{
					Game.score.setText(String.valueOf(Integer.parseInt(checker.substring(5))));
					Tile.score = Integer.parseInt(checker.substring(5));		
					//update the score and update the score label of game's if save file has score
					continue;
				}
				String[] temp = checker.split(",");
				parts.add(temp[0]);
				parts.add(temp[1]);					//fill the arraylist to return it generate tiles methods
				parts.add(temp[2]);
			}
			//update the highscore if the given save's level has a highscore
			highScore = Integer.parseInt(getLevelHighScore(currentLevel));
			Game.highScore.setText(String.valueOf(highScore));
			
			//set game's level label
			Game.levelLabel.setText("  " + currentLevel);
			
			//reset tiles in order to arrange pane again
			tile = new Tile[tileH][tileW];
			return parts;
		}
	}
	public static String getLevelHighScore(String currentLevel) throws FileNotFoundException
	{
		//check if the given level parameter has a highscore or not, if not return 0
		
		File file = new File("resources\\levels\\" + currentLevel + ".txt");
		try(Scanner scanner = new Scanner(file);)
		{
			while(scanner.hasNext())
			{
				String checker = scanner.nextLine();
				if(checker.length() == 0)
					continue;
				if (checker.contains("highscore"))
				{
					return checker.substring(9);
				}
			}
		}
		return "0";
	}
}
