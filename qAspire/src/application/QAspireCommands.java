package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QAspireCommands
{
	static ObservableList<String> options = create();
	
	/**
	 * <b>create</b><br>
	 * <br>
	 * Create a list of all Selenium commands available in the application.<br>
	 * <br>
	 * @return ObservableList - Contains the ComboBox selection options
	 */
	static ObservableList<String> create()
	{
		if (options == null)
		{
			options = FXCollections.observableArrayList
			(
				"click"
				,"clickAndWait"
				,"deleteAllVisibleCookies"
				,"goBack"
				//,"goForward"// There is no such option in Selenium IDE
				,"open"
				,"openAndWait"
				,"refresh"
				,"select"
				,"type"
				,"waitForElementPresent"
				,"waitForLocation"
				,"waitForText"
				,"waitForTitle"
				,"verifyElementPresent"
				,"verifyText"
				,"verifyTitle"
				,"verifyLocation"
		    );
		}
		
		return options;
	}
}