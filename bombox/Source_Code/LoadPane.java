//Firat Bakici 150120029
//Batuhan basturk 150119035

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class LoadPane extends BorderPane {
	
	//in order to keep organized, loadpane get's the files from savePane
	
	private Button loadButton = new Button("Load Game");

	public LoadPane() throws Exception {
		//refresh the savePane with the given color hash to look like load pane is another listview of files
		SavePane.refresh("8E2DB6");
		
		//if there's not any selected item in listview, then make loadbutton unclickable
		loadButton.disableProperty().bind(SavePane.getListView().getSelectionModel().selectedItemProperty().isNull());
		
		//if gameStage is open at the moment, the event set on loadbutton will just change the game's pane
		if (Game.gameStage.isShowing())
		{
			loadButton.setOnMouseClicked(e -> {
				for (int i = 0; i < SavePane.getListOfFiles().length; i++) 
				{
					//find the selected save which matches in savelist of SavePane
					if (SavePane.getSaveList()[i].equals(SavePane.getListView().getSelectionModel().getSelectedItem().saveLabel.getText())) 
					{
								try {
									Game_Manager.generateLevel(Game_Manager.loadGenerateSaveArray(SavePane.getSaveList()[i]));	//generate game's pane by loadGenerateSaveArray's array
									Game.pane.setPrefWidth(Game_Manager.tileW*50 + 8);                  //adjust width and height if tileH and tileW change
									Game.pane.setPrefHeight(Game_Manager.tileH*50 + 59);
									
									Game.bombLabel.setText(SavePane.containsBomb[i] == true ? "  Bomb" : "  Normal");         //set the game's bomb label if leveltype contains bomb
									Game.menuUI.setPrefWidth(Game_Manager.tileW*50 + 8);
								} catch (Exception e1) {
									e1.printStackTrace();
								}	
					}
				}
			});
		}
		//if gameStage is closed at the moment, the event set on loadbutton will 
		//change the game's pane and after that show the game stage
		else
		{
			loadButton.setOnMouseClicked(e -> {
				for (int i = 0; i < SavePane.getListOfFiles().length; i++) 
				{
					//find the selected save which matches in savelist of SavePane
					if (SavePane.getSaveList()[i].equals(SavePane.getListView().getSelectionModel().getSelectedItem().saveLabel.getText())) 
					{
								try {
									Game_Manager.generateLevel(Game_Manager.loadGenerateSaveArray(SavePane.getSaveList()[i]));	//generate game's pane by loadGenerateSaveArray's array
									Game.pane.setPrefWidth(Game_Manager.tileW*50 + 8);			//adjust width and height if tileH and tileW change
									Game.pane.setPrefHeight(Game_Manager.tileH*50 + 59);
									
									Game.bombLabel.setText(SavePane.containsBomb[i] == true ? "  Bomb" : "  Normal");		//set the game's bomb label if leveltype contains bomb
									Game.menuUI.setPrefWidth(Game_Manager.tileW*50);
									
									//show game stage and close the load pane itself when load button clicked
									Game.gameStage.show();
									Game.loadStage.close();
								} catch (Exception e1) {
									e1.printStackTrace();
								}	
					}
				}
			});
		}
		//temporary borderpane to hold load level button and label at the bottom
		BorderPane temporaryPane = new BorderPane();
		temporaryPane.setLeft(loadButton);
		temporaryPane.setCenter(new Label("Please change setting tabs after loading"));
		
		//adjust the pane's width and height 
		setMinWidth(450);
		setMinHeight(350);
		
		//arrange the pane's on the actual borderpane which is loadPane who extends borderpane
		setCenter(SavePane.getListView());
		setBottom(temporaryPane);
	}
}
