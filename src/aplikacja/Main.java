package aplikacja;
import java.io.File;
import java.util.ArrayList;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Main extends Application{

	Stage stage;
	Scene scene;
	BorderPane mainPane;
	BorderPane panelPrawy;
	FlowPane panelPrzyciskow;
	Button wczytajBut;
	Button czyscBut;
	
	Canvas canvas;
	GraphicsContext gc;
	Image image;
	WritableImage dstImage;
	PixelWriter writer;
	PixelReader reader;
	
	ArrayList<Kwadracik> kwadraciki;
	GridPane siatka;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		mainPane = new BorderPane();
		scene = new Scene(mainPane, 700, 450);
		scene.getStylesheets().add(getClass().getResource("styl.css").toExternalForm());
		panelPrawy = new BorderPane();
		panelPrzyciskow = new FlowPane(30, 30);
		wczytajBut = new Button("Wczytaj");
		czyscBut = new Button("Czyść");
		kwadraciki = new ArrayList<Kwadracik>();
		siatka = new GridPane();
		
		// Domyslny obrazek
		canvas = new Canvas(360, 360);
		gc = canvas.getGraphicsContext2D();
		image = new Image(getClass().getResourceAsStream("lenna256px.png"));
		dstImage = new WritableImage(360, 360);
		writer = dstImage.getPixelWriter();
		reader = dstImage.getPixelReader();
		for(int x = 0; x < 360; x++) {
			for (int y = 0; y < 360; y++) {
				//Color color = Color.valueOf("#43565c");
				Color color = Color.BLACK;				
				writer.setColor(x, y, Color.color(
						color.getRed(), 
						color.getGreen(), 
						color.getBlue()));
			}
		}
		
		gc.drawImage(dstImage, 0, 0);
		gc.drawImage(image, 0, 0);
		
		siatka.setHgap(15);
		siatka.setVgap(15);
		siatka.setPadding(new Insets(10));
		
		for(int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Kwadracik k = new Kwadracik(41, 41);
				kwadraciki.add(k);
				GridPane.setRowIndex(k, j);
				GridPane.setColumnIndex(k, i);
				siatka.getChildren().add(k);
			}
		}	
		
		panelPrzyciskow.getChildren().addAll(wczytajBut, czyscBut);
		panelPrzyciskow.setPadding(new Insets(0, 30, 30, 30));
		panelPrawy.setTop(panelPrzyciskow);
		panelPrawy.setCenter(siatka);
		panelPrawy.setPadding(new Insets(0, 0, 0, 10));
		mainPane.setRight(panelPrawy);
		mainPane.setLeft(canvas);
		mainPane.setPadding(new Insets(30));
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("ObrazFX");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		wczytajBut.setOnAction(e -> {
			zaladujObrazek();
		});
		canvas.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
			double x = event.getX();
			double y = event.getY();
			terazTu
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void zaladujObrazek() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Załaduj obrazek");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		File selectedFile = fileChooser.showOpenDialog(stage);
	}
	
}
