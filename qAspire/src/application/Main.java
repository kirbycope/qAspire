package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import application.WebViewPane;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
    	// Application Icon
        primaryStage.getIcons().add(Settings.applicationIcon);
        
        // Application Title
        primaryStage.setTitle("qAspire");
        
        // Make the center-pane
        VBox center = new VBox();
        center.setPrefSize(1024, 768);
        center.getChildren().addAll(NavigationBar.toolbar, WebViewPane.webView, StepBuilderPane.create());
        
        // Make the left-pane
        VBox left = TestStepsPane.parentVbox;
        
        // Create UI layout
        BorderPane root = new BorderPane(center, null, null, null, left);
        
        // Application window and size
        primaryStage.setScene(new Scene(root, 1686, 875));
        
        // Show the application window
        primaryStage.show();
        
        TestStepControls.inPlay = false;
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}