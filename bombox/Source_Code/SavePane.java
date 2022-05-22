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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SavePane extends BorderPane
{
	public static String[] saveList = new String[5];					//string array that holds save names
	private static String[] levelList = new String[5];					//string array that holds saves levels
	public static boolean[] containsBomb = new boolean[5];				//boolean array that hold if the saving file's level type is bomb
	private static File folder = new File("resources\\saves");	//folder of save files
	private static File[] listOfFiles;									//file array that holds saves
	private static SavePaneCell[] f = new SavePaneCell[5];				//innerclass's array that holds level, level type and name
	private static Button saveButton = new Button("New Save");			//button that saves the current game tiles
	
	private Label labelOfButton = new Label("Enter Save Name:");		//label and button after clicking new save button
	private Button saveButton2 = new Button("Save Game");
	
	private static ListView<SavePaneCell> listView;						//listview that holds savepanecells(inner class)
	private Button deleteButton = new Button("Delete File");			//button that deletes the selected save
	
	//getters for loadpane
	public static String[] getSaveList() {
		return saveList;
	}
	public static File[] getListOfFiles() {
		return listOfFiles;
	}
	public static ListView<SavePaneCell> getListView() {
		return listView;
	}
	
	public SavePane() throws FileNotFoundException 
	{
		//refresh listview with the given color (save's color)
		refresh("04B45F");	
		
		//in order to keep max save files count to 5, disable save button
		//and if game is over, can't save
        if (listOfFiles.length >= 5 || Game.isGameOver)
        {
        	saveButton.setDisable(true);
        }
        
        //set event for save button
        saveButton.setOnMouseClicked(e -> {
        	saveButton.setDisable(true);							//disable when clicked in order to don't spam
        	
        	BorderPane pane = new BorderPane();						//after clicking new save button, new stage is opened
        	GridPane temp = new GridPane();							//and these pane's are for to adjust the new stage
        	
        	TextField saveTextField = new TextField();
        	temp.add(labelOfButton, 2, 1);
        	temp.add(saveTextField, 4, 1);							//add gridpane buttons, textfield and final save button
        	temp.add(saveButton2, 3, 2);
        	
        	//adjust the padding to make better look
        	GridPane.setMargin(labelOfButton, new javafx.geometry.Insets(15, 15, 10, 15));
        	GridPane.setMargin(saveTextField, new javafx.geometry.Insets(15, 15, 10, 15));
        	
        	//add borderpane to gridpane
        	pane.setCenter(temp);
        	
        	//set temporary second scene with temporary second stage
        	Scene scene = new Scene(pane);
        	Stage st = new Stage();
        	st.setScene(scene);
        	
        	//add event to second stage's close request
        	st.setOnCloseRequest(k -> {
        		saveButton.setDisable(false);					//if second stage is closed, make the save button clickable
        	});
        	
        	//add event to second stage's button
        	saveButton2.setOnMouseClicked(k -> {
        		saveButton.setDisable(true);					//in order to prevent spam, disable saveButton, if you'd likte to save again change tabs
        		Game_Manager.saveGame(saveTextField.getText());	//call Game_Manager's saveGame method to save the game
        		st.close();										//close the second stage
        	});
        	
        	st.show();											//show the second stage
        });
        
        //bind the delete button clickable property to whether is there a selected item or not
        deleteButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        
        //add event to delete button
        deleteButton.setOnMouseClicked(e -> {
        	for (int i = 0; i < listOfFiles.length ; i++)
        	{
        		if (saveList[i].equals(listView.getSelectionModel().getSelectedItem().saveLabel.getText()))		//find the selected item in savelist
        		{
        			File temp = new File("resources\\saves\\" + saveList[i] + ".txt");
        			temp.delete();		//delete the file
        		}		
        	}
        	
        });
        
        //set width and height in order to fill in-game settingUI
        setMinWidth(450);
		setMinHeight(350);
		
        BorderPane temporaryPane = new BorderPane();		//temporary pane to hold save and delete button at the bottom of the main pane
        temporaryPane.setLeft(saveButton);
        temporaryPane.setRight(deleteButton);
        temporaryPane.setCenter(new Label("Please change setting tabs after deleting/saving"));
        
        //since SavePane extends BorderPane, we can easily set center and buttom 
        setCenter(listView);
        setBottom(temporaryPane);
	}
	// tried to refresh listview while Save Pane is selected in-game but we couldn't
	// but we loacated the method in there in order to keep organized
	// and made static to reach from loadPane
	public static void refresh(String colorHash) throws FileNotFoundException
	{
		listOfFiles = folder.listFiles();							//find the files in folder and store it
		f = new SavePaneCell[listOfFiles.length];					//dynamically hold listoffiles by it's length
		
		int i = 0;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				if (i == 5) {
					Scene scene = new Scene(new Pane(new Label("You can save up to 5 files!\nPlease delete unnecessary files")));
					saveButton.setDisable(true);	//disable the save button if there are more than 5 files.
					Stage stage = new Stage();		//a temporary error stage if there are more than 5 files.
					stage.setScene(scene);
					stage.show();
					break;
				}
				else
					saveButton.setDisable(false);	//enable the save button if there is no problem.
				
				//add saves in folder to savelist
				saveList[i] = file.getName().substring(0, file.getName().indexOf(".txt"));
				
				try (Scanner scanner = new Scanner(file)) 
				{
					//scan the files in case of if it has level/level type
					while (scanner.hasNext()) 
					{
						String checker = scanner.nextLine();
						if (checker.contains("level"))
						{
							levelList[i] = checker;					//if checker contains level, add it to levellist to show in listview
						}
						if (checker.contains("-")) 
						{
							if (checker.substring(checker.indexOf("-") + 1).equals("normal"))
							{
								containsBomb[i] = false;			//if checker doesn't contains bomb, add false to containsBomb
								break;
							}
							else if (checker.substring(checker.indexOf("-") + 1).equals("bomb"))
							{
								containsBomb[i] = true;				//if checker contains bomb, add it to containsbomb to show in listview
								break;
							}
								
						}
					}
				} 
				//add the files to savepanecell array f
				f[i] = new SavePaneCell(saveList[i], containsBomb[i], levelList[i], colorHash);

				i++;
			}
		}

        List<SavePaneCell> list = new ArrayList<>();			//temporary list to create a observablelist
        for (int k = 0; k < f.length; k++) {
             list.add(f[k]);									//add the savepanecells to temporary list
        }
        
        //adjust the listview
        listView = new ListView<SavePaneCell>();
        ObservableList<SavePaneCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);
        
	}
	static class SavePaneCell extends BorderPane {
		//inner class to show player save's information
		
        Label saveLabel = new Label();							//save label to keep save's name
        Label levelLabel = new Label();							//level label to keep level
        ImageView bombImage = new ImageView(Image_Manager.bombImage);	//image of bomb whether save has bomb or not

        public SavePaneCell(String saveName, boolean isBomb, String level, String colorHash) {
        	//set fonts of labels
        	saveLabel.setFont(new Font("Arial",16));
        	levelLabel.setFont(new Font("Arial",16));
        	
        	//set texts of labels
        	saveLabel.setText(saveName);
        	levelLabel.setText(level);
        	
        	//set size of each cell
            this.setPrefSize(120, 33);
            this.setLeft(saveLabel);
            if (isBomb)									//set cell's left saveLabel, center bombImage(if it has bomb), right levelLabel
            	this.setCenter(bombImage);
            this.setRight(levelLabel);
            
            //set the color of cell with given color hash
            setStyle("-fx-background-color: #"+ colorHash +";");
            
        }
    }
}
