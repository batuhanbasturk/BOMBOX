//Firat Bakici 150120029
//Batuhan basturk 150119035

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LevelPane extends BorderPane
{
	private static File folder =  new File("resources\\levels"); //folder of level files
	private static File[] listOfFiles;													 //file array that holds level files
	private static ArrayList<LevelCell> f = new ArrayList<>();							 //innerclass's array that holds level, level type and highscore
	public static ArrayList<String> levelList = new ArrayList<>();						 //arraylist that holds levels
	private static Button loadLevelButton = new Button("Load Level");					 //button to load level
	private static ListView<LevelCell> listView;										 //listview of levelcell to show list of levels
	private static ArrayList<Integer> highScoreList = new ArrayList<>();				 //arraylist of highscores
	public static ArrayList<Boolean> containsBomb = new ArrayList<>();					 //arraylist of leveltype
	private static ArrayList<Boolean> doneList = new ArrayList<>();
	private static ArrayList<Boolean> lockList = new ArrayList<>();
	
	public LevelPane() throws FileNotFoundException
	{
		//refresh the listview
		refresh();
		
		//if gameStage is open at the moment, the event set on loadlevel will just change the game's pane
		if (Game.gameStage.isShowing())
		{
			loadLevelButton.setOnMouseClicked(e -> {
				for (int i = 0; i < listOfFiles.length; i++) 
				{
					if (levelList.get(i).equals(listView.getSelectionModel().getSelectedItem().levelLabel.getText())) //find the selected level which matches in levellist
					{
								try {
									if(lockList.get(i) == true)
									{
										Scene tempsc = new Scene(new Pane(new Label("You can't")));
										Stage st = new Stage();
										st.setScene(tempsc);
										st.show();
									}
									else
									{
										Game_Manager.generateLevel(levelList.get(i));				//generate game's pane by level parameter
										Game.pane.setPrefWidth(Game_Manager.tileW*50 + 8);			//adjust width and height if tileH and tileW change
										Game.pane.setPrefHeight(Game_Manager.tileH*50 + 59);
										
										Game.score.setText(String.valueOf(0));
										Tile.score = 0;
										
										Game.bombLabel.setText(containsBomb.get(i) == true ? "  Bomb" : "  Normal");	//set the game's bomb label if leveltype contains bomb
										Game.menuUI.setPrefWidth(Game_Manager.tileW*50 + 8);
									}
									
								} catch (Exception e1) {
									e1.printStackTrace();
								}	
					}
				}
			});
		}
		//if gameStage is closed at the moment, the event set on loadlevel will 
		//change the game's pane and after that show the game stage
		else
		{
			loadLevelButton.setOnMouseClicked(e -> {
				for (int i = 0; i < listOfFiles.length; i++) 
				{
					if (levelList.get(i).equals(listView.getSelectionModel().getSelectedItem().levelLabel.getText())) 
					{
								try {
									if(lockList.get(i) == true)
									{
										Scene tempsc = new Scene(new Pane(new Label("You can't")));
										Stage st = new Stage();
										st.setScene(tempsc);
										st.show();
									}
									else
									{
										Game_Manager.generateLevel(levelList.get(i));							//generate game's pane by level parameter
										Game.pane.setPrefWidth(Game_Manager.tileW*50 + 8);						//adjust width and height if tileH and tileW change
										Game.pane.setPrefHeight(Game_Manager.tileH*50 + 59);
										
										Game.bombLabel.setText(containsBomb.get(i) == true ? "  Bomb" : "  Normal"); //set the game's bomb label if leveltype contains bomb
										Game.menuUI.setPrefWidth(Game_Manager.tileW*50 + 8);
										
										
										//show game stage and close the level pane itself when loadlevel button clicked
										Game.gameStage.show();					
										Game.newGameStage.close();
									}
									
								} catch (Exception e1) {
									e1.printStackTrace();
								}	
					}
				}
			});
		}
		//temporary borderpane to hold load level button and label at the bottom
		BorderPane temporaryPane = new BorderPane();
		temporaryPane.setLeft(loadLevelButton);
		temporaryPane.setCenter(new Label("Please change setting tabs after loading"));
		
		//adjust the pane's width and height 
		setMinWidth(450);
		setMinHeight(350);
		
		//arrange the pane's on the actual borderpane which is levelPane who extends borderpane
		setCenter(listView);
		setBottom(temporaryPane);
	}
	public static void refresh() throws FileNotFoundException
	{
		//clear the levelcell array in order to not stack them every time the levelpane opened
		f.clear();
		
		listOfFiles = folder.listFiles();						//find the files in folder and store it
		doneList.add(true);										//add initialy true for level 1

		int i = 0;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				//add levels in folder to levellist
				levelList.add(file.getName().substring(0, file.getName().indexOf(".txt")));
				try (Scanner scanner = new Scanner(file)) 
				{
					while (scanner.hasNext()) 
					{
						String checker = scanner.nextLine();
						
						if (checker.contains("-")) //check if file has a line such as "10x10-normal"
						{
							if (checker.substring(checker.indexOf("-") + 1).equals("normal"))
							{
								containsBomb.add(false);					//if checker doesn't contains bomb, add false to containsBomb
								continue;
							}
							else if (checker.substring(checker.indexOf("-") + 1).equals("bomb"))
							{
								containsBomb.add(true);						//if checker contains bomb, add true to containsBomb in order to show in listview
								continue;
							}	
						}
						else if (checker.contains("highscore"))
						{
							//if level file doesn't contain "-", set false to containsbomb as default
							try {containsBomb.get(i);}
							catch(IndexOutOfBoundsException e){containsBomb.add(false);}
							//if checker contains highscore, add to highscoreList to show in listview
							highScoreList.add(Integer.parseInt(checker.substring(9)));
							continue;
						}
						else if (checker.contains("done"))
						{
							//if level file doesn't contain "-", set false to containsbomb as default
							try {containsBomb.get(i);}
							catch(IndexOutOfBoundsException e){containsBomb.add(false);}
							//if level file doesn't contain "highscore", set 0 to highscore as default
							try {highScoreList.get(i);}
							catch(IndexOutOfBoundsException e){highScoreList.add(0);}
							
							doneList.add(true);	//if checker contains "done", add true to done list
							continue;
						}
						else
						{
							//if level file doesn't contain "-", set false to containsbomb as default
							try {containsBomb.get(i);}
							catch(IndexOutOfBoundsException e){containsBomb.add(false);}
							//if level file doesn't contain "highscore", set 0 to highscore as default
							try {highScoreList.get(i);}
							catch(IndexOutOfBoundsException e){highScoreList.add(0);}
							
							//if there is no "done" in file, add false to doneList
							try
							{
								doneList.get(i+1);
							}
							catch(Exception e)
							{
								doneList.add(false);
							}
							
							//if the current doneList is true or not, add locklist whether it's value
								if (doneList.get(i) == true)
								{
									lockList.add(false);
								}
								else
								{
									lockList.add(true);
								}
							
							
							
							break;
						}
					}
				} 

				//add the files to	levelcell array f
				f.add(new LevelCell(levelList.get(i), containsBomb.get(i), highScoreList.get(i),lockList.get(i)));
				
				i++;
			}
		}

		
        List<LevelCell> list = new ArrayList<>();				//temporary list to create a observablelist
        for (int k = 0; k < f.size(); k++) {
             list.add(f.get(k));								//add the savepanecells to temporary list
        }
        
        //adjust the listview
        listView = new ListView<LevelCell>();
        ObservableList<LevelCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);
        
	}
	static class LevelCell extends BorderPane {
		//inner class to show player level's information
		
        Label levelLabel = new Label();						//level label to keep level
        Label highScoreLabel = new Label();					//highscore label to keep highscore of that level
        ImageView bombImage = new ImageView(Image_Manager.bombImage);	//imageview of bomb
        ImageView lockImage = new ImageView(Image_Manager.lockImage);	//imageview of lock
        BorderPane tempCenter = new BorderPane();			//temp pane for if the level is contains bomb and locked at the same time

        public LevelCell(String saveName, boolean isBomb, int highScore,boolean isLocked) {
        	//set fonts of labels
        	levelLabel.setFont(new Font("Arial",16));
        	highScoreLabel.setFont(new Font("Arial",16));
        	
        	//set texts of labels
        	levelLabel.setText(saveName);
        	highScoreLabel.setText("High Score: " + String.valueOf(highScore));
        	
        	tempCenter.setMaxWidth(100);
        	
        	//set size of each cell
            this.setPrefSize(120, 33);
            this.setLeft(levelLabel);
            if (isBomb)										//set cell's left levelLabel, center bombImage(if it has bomb) and lock(if it has lock)
            	tempCenter.setLeft(bombImage);				//right highscoreLabel
            if(isLocked)									
            	tempCenter.setRight(lockImage);
            
            this.setCenter(tempCenter);
            this.setRight(highScoreLabel);
            
            //set the color of cell
            setStyle("-fx-background-color: #E8E654;");
            
        }
    }
}
