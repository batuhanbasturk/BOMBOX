//Firat Bakici 150120029
//Batuhan basturk 150119035

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WoodTile extends Tile
{
	public static Image imageOfTile = Image_Manager.WoodImage;		//imageofTile is defined in field in case of change by settings
	public WoodTile()
	{
		getChildren().add(new ImageView(imageOfTile));			//set the image of tile
		pointOfTile = 2;
	}
}
