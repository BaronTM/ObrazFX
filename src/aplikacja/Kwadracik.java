package aplikacja;

import java.util.Comparator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Kwadracik extends Canvas implements Comparable<Kwadracik> {

	WritableImage wimage;
	PixelWriter writer;
	PixelReader reader;
	GraphicsContext gc;
	double czerwony;

	public Kwadracik() {
		super(41, 41);
		wimage = new WritableImage(41, 41);
		writer = wimage.getPixelWriter();
		reader = wimage.getPixelReader();
		czerwony = 0;
		gc = this.getGraphicsContext2D();
		for (int x = 0; x < 41; x++) {
			for (int y = 0; y < 41; y++) {
				// Color color = Color.valueOf("#43565c");
				Color color = Color.BLACK;
				writer.setColor(x, y, Color.color(color.getRed(), color.getGreen(), color.getBlue()));
			}
		}
		gc.drawImage(wimage, 0, 0);
	}

	public Kwadracik(WritableImage img, double czerwony) {
		super(img.getWidth(), img.getHeight());
		wimage = img;
		this.czerwony = czerwony;
		writer = wimage.getPixelWriter();
		reader = wimage.getPixelReader();
		gc = this.getGraphicsContext2D();
		gc.drawImage(wimage, 0, 0);
	}

	@Override
	public int compareTo(Kwadracik k) {
		if (this.czerwony > k.czerwony) {
			return -1;
		} else if (this.czerwony < k.czerwony) {
			return 1;
		} else {
			return 0;
		}
	}

}
