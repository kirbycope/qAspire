package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TestStepsPane
{
	public static VBox childVBox;
	public static VBox parentVbox = create();
	public static VBox testStepsVbox;
	public static TextField baseAddresstextField;
	
	/**
	 * <b>create</b><br>
	 * <br>
	 * Create the Test Steps Pane.<br>
	 * <br>
	 * 
	 * <table border="1">
	 * <tr>
	 *   <td colspan="10"><b>Test Steps<b></th>
	 * </tr>
	 * <tr>
	 *   <td colspan="2">Base Address</td>
	 *   <td colspan="8">baseAddress</td>
	 * </tr>
	 * <tr>
	 *   <td colspan="9">description</td>
	 *   <td>X</td>
	 * </tr>
	 * <tr>
	 *   <td colspan="3">command</td>
	 *   <td colspan="3">target</td>
	 *   <td colspan="3">value</td>
	 *   <td></td>
	 * </tr>
	 * <tr>
	 *   <td colspan="10" rowspan="2"></td>
	 * </tr>
	 * <tr>
	 * </tr>
	 * <tr>
	 *   <td>Play</td>
	 *   <td>Save</td>
	 *   <td>Open</td>
	 *   <td colspan="7"></td>
	 * </tr>
	 * </table>
	 * 
	 * @return VBox
	 */
	public static VBox create()
	{
		if (parentVbox == null)
		{
			// Label for parent
			Label label = new Label(" Test Steps");
			label.setStyle("-fx-font-size: 24px;");
			
			// TestSteps Pane
			testStepsVbox = new VBox();
			ScrollPane testSteps = new ScrollPane();
			testSteps.setPrefSize(642, 743);
			testSteps.setContent(testStepsVbox);
			
			// BaseAddress
			Label baseAddressLabel = new Label(" Base Address: ");
			baseAddressLabel.setStyle("-fx-font-size: 16px;");
			baseAddresstextField = new TextField();
			baseAddresstextField.setPrefWidth(530);
			HBox baseAddress = new HBox();
			baseAddress.getChildren().addAll(baseAddressLabel, baseAddresstextField);
			
			// ChildVBox Pane
			VBox childVbox = new VBox();
			childVbox.getChildren().addAll(baseAddress, testSteps);
			
			// Play Button
			Button play = new Button("\u25BA" + " Play"); // ► 
			Tooltip tPlay = new Tooltip("Play");
	        Tooltip.install(play, tPlay);
	        play.setOnAction(e -> {
	        	TestStepControls.play();
	        });
			
			// Step Button
			Button step = new Button("\u25BA"+"| Step"); // ►|
			Tooltip tStep = new Tooltip("Step");
	        Tooltip.install(step, tStep);
	        step.setOnAction(e -> {
	        	System.out.println("Step button pushed...");
	        });
			
			// Pause Button
			Button pause = new Button("|| Pause");
			Tooltip tPause = new Tooltip("Pause");
	        Tooltip.install(pause, tPause);
	        pause.setOnAction(e -> {
	        	System.out.println("Pause button pushed...");
	        });
			
			// Stop Button
			Button stop = new Button("\u25A0" + " Stop"); // ■
			Tooltip tStop = new Tooltip("Stop");
	        Tooltip.install(stop, tStop);
	        stop.setOnAction(e -> {
	        	System.out.println("Stop button pushed...");
	        });
	        
	        // Save Button - TODO: Move to somewhere else on the app
	        Button save = new Button("\u25BC" + " Save"); // ▼
	        Tooltip tSave = new Tooltip("Save");
	        Tooltip.install(save, tSave);
	        save.setOnAction(e -> {
	        	SaveTest.toFile();
	        });
	        
	        // Open Button - TODO: Move to somewhere else on the app
	        Button open = new Button("\u25C6" + " Open"); // ◆
	        Tooltip tOpen = new Tooltip("Open");
	        Tooltip.install(open, tOpen);
	        open.setOnAction(e -> {
	        	OpenTest.fromFile();
	        });
	        
	        /********ParentVBox*******
			*	Label				 *
			*-------------------------
			*    ChildVbox
			*    -------------		 *
			*    |baseAddress|		 *
			*    |testSteps  |       *
			*                        *
			**************************
			*      controls          *
			*************************/
			
			// Create HBox for Controls
			HBox controls = new HBox();
			controls.getChildren().addAll(play, save, open);
			
			// Create parent VBox and add it's elements
			VBox parentVbox = new VBox();
			parentVbox.getChildren().addAll(label, childVbox, controls);
			
			return parentVbox;
		}
		
		return parentVbox;
	}
}