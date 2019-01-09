package aplikacja;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Kwadracik extends Canvas implements Comparable<Kwadracik>{

	WritableImage wimage;
	PixelWriter writer;
	GraphicsContext gc;
	
	public Kwadracik(int a, int b) {
		super(a, b);
		wimage = new WritableImage(a, b);
		writer = wimage.getPixelWriter();
		gc = this.getGraphicsContext2D();
		for(int x = 0; x < a; x++) {
			for (int y = 0; y < b; y++) {
				//Color color = Color.valueOf("#43565c");
				Color color = Color.BISQUE;				
				writer.setColor(x, y, Color.color(
						color.getRed(), 
						color.getGreen(), 
						color.getBlue()));
			}
		}
		gc.drawImage(wimage, 0, 0);
	}

	@Override
	public int compareTo(Kwadracik o) {
		// TODO Auto-generated method stub
		return 0;
	}	
}
