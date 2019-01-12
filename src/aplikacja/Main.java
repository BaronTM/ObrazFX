package aplikacja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Main extends Application {

	Stage stage;
	Scene scene;
	GridPane mainPane;
	Pane panelPodImg;
	BorderPane panelPrawy;
	FlowPane panelPrzyciskow;
	Button wczytajBut;
	Button czyscBut;

	ImageView imgView;
	PixelReader reader;
	Image image;
	Image celownik;
	BorderPane panelCelownika;
	ImageView celownikView;
	JFXCustomCursor kursor;
	

	ArrayList<Kwadracik> kwadraciki;
	GridPane siatka;

	// zmienic glowny z border pane na anchor zeby za kazdym razem nie
	// zmienialo sie rozstawienie kwadracikow

	// w klasie kwadracik tez zmienic z canvas na image bo nie mozna
	// odczytac koloru czerwonego do poaddania analizy

	// sprobowac map, a jako klucz dawac wartosc nowej klasy xy

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		mainPane = new GridPane();
		mainPane.setHgap(15);
		mainPane.setVgap(15);
		mainPane.setPadding(new Insets(30));
		panelPodImg = new Pane();
		panelPodImg.setMinSize(360, 360);
		scene = new Scene(mainPane, 710, 450);
		scene.getStylesheets().add(getClass().getResource("styl.css").toExternalForm());
		panelPrawy = new BorderPane();
		panelPrzyciskow = new FlowPane(30, 30);
		wczytajBut = new Button("Wczytaj");
		czyscBut = new Button("Czyść");
		kwadraciki = new ArrayList<Kwadracik>();
		siatka = new GridPane();
		
		// Domyslny obrazek
		imgView = new ImageView();
		imgView.maxWidth(360);
		imgView.maxHeight(360);
		imgView.setPreserveRatio(true);
		imgView.setSmooth(true); // Sprawdzić co to jest???
		image = new Image(getClass().getResourceAsStream("lenna256px.png"));
		imgView.setCursor(Cursor.DISAPPEAR);
		imgView.setImage(image);
		reader = imgView.getImage().getPixelReader();
		siatka.setHgap(15);
		siatka.setVgap(15);
		siatka.setPadding(new Insets(10));
		
		// kursor
		celownik = new Image(getClass().getResourceAsStream("celownik.png"));
		panelCelownika= new BorderPane();
		celownikView = new ImageView();
		celownikView.maxWidth(41);
		celownikView.maxHeight(41);
		celownikView.setImage(celownik);
		panelCelownika.setCenter(celownikView);
		kursor = new JFXCustomCursor(scene, panelPodImg, panelCelownika, 51, 51);
		panelCelownika.setVisible(false);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Kwadracik k = new Kwadracik();
				kwadraciki.add(k);
				GridPane.setRowIndex(k, j);
				GridPane.setColumnIndex(k, i);
				siatka.getChildren().add(k);
			}
		}
		
		
		// css 
		imgView.getStyleClass().add("obrazek");
		czyscBut.getStyleClass().add("button");
		wczytajBut.getStyleClass().add("button");
		
		panelPrzyciskow.getChildren().addAll(wczytajBut, czyscBut);
		panelPrzyciskow.setPadding(new Insets(0, 30, 30, 30));
		panelPrawy.setTop(panelPrzyciskow);
		panelPrawy.setCenter(siatka);
		panelPodImg.getChildren().add(imgView);
		GridPane.setRowIndex(panelPrawy, 0);
		GridPane.setColumnIndex(panelPrawy, 1);
		GridPane.setRowIndex(panelPodImg, 0);
		GridPane.setColumnIndex(panelPodImg, 0);
		mainPane.getChildren().addAll(panelPodImg, panelPrawy);

		primaryStage.setScene(scene);
		primaryStage.setTitle("ObrazFX");
		primaryStage.setResizable(false);
		primaryStage.show();

		wczytajBut.setOnAction(e -> {
			zaladujObrazek();
		});
		czyscBut.setOnAction(e -> {
			czysc();
		});
		imgView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
			przechwycObrazek(event.getX(), event.getY());
			//System.out.println("X " + event.getX() + "   Y " + event.getY());
		});
		imgView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, event -> {
			panelCelownika.setVisible(true);
		});
		imgView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, event -> {
			panelCelownika.setVisible(false);
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void zaladujObrazek() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Załaduj obrazek");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		try {
			image = new Image(new FileInputStream(selectedFile));
			if ((image.getWidth() > 360) || image.getHeight() > 360) {
				image = new Image(new FileInputStream(selectedFile), 360, 360, true, true);	
			}
			imgView.setImage(image);
			reader = imgView.getImage().getPixelReader();
		} catch (Exception e) {
		}
		
	}

	private void przechwycObrazek(double xdou, double ydou) {
		int x = (int) xdou;
		int y = (int) ydou;
		double czerwony = 0;
		WritableImage capturedImg = new WritableImage(41, 41);
		PixelWriter capturedWriter = capturedImg.getPixelWriter();
		for (int a = 0; a < 41; a++) {
			for (int b = 0; b < 41; b++) {
				if (((x - 21 + a) >= 0) & ((y - 21 + b) >= 0) & ((x - 21 + a) < image.getWidth())
						& ((y - 21 + b) < image.getHeight())) {
					Color color = reader.getColor((x - 21 + a), (y - 21 + b));
					capturedWriter.setColor(a, b, Color.color(color.getRed(), color.getGreen(), color.getBlue()));
					czerwony += color.getRed();
				} else {
					capturedWriter.setColor(a, b, Color.color(0, 0, 0));
				}
			}
		}
		czerwony = czerwony / 1681;
		kwadraciki.add(new Kwadracik(capturedImg, czerwony));
		Collections.sort(kwadraciki);
		kwadraciki.remove(25);
		odswiezObrazki();
	}

	public void odswiezObrazki() {
		siatka.getChildren().clear();
		int k = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				GridPane.setRowIndex(kwadraciki.get(k), i);
				GridPane.setColumnIndex(kwadraciki.get(k), j);
				siatka.getChildren().add(kwadraciki.get(k));
				k++;
			}
		}
	}

	public void czysc() {
		siatka.getChildren().clear();
		kwadraciki = new ArrayList<Kwadracik>();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Kwadracik kw = new Kwadracik();
				GridPane.setRowIndex(kw, i);
				GridPane.setColumnIndex(kw, j);
				siatka.getChildren().add(kw);
				kwadraciki.add(kw);
			}
		}
	}
}
