//Firat Bakici 150120029
//Batuhan basturk 150119035

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public abstract class Tile extends Pane
{
	//Tile is an abstract class that the other tile's extends it
	
	public final double k = 49.6;
	public final double l = 49.6;
	protected int pointOfTile;							//point of tile that inherits to other tiles in order to get point when clicked
	public static int destroyCount = 0;					//destroy count to see how much boxes destroyed
	
	public static StringBuilder infoStr = new StringBuilder("--Text--");	//StringBuilder to see which boxes destroyed in the label below game pane
	public static int highScore = 0;					//highscore to save highest score 
	public static int score = 0;						//score to keep the score at the moment
	
	int i = 0;											//initialize i in field in order to get in curly brackets of events
	public Tile()
	{
		//adjust the tile(s) size
		this.setPrefSize(l,k);
		this.setMaxHeight(k);
		this.setMaxWidth(l);
		this.setMinHeight(k);
		this.setMinWidth(l);
		
		//add event to tile when clicked
			this.setOnMouseClicked(e -> {
				//get the column and row index of which node clicked
				Node source = (Node)e.getSource();
				Integer colIndex = GridPane.getColumnIndex(source);
				Integer rowIndex = GridPane.getRowIndex(source);
				System.out.println(colIndex + " " + rowIndex);
				
				//clear stringbuilder and add the information of where the box clicked
				infoStr.setLength(0);
				infoStr.append("Box:" + rowIndex + "-" + colIndex);
				
				//while the game is not over, call the important methods such as destroying the boxes
				if (Game.isGameOver == false)
				{
					//call the method1 by the parameter of the box clicked to destroy the surrounding boxes
					method1(colIndex,rowIndex);
					
					//switch case for destroy count in order to change infoStr,reactionImage and score
					switch (destroyCount)
					{
						case 5:
						{
							infoStr.append("(+4 points)");
							Game.reactionImage.setImage(Image_Manager.lovelyImage);
							score += 4;
							break;
						}
						case 4:
						{
							infoStr.append("(+2 points)");
							Game.reactionImage.setImage(Image_Manager.happyImage);
							score += 2;
							break;
						}
						case 3:
						{
							infoStr.append("(+1 points)");
							Game.reactionImage.setImage(Image_Manager.huhImage);
							score += 1;
							break;
						}
						case 2:
						{
							infoStr.append("(-1 points)");
							Game.reactionImage.setImage(Image_Manager.nothappyImage);
							score -= 1;
							break;
						}
						case 1:
						{
							infoStr.append("(-3 points)");
							Game.reactionImage.setImage(Image_Manager.sadImage);
							score -= 3;
							break;
						}
					}
					//if bomb gets clicked, change the reactionImage
					if(Game.isBombClicked) 	Game.reactionImage.setImage(Image_Manager.deadImage);
					
					//set the highscore to highest every time
					if(Game_Manager.highScore < score) Game_Manager.highScore = score;
					
					//set highscore,score,bottom label of game
					Game.highScore.setText(String.valueOf(Game_Manager.highScore) + "  ");
					Game.score.setText(String.valueOf(score));
					Game.bottomLabel.setText(infoStr.toString());
				}
				Game.isGameOver = Game.isGameOver();		// check is game over every time clicked
				TextFlow tf = new TextFlow(new Text("Next Level >>>> CLICK HERE !!!!!!!!"));	//textflow to add event to pass other level
				
				if (Game.isGameOver && Game_Manager.currentFileName.startsWith("level") && !Game.isBombClicked)
				{
					//if game is over and the game is loaded from the level files
					//update the level by it's highscore if it's changed
					Game_Manager.saveLevel(Game_Manager.currentLevel);
					
					i = 0;	//set i = 0 to use in for loop
					
					//a textflow in order to get to the next level
					for( ; i < LevelPane.levelList.size(); i++)
					{
						//find the matching level in levelpane's levellist
						if(Game_Manager.currentLevel.equals(LevelPane.levelList.get(i)))
						{
							try {
								LevelPane.levelList.get(i+1);	//check if this level is the last level
								Game.bottomPane.setRight(tf);	//set right of bottompane of game clickable textflow
							} 
							catch (Exception e2) {
								//if the level that is done is last level, prompt a message
								Scene sc = new Scene(new Pane(new Label("THAT WAS FINAL LEVEL")));
								Stage st = new Stage();
								st.setScene(sc);
								st.show();
							}
							tf.setOnMouseClicked(k -> {
									try {
										Game_Manager.generateLevel(LevelPane.levelList.get(i+1));	//generate the next level
										Game.pane.setPrefWidth(Game_Manager.tileW*50 + 8);			//adjust width and height if tileH and tileW change
										Game.pane.setPrefHeight(Game_Manager.tileH*50 + 59);
										
										Game.score.setText(String.valueOf(0));						//set score to 0
										Tile.score = 0;
										
										Game.bombLabel.setText(LevelPane.containsBomb.get(i+1) == true ? "  Bomb" : "  Normal");	//set the game's bomb label if leveltype contains bomb
										Game.menuUI.setPrefWidth(Game_Manager.tileW*50 + 8);
										//after clicking, set bottom pane's right null
										Game.bottomPane.setRight(null);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
									
							});
							break;
						}
					}
				}
				else if (Game.isGameOver && !Game_Manager.currentFileName.startsWith("level") && !Game.isBombClicked)
				{
					//if game is over and the game is loaded from the save files
					//update the level file by the highscore if it's changed
					Game_Manager.saveLevel(Game_Manager.currentLevel, Game_Manager.highScore);
					
					//a textflow in order to get to the next level
					
					i = 0;	//set i = 0 to use in for loop
					
					//a textflow in order to get to the next level
					for( ; i < LevelPane.levelList.size(); i++)
					{
						//find the matching level in levelpane's levellist
						if(Game_Manager.currentLevel.equals(LevelPane.levelList.get(i)))
						{
							try {
								LevelPane.levelList.get(i+1);	//check if this level is the last level
								Game.bottomPane.setRight(tf);	//set right of bottompane of game clickable textflow
							} 
							catch (Exception e2) {
								//if the level that is done is last level, prompt a message
								Scene sc = new Scene(new Pane(new Label("THAT WAS FINAL LEVEL")));
								Stage st = new Stage();
								st.setScene(sc);
								st.show();
							}
							tf.setOnMouseClicked(k -> {
									try {
										Game_Manager.generateLevel(LevelPane.levelList.get(i+1));	//generate the next level
										Game.pane.setPrefWidth(Game_Manager.tileW*50 + 8);			//adjust width and height if tileH and tileW change
										Game.pane.setPrefHeight(Game_Manager.tileH*50 + 59);
										
										Game.score.setText(String.valueOf(0));						//set score to 0
										Tile.score = 0;
										
										Game.bombLabel.setText(LevelPane.containsBomb.get(i+1) == true ? "  Bomb" : "  Normal");	//set the game's bomb label if leveltype contains bomb
										Game.menuUI.setPrefWidth(Game_Manager.tileW*50 + 8);
										//after clicking, set bottom pane's right null
										Game.bottomPane.setRight(null);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
									
							});
							break;
						}
					}
				}
				
			});
	}
	protected void method1(int c, int r)
	{
		//first method on clicked box to apply the destroying to surrounding boxes
		
		destroyCount = 0;								//initalize destroycount as 0 every time a box clicked
		if(Game.isGameOver == false)
			method2(c,r);
		
			Game.isGameOver = Game.isGameOver();		//check is game over every time when method is called
			
		if(Game.isGameOver == false) method2(c+1,r);
			Game.isGameOver = Game.isGameOver();
		if(Game.isGameOver == false) method2(c,r+1);
			Game.isGameOver = Game.isGameOver();
		if(Game.isGameOver == false) method2(c,r-1);
			Game.isGameOver = Game.isGameOver();
		if(Game.isGameOver == false) method2(c-1,r);
			Game.isGameOver = Game.isGameOver();

	} 
	protected void method2(int c, int r)
	{
		//check the tile is destroyable
		if(!checkTileEmpty(c,r))
		{
			updateTile(c,r);			//update the tile by it's point
			destroyCount++;				//update the destroycount
			infoStr.append("- Hit: " + r + "," + c + " ");	//update the infoStr by the hit box
		}
			
	}
	protected boolean checkTileEmpty(int c,int r)
	{
		//checks if the given parameter of tile destroyable
		try
		{
			if (Game_Manager.tile[r].length - c < 1 || Game_Manager.tile[r][c] instanceof EmptyTile || Game_Manager.tile[r][c] instanceof WallTile || Game_Manager.tile.length - r < 1
					)
				return true;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return true;
		}
		return false;
	}
	protected void updateTile(int c, int r)
	{
		//updates the tile if it's destroyable by it's point 
		switch(Game_Manager.tile[r][c].pointOfTile)
		{
			case 2:
			{
				System.out.println(Game_Manager.tile[r][c].getWidth() + " " + Game_Manager.tile[r][c].getHeight());
				Game_Manager.tile[r][c] = new MirrorTile();				//if the point is 2(walltile) update it to mirrortile
				Game.pane.add(Game_Manager.tile[r][c], c, r);			//add it to game's pane
				break;
			}
			case 1:
			{
				System.out.println(Game_Manager.tile[r][c].getWidth() + " " + Game_Manager.tile[r][c].getHeight());
				Game_Manager.tile[r][c] = new EmptyTile();				//if the point is 1(mirrortile) update it to emptytile
				Game.pane.add(Game_Manager.tile[r][c], c, r);			//add it to game's pane
				break;
			}
			case 0:
			{
				Game_Manager.tile[r][c] = new EmptyTile();				//if the point is 0(bombtile) update it to emptytile
				Game.pane.add(Game_Manager.tile[r][c], c, r);			//add it to game's pane
				Game.isBombClicked = true;								//update the isbombclicked since the point of this tile is 0(bombtile)
				break;
			}
		}
	}
}
