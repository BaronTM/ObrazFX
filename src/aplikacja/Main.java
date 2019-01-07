package aplikacja;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

	Scene scene;
	BorderPane mainPane;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainPane = new BorderPane();
		scene = new Scene(mainPane, 600, 400);
		scene.getStylesheets().add(getClass().getResource("styl.css").toExternalForm());
		
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("ObrazFX");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}	
	
}
