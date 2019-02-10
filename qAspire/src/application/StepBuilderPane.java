package application;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StepBuilderPane
{
	public static TextField description;
	public static ComboBox<String> command;
	public static TextField target;
	public static TextField value;
	
	/**
	 * <b>create</b><br>
	 * <br>
	 * Create the Step Builder Pane.<br>
	 * <br>
	 * 
	 * <table border="1">
	 * <tr>
	 *   <td colspan="9"><b>Step Builder</b></th>
	 * </tr>
	 * <tr>
	 *   <td colspan="3">Description</td>
	 *   <td colspan="3"> </td>
	 *   <td colspan="3">+</td>
	 * </tr>
	 * <tr>
	 *   <td>Command</td>
	 *   <td>Target</td>
	 *   <td>Value</td>
	 *   <td colspan="3"> </td>
	 *   <td colspan="3">X</td>
	 * </tr>
	 * </table>
	 * 
	 * @return VBox
	 */
	public static VBox create()
	{	
		description = new TextField();
		description.setPromptText("Description");
		
		// Command drop-down menu
		command = new ComboBox<String>(QAspireCommands.options);
		command.setPrefWidth(180);
		//command.setMinWidth(180);
		command.setPromptText("Command");
		
		// Target input field
		target = new TextField();
		target.setPrefWidth(340);
		target.setPromptText("Target");
		
		// Value input field
		value = new TextField();
		value.setPrefWidth(340);
		value.setPromptText("Value");
		
		// ✚  Add test step button
		Button add = new Button("\u271A");
		add.setStyle("-fx-base: #b6e7c9;");
		Tooltip ta = new Tooltip("Add Test Step");
        Tooltip.install(add, ta);
        add.setOnAction(e -> {
        	addTestSteps();
        });
		
        // ✖ Clear test step button
		Button clear = new Button("\u2716");
		clear.setStyle("-fx-base: #e7b6bc;");
		Tooltip tc = new Tooltip("Clear Test Step");
        Tooltip.install(clear, tc);
        clear.setOnAction(e -> {

        	description.clear();
        	command.valueProperty().set(null);
        	target.clear();
        	value.clear();
        });
        
		//---------------ParentVBox---------------------//
        //                ParentBox Row 1 (Header)		//
        //------------------ParentBox Row 2-------------//
        //row2column1          |row2column2|row2column3	//
        //	descriptionField   |           |  (+)		//		
        //  commandTargetValue |           |  (X)		//
        //----------------------------------------------//
        
        HBox commandTargetValue = new HBox();
        commandTargetValue.getChildren().addAll(command, target, value);
        
        VBox row2column1 = new VBox();
        row2column1.setPrefWidth(860);
        row2column1.getChildren().addAll(description, commandTargetValue);
        
        VBox row2column2 = new VBox();
        row2column2.setPrefWidth(100);
        
        VBox row2column3 = new VBox();
        row2column3.getChildren().addAll(add, clear);
        
        HBox parnetVboxRow2 = new HBox();
        parnetVboxRow2.getChildren().addAll(row2column1, row2column2, row2column3);
        
		VBox parentVbox = new VBox();
		Label parentVboxRow1 = new Label("Step Builder");
		parentVbox.getChildren().addAll(parentVboxRow1, parnetVboxRow2);
		
		return parentVbox;
	}

	/**
	 * <b>addTestSteps</b><br>
	 * <br>
	 * Adds the description, command, target, and value from the Step Builder to the Test Steps pane
	 */
	public static void addTestSteps()
	{
		if(command.getValue() != null)
		{
			// Add command, then clear
			ComboBox<String> stepCommand = new ComboBox<String>(QAspireCommands.options);
			stepCommand.setPrefWidth(180);
			stepCommand.setValue(command.getValue());
			if (stepCommand.getValue() == null) {stepCommand.setPromptText("Command");}
			command.valueProperty().set(null);
			
			// Add target, then clear
			TextField newTarget = new TextField();
			newTarget.setPrefWidth(209);
			newTarget.setText(target.getText());
			if (newTarget.getText().trim().isEmpty()) {newTarget.setPromptText("Target");}
			target.clear();
			
			// Add value, then clear
			TextField newValue = new TextField();
			newValue.setPrefWidth(209);
			newValue.setText(value.getText());
			if (newValue.getText().trim().isEmpty()) {newValue.setPromptText("Value");}
			value.clear();
			
			HBox hbox = new HBox();
			hbox.getChildren().addAll(stepCommand, newTarget, newValue);
			
			// Add description, then clear
			TextField newDescription = new TextField();
			newDescription.setText(description.getText());
			if (newDescription.getText().trim().isEmpty()) {newDescription.setPromptText("Description");}
			description.clear();
			
			// Join the inputs into a two row vBox
			VBox vbox = new VBox();
			vbox.getChildren().addAll(newDescription, hbox);
			
			// ✖  Delete button
			Button delete = new Button("\u2716"); 
			delete.setStyle("-fx-base: #e7b6bc;");
			Tooltip td = new Tooltip("Delete Test Step");
	        Tooltip.install(delete, td);
	        delete.setOnAction(e -> {
	        	Object source = e.getSource();
	        	if (source instanceof Button)
	        	{
	        	    Button clickedBtn = (Button) source;
	        	    // Get Parent node of the button, should be an HBox
	        	    HBox buttonParent = (HBox) clickedBtn.getParent();
	        	    // Get the Parent of that, so that we can remove the buttonParent from it
	        	    VBox grandparent = (VBox) buttonParent.getParent();
	        	    // Delete the buttonParent from the grandparent
	        	    grandparent.getChildren().remove(buttonParent);
	        	}
	        });
			
			HBox hbox2 = new HBox();
			hbox2.getChildren().addAll(vbox, delete);
			
			// Add the new test step to the test steps pane
			TestStepsPane.testStepsVbox.getChildren().add(hbox2);
		}
	}
}