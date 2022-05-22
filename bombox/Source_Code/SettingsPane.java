//Firat Bakici 150120029
//Batuhan basturk 150119035

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SettingsPane extends GridPane
{
	//settings pane's goal is changing the image of tiles 
	
	//imageview's to see which image's shown when choicebox change
	ImageView woodImage = new ImageView(Image_Manager.selectImage);			
	ImageView mirrorImage = new ImageView(Image_Manager.selectImage);
	ImageView emptyImage = new ImageView(Image_Manager.selectImage);
	ImageView wallImage = new ImageView(Image_Manager.selectImage);
	public SettingsPane()
	{
		//choiceboxes to see different images of tiles
		ChoiceBox<String> choiceBoxWood = new ChoiceBox<>();
		ChoiceBox<String> choiceBoxMirror = new ChoiceBox<>();
		ChoiceBox<String> choiceBoxEmpty = new ChoiceBox<>();
		ChoiceBox<String> choiceBoxWall = new ChoiceBox<>();

		//adding choices for each choicebox
		choiceBoxWood.getItems().addAll("Default","Hardened Clay","Pink Glass","Wood log");
		choiceBoxMirror.getItems().addAll("Default","Glass","Beacon","Cyan Glass");
		choiceBoxEmpty.getItems().addAll("Default","Quartz","Iron Block","Clay");
		choiceBoxWall.getItems().addAll("Default","Gray wool","Stone","Obsidian");
		
		//set choiceboxes default value to default
		choiceBoxWood.setValue("Default");
		choiceBoxMirror.setValue("Default");
		choiceBoxEmpty.setValue("Default");
		choiceBoxWall.setValue("Default");

		//for each choicebox, add listener to selected choice
		//selected choice will change the imageview of it above to see which one is chosen
		choiceBoxWood.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
			switch(newValue)
			{
				case "Default":
				{
					woodImage.setImage(Image_Manager.WoodImage);
					break;
				}
				case "Hardened Clay":
				{
					woodImage.setImage(Image_Manager.wood1Image);
					break;
				}
				case "Pink Glass":
				{
					woodImage.setImage(Image_Manager.wood2Image);
					break;
				}
				case "Wood log":
				{
					woodImage.setImage(Image_Manager.wood3Image);
					break;
				}
			}
		});
		choiceBoxMirror.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
			switch(newValue)
			{
				case "Default":
				{
					mirrorImage.setImage(Image_Manager.MirrorImage);
					break;
				}
				case "Glass":
				{
					mirrorImage.setImage(Image_Manager.mirror1Image);
					break;
				}
				case "Beacon":
				{
					mirrorImage.setImage(Image_Manager.mirror2Image);
					break;
				}
				case "Cyan Glass":
				{
					mirrorImage.setImage(Image_Manager.mirror3Image);
					break;
				}
			}
		});
		choiceBoxEmpty.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
			switch(newValue)
			{
				case "Default":
				{
					emptyImage.setImage(Image_Manager.EmptyImage);
					break;
				}
				case "Quartz":
				{
					emptyImage.setImage(Image_Manager.empty1Image);
					break;
				}
				case "Iron Block":
				{
					emptyImage.setImage(Image_Manager.empty2Image);
					break;
				}
				case "Clay":
				{
					emptyImage.setImage(Image_Manager.empty3Image);
					break;
				}
			}
		});
		choiceBoxWall.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
			switch(newValue)
			{
				case "Default":
				{
					wallImage.setImage(Image_Manager.WallImage);
					break;
				}
				case "Gray wool":
				{
					wallImage.setImage(Image_Manager.wall1Image);
					break;
				}
				case "Stone":
				{
					wallImage.setImage(Image_Manager.wall2Image);
					break;
				}
				case "Obsidian":
				{
					wallImage.setImage(Image_Manager.wall3Image);
					break;
				}
			}
		});
		
		//a button to load textures of tiles when clicked
		Button button = new Button("Load Textures");
		//add event to button
		//event will set the textures which selected on every choicebox when clicked

		button.setOnMouseClicked(e -> {
				implementChoicesWood(choiceBoxWood);
				implementChoicesMirror(choiceBoxMirror);
				implementChoicesEmpty(choiceBoxEmpty);
				implementChoicesWall(choiceBoxWall);
		});
		
		//add imageviews and choiceboxes to settingspane
		//and adjust paddings, size, color
		setHgap(10);
		setVgap(10);
		setPadding(new Insets(50,50,50,50));
		add(woodImage, 0, 0);
		add(mirrorImage, 2, 0);
		add(emptyImage, 0, 2);
		add(wallImage, 2, 2);
		add(new Label("Please implement textures"),0,4);
		add(new Label("while game stage is closed"),0,5);
		add(new Label("Or load another save/level"),0,6);

		add(choiceBoxWood, 0, 1);
		add(choiceBoxMirror, 2, 1);
		add(choiceBoxEmpty, 0, 3);
		add(choiceBoxWall, 2, 3);
		
		add(button, 1, 2);
		setPrefSize(450, 350);
		setMinSize(450, 350);
		setStyle("-fx-background-color: #D2DEDF");
	}
	
	//the methods below fill get the given choicebox's choice and set the image of tile to chosen value
	
	private void implementChoicesWood(ChoiceBox<String> choiceBox)
	{
		switch(choiceBox.getValue())
		{
			case "Default":
			{
				WoodTile.imageOfTile = Image_Manager.WoodImage;
				break;
			}
			case "Hardened Clay":
			{
				WoodTile.imageOfTile = Image_Manager.wood1Image;
				break;
			}
			case "Pink Glass":
			{
				WoodTile.imageOfTile = Image_Manager.wood2Image;
				break;
			}
			case "Wood log":
			{
				WoodTile.imageOfTile = Image_Manager.wood3Image;
				break;
			}
		}
	}
	private void implementChoicesMirror(ChoiceBox<String> choiceBox)
	{
		switch(choiceBox.getValue())
		{
			case "Default":
			{
				MirrorTile.imageOfTile = Image_Manager.MirrorImage;
				break;
			}
			case "Glass":
			{
				MirrorTile.imageOfTile = Image_Manager.mirror1Image;
				break;
			}
			case "Beacon":
			{
				MirrorTile.imageOfTile = Image_Manager.mirror2Image;
				break;
			}
			case "Cyan Glass":
			{
				MirrorTile.imageOfTile = Image_Manager.mirror3Image;
				break;
			}
		}
	}
	private void implementChoicesEmpty(ChoiceBox<String> choiceBox)
	{
		switch(choiceBox.getValue())
		{
			case "Default":
			{
				EmptyTile.imageOfTile = Image_Manager.EmptyImage;
				break;
			}
			case "Quartz":
			{
				EmptyTile.imageOfTile = Image_Manager.empty1Image;
				break;
			}
			case "Iron Block":
			{
				EmptyTile.imageOfTile = Image_Manager.empty2Image;
				break;
			}
			case "Clay":
			{
				EmptyTile.imageOfTile = Image_Manager.empty3Image;
				break;
			}
		}
	}
	private void implementChoicesWall(ChoiceBox<String> choiceBox)
	{
		switch(choiceBox.getValue())
		{
			case "Default":
			{
				WallTile.imageOfTile = Image_Manager.WallImage;
				break;
			}
			case "Gray wool":
			{
				WallTile.imageOfTile = Image_Manager.wall1Image;
				break;
			}
			case "Stone":
			{
				WallTile.imageOfTile = Image_Manager.wall2Image;
				break;
			}
			case "Obsidian":
			{
				WallTile.imageOfTile = Image_Manager.wall3Image;
				break;
			}
		}
	}
}
