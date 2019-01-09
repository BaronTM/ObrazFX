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
	long czerwone;

	public Kwadracik() {
		super(41, 41);
		wimage = new WritableImage(41, 41);
		writer = wimage.getPixelWriter();
		reader = wimage.getPixelReader();
		czerwone = 0;
		gc = this.getGraphicsContext2D();
		for (int x = 0; x < 41; x++) {
			for (int y = 0; y < 41; y++) {
				// Color color = Color.valueOf("#43565c");
				Color color = Color.BLACK;
				writer.setColor(x, y, Color.color(color.getRed(), color.getGreen(), color.getBlue()));
			}
		}
		gc.drawImage(wimage, 0, 0);
		liczCzerwone();
	}

	public Kwadracik(WritableImage img) {
		super(img.getWidth(), img.getHeight());
		wimage = img;
		writer = wimage.getPixelWriter();
		reader = wimage.getPixelReader();
		gc = this.getGraphicsContext2D();
		gc.drawImage(wimage, 0, 0);
		liczCzerwone();
	}

	public void liczCzerwone() {
		czerwone = 0;
		for (int x = 0; x < 41; x++) {
			for (int y = 0; y < 41; y++) {
				czerwone += reader.getColor(x, y).getRed();
			}
		}
	}

	@Override
	public int compareTo(Kwadracik k) {
		if (this.czerwone > k.czerwone) {
			return (int) (this.czerwone - k.czerwone);
		} else if (this.czerwone < k.czerwone) {
			return (int) (k.czerwone - this.czerwone);
		} else {
			return 0;
		}
	}

}
