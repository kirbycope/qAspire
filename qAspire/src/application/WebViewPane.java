package application;

import netscape.javascript.JSObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WebViewPane
{
	public static WebView webView = Create();
	public static JSObject window;
	
	public static WebView Create()
	{
		if (webView == null)
		{
			// Create a WebView
			webView = new WebView();
			
			// Set the WebView's ViewPort
			webView.setPrefSize(1024, 768);
			
			// Load the Home Page URL in the WebView's WebEngine
		    webView.getEngine().load(Settings.homePage);
		    
		    // Disable default context menu
		    webView.setContextMenuEnabled(false);
		    
		    // Create the custom Context Menu
		    //WebViewContextMenu.Create(webView);
		    
		    // Get the WebEngine and set a listener for a state change
		    WebEngine webEngine = webView.getEngine();
		    webEngine.getLoadWorker().stateProperty().addListener
		    (
	            new ChangeListener<State>()
	            {
	            	public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, State oldState, State newState)
	                {
	                    if (newState == State.SUCCEEDED)
	                    {	                    	
	                    	// NOTE(S)
	    					// Execute jQuery method will insert a given .js file into the DOM
	    					// Execute Script method is stock and simply runs the string as it would in the dev console
	    					// To call a JavaFX Function from JavaScript use:
	    					//webEngine.executeScript("app.javaFunction();");
	                     	
	                     	// Update URL in AddressBar of JavaFX app
	                    	NavigationBar.addressBar.setText(webEngine.getLocation());
	                    	
	                    	// Get the page title
	    					TestStepControls.pageTitle = (String) webEngine.executeScript("document.title");
	    					
	    					// Get the page URL
	    					TestStepControls.pageUrl = webEngine.getLocation();
	                    	
	    					// http://stackoverflow.com/a/14543458
	    					window = (JSObject) webView.getEngine().executeScript("window");
	    					window.setMember("app", new JavaApp());
	    					 
	    					// Inject jQuery
	    					InjectJs("assets/scripts/jquery2_1_3.min.js");
	    					 
	    					// AddClickEvent
	    					InjectJs("assets/scripts/AddClickEvent.js");
	    					
	    					// HightlightMouseoverElement
	    					InjectCss("assets/css/HightlightMouseoverElement.css");
	    					InjectJs("assets/scripts/HightlightMouseoverElement.js");
	    					
	    					// CustomContextMenu
	    					InjectCss("assets/css/CustomContextMenu.css");
	    					InjectJs("assets/scripts/CustomContextMenu.js");
	    					
	    					// Set the page loaded flag
	    					// At the start of each test step, the flag is false.
	    					// TestStepControls.andWait() waits on this flag.
	    					TestStepControls.pageLoaded = true;
    					}
	                }
	            }
            );
		}
		
	    return webView;
	}
	
	public static void InjectCss(String fileName)
	{
		Document doc = webView.getEngine().getDocument();
		Node addStyle = doc.createElement("style");
		addStyle.setTextContent(LoadResourceAsString.sourceFileToString(fileName));
		Element element = doc.getDocumentElement();
        element.appendChild(addStyle);
	}
	
	public static void InjectJs(String fileName)
	{
		Document doc = webView.getEngine().getDocument();
		Node addStyle = doc.createElement("script");
		addStyle.setTextContent(LoadResourceAsString.sourceFileToString(fileName));
		Element element = doc.getDocumentElement();
        element.appendChild(addStyle);
	}
	
	public static class JavaApp
    {
        public void printToJavaConsole(Object object)
        	{ System.out.println(object); }
        public void setDescription(Object object)
        	{ StepBuilderPane.description.setText(object.toString()); }
        public void setAction(Object object)
        	{ StepBuilderPane.command.setValue(object.toString()); }
		public void setTarget(Object object)
        	{ StepBuilderPane.target.setText(object.toString()); }
		public void setValue(Object object)
	        { StepBuilderPane.value.setText(object.toString()); }
		public void addTestSteps()
			{  if (TestStepControls.inPlay == false) { StepBuilderPane.addTestSteps(); } }
    }
	
	// https://community.oracle.com/message/10446280
	public static Object executejQuery(final WebEngine engine, String script)
	{
	    return engine.executeScript(
	      "(function(window, document, version, callback) { "
	        + "var j, d;"
	        + "var loaded = false;"
	        + "if (!(j = window.jQuery) || version > j.fn.jquery || callback(j, loaded)) {"
	        + " var script = document.createElement(\"script\");"
	        + " script.type = \"text/javascript\";"
	        + " script.src = \"http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js\";"
	        + " script.onload = script.onreadystatechange = function() {"
	        + " if (!loaded && (!(d = this.readyState) || d == \"loaded\" || d == \"complete\")) {"
	        + " callback((j = window.jQuery).noConflict(1), loaded = true);"
	        + " j(script).remove();"
	        + " }"
	        + " };"
	        + " document.documentElement.childNodes[0].appendChild(script) "
	        + "} "
	      + "})(window, document, \"2.1.3\", function($, jquery_loaded) {" + script + "});"
	    );
	}
}