
//Firat Bakici 150120029
//Batuhan basturk 150119035

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CreditsPane extends BorderPane
{
	//a little pane to show credits
	public CreditsPane()
	{
		TextFlow tf = new TextFlow();						//temporary textflow to set padding of main text
        Text text = new Text("Just two guys go on a game-making adventure");
        text.setFont(Font.font("Rockwell", FontWeight.EXTRA_BOLD,FontPosture.REGULAR, 20));			//set font of text
        tf.getChildren().add(text);							//add text to textflow
        tf.setPadding(new Insets(30,50,30,110));			//set padding to keep in center

        //set image/imageview for Firat
        ImageView iv = new ImageView(Image_Manager.firat);
        iv.setFitHeight(200);
        iv.setFitWidth(200);
        iv.setPreserveRatio(true);
        
        //set image/imageview for Batuhan
        ImageView iv2 = new ImageView(Image_Manager.batu);
        iv2.setFitHeight(200);
        iv2.setFitWidth(200);
        iv2.setPreserveRatio(true);
        
        //label's to hold our names
        Label isim1 = new Label("Firat");
        isim1.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
        isim1.setTextFill(Color.web("#5B1A0C"));
        Label isim2 = new Label("Batu");
        isim2.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
        isim2.setTextFill(Color.web("#5B1A0C"));
        
        //set padding in order to hold at center
        isim1.setPadding(new Insets(0,0,15,50));
        isim2.setPadding(new Insets(0,0,15,50));
        
        //temporary gridpane to hold all images, names
        GridPane gp = new GridPane();
        gp.add(isim1, 0, 0);
        gp.add(isim2, 1, 0);
        gp.add(iv, 0, 1);
        gp.add(iv2, 1, 1);
        gp.setPadding(new Insets(50,100,50,100));
        gp.setHgap(10);
        
        //since creditsPane extends BorderPane, easily set top text, and set bottom photos and names.
        setTop(tf);
        setBottom(gp);
        setPrefSize(450, 350);
        setStyle("-fx-background-color: #D2DEDF");
	}
}
