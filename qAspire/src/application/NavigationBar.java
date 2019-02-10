package application;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;

/**
 * <b>NavigationBar<b><br>
 * <br>
 * The NavigationBar contains the buttons for the browser (back, forward, reload),
 * the address bar, and a menu.
 *
 */
public class NavigationBar
{
	/**
	 * The "Address Bar" where you enter a URL.
	 */
	static TextField addressBar;
	
	static ToolBar toolbar = create();
	
	/**
	 * <b>create</b><br>
	 * <br>
	 * Create the address bar for WebView.<br>
	 * <br>
	 * @return ToolBar
	 */
	static ToolBar create()
	{
		// ← Back Button
        Button back = new Button("\u2190");
        Tooltip tb = new Tooltip("Back");
        Tooltip.install(back, tb);
        back.setOnAction(e -> {
        	TestStepControls.goBack();
        	// Save the back button press
        	StepBuilderPane.description.setText("Click browser back button");
            StepBuilderPane.command.setValue("goBack");
            StepBuilderPane.target.setText("");
            StepBuilderPane.value.setText("");
            StepBuilderPane.addTestSteps();
        });
        
        // → Forward Button
        Button forward = new Button("\u2192");
        Tooltip tf = new Tooltip("Forward");
        Tooltip.install(forward, tf);
        forward.setOnAction(e -> {
        	TestStepControls.goForward();
        	/* Note: This is now commented out because there is no goForward in Selenium IDE
        	// Save the forward button press
        	//StepBuilderPane.description.setText("Click browser forward button");
            //StepBuilderPane.action.setValue("goForward");
            //StepBuilderPane.target.setText("");
            //StepBuilderPane.value.setText("");
            //StepBuilderPane.AddTestSteps();
             */
        });
        
        // ↻ Reload Button
        Button reload = new Button("\u21BB");
        Tooltip tr = new Tooltip("Reload");
        Tooltip.install(reload, tr);
        reload.setOnAction(e -> {
        	TestStepControls.refresh();
        	// Save the reload button press
        	StepBuilderPane.description.setText("Refresh browser window");
            StepBuilderPane.command.setValue("refresh");
            StepBuilderPane.target.setText("");
            StepBuilderPane.value.setText("");
            StepBuilderPane.addTestSteps();
        });
        
        // Create the "Address Bar"
	    addressBar = new TextField(Settings.homePage);
	    addressBar.setPrefWidth(884);
	    // When text is submitted via [Enter] key press...
    	addressBar.setOnAction(e -> {
    		// Get the URL
    		String url = addressBar.getText();
    		// If the URL is not blank...
    		if (!url.isEmpty())
    		{
    			// Then navigate to the URL
    			WebViewPane.webView.getEngine().load(WebViewContextMenu.getUrl(url));
    			// Trim the URL if it starts with the baseAddress
    			url = BaseUrlHelper.removeBaseAddress(url);
        		// Add the test step info to the step builder
                StepBuilderPane.description.setText("Open URL: " + url);
                StepBuilderPane.command.setValue("open");
                StepBuilderPane.target.setText(url);
                StepBuilderPane.value.setText("");
                // Add the test case to the Test Steps
                StepBuilderPane.addTestSteps();
			}
        });
        
    	// Hamburger menu option 1
    	MenuItem clearCookies = new MenuItem("Clear Cookies");
    	clearCookies.setOnAction(e -> {
    		TestStepControls.deleteAllVisibleCookies();
    		// Save the clear cookies option press
        	StepBuilderPane.description.setText("Clear all cookies");
            StepBuilderPane.command.setValue("deleteAllVisibleCookies");
            StepBuilderPane.target.setText("");
            StepBuilderPane.value.setText("");
            StepBuilderPane.addTestSteps();
        });
    	
    	// Hamburger menu option 2
    	MenuItem verifyLocation = new MenuItem("Verify Current URL");
    	verifyLocation.setOnAction(e -> {
    		// Get current URL
    		String currentUrl = TestStepControls.getLocation();
    		// Save the verify title option press
        	StepBuilderPane.description.setText("Verify current url");
            StepBuilderPane.command.setValue("verifyLocation");
            StepBuilderPane.target.setText(currentUrl);
            StepBuilderPane.value.setText("");
            StepBuilderPane.addTestSteps();
        });
    	
    	// Hamburger menu option 3
    	MenuItem verifyTitle = new MenuItem("Verify Page Title");
    	verifyTitle.setOnAction(e -> {
    		// Get title
    		String title = TestStepControls.pageTitle;
    		// Save the verify title option press
        	StepBuilderPane.description.setText("Verify page title");
            StepBuilderPane.command.setValue("verifyTitle");
            StepBuilderPane.target.setText(title);
            StepBuilderPane.value.setText("");
            StepBuilderPane.addTestSteps();
        });
    	
        // ☰ Hamburger Menu
        MenuButton hamburgerMenu = new MenuButton("\u2630");
        hamburgerMenu.getItems().addAll(clearCookies, verifyLocation, verifyTitle);
        
        // Create a ToolBar to give the user a browser-like experience
        ToolBar toolbar = new ToolBar
		(
    		back,
    		forward,
    		reload,
    		new Separator(),
    		addressBar,
    		hamburgerMenu
		);
        
        return toolbar;
	}
}