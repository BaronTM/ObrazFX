package aplikacja;
import java.io.File;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application{

	Stage stage;
	Scene scene;
	AnchorPane root;
	BorderPane mainPane;
	BorderPane panelPrawy;
	FlowPane panelPrzyciskow;
	Button wczytajBut;
	Button czyscBut;
	
	Canvas canvas;
	GraphicsContext gc;
	Image image;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		root = new AnchorPane();
		scene = new Scene(root, 600, 400);
		scene.getStylesheets().add(getClass().getResource("styl.css").toExternalForm());
		mainPane = new BorderPane();
		panelPrawy = new BorderPane();
		panelPrzyciskow = new FlowPane(30, 30);
		wczytajBut = new Button("Wczytaj");
		czyscBut = new Button("Czyść");
		
		// Domyslny obrazek
		Canvas canvas = new Canvas(256, 256);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image image = new Image(getClass().getResourceAsStream("lenna256px.png"));
		gc.drawImage(image, 0, 0);
		
		panelPrzyciskow.getChildren().addAll(wczytajBut, czyscBut);
		panelPrawy.setTop(panelPrzyciskow);
		mainPane.setRight(panelPrawy);
		
		AnchorPane.setTopAnchor(mainPane, 30d);
		AnchorPane.setRightAnchor(mainPane, 30d);
		AnchorPane.setLeftAnchor(mainPane, 30d);
		AnchorPane.setBottomAnchor(mainPane, 30d);
		root.getChildren().add(mainPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ObrazFX");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		wczytajBut.setOnAction(e -> {
			zaladujObrazek();
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
