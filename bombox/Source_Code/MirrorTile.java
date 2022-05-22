//Firat Bakici 150120029
//Batuhan basturk 150119035

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MirrorTile extends Tile
{
	public static Image imageOfTile = Image_Manager.MirrorImage;	//imageofTile is defined in field in case of change by settings
	public MirrorTile()
	{
		getChildren().add(new ImageView(imageOfTile));			//set the image of tile
		pointOfTile = 1;
	}
}
