//Firat Bakici 150120029
//Batuhan basturk 150119035

import javafx.scene.image.Image; 
import javafx.scene.image.ImageView;

public class EmptyTile extends Tile
{
	public static Image imageOfTile = Image_Manager.EmptyImage;		//imageofTile is defined in field in case of change by settings
	public EmptyTile()
	{
		getChildren().add(new ImageView(imageOfTile));				//set the image of tile
		//has no points since it can not be destroyed
	}
}
