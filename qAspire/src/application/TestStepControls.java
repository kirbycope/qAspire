package application;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;

public class TestStepControls
{	
	// Test Step Values
	public static String command;
	public static String target;
	public static String value;
	
	// Test Execution Values
	public static Boolean inPlay;
	public static Boolean pageLoaded;
	public static String pageTitle;
	public static String pageUrl;
	public static String targetId;
	public static String targetName;
	public static String targetType;
	public static String targetValue;
	public static String targetLink;
	public static Element targetElement;
	public static String javaScript;
	
	// Get the WebEngine
	static WebEngine webEngine = WebViewPane.webView.getEngine();
	
	/**
	 * <b>play</b><br>
	 * <br>
	 * Runs each test step from the application.
	 */
	public static void play()
	{
		Thread thread = new Thread()
		{
			@SuppressWarnings("rawtypes")
			public void run()
			{
				inPlay = true;
				
				// Get Test Step(s). See StepBuilder.AddTestSteps() for test step structure
				ObservableList<Node> testSteps = TestStepsPane.testStepsVbox.getChildren();
				int stepCount = testSteps.size();
				// For each test step in the Test Steps panel
				for (int i = 0; i < stepCount; i++) // Start at index 1 to skip the base address
				{
					// Reset Test Values (in case there are any artifacts exists from the previous test step
					String result = null;
					pageLoaded = false;
					targetId = null;
					targetName = null;
					targetType = null;
					targetValue = null;
					targetLink = null;
					targetElement = null;
					javaScript = null;
					
					// Get "hbox2" added from StepBuilder.AddTestSteps()
					HBox testStepContainer = (HBox) testSteps.get(i);
					// Get the children of testStepContainer
					ObservableList<Node> testStepChildren = testStepContainer.getChildren();
					// Get "vbox" added from StepBuilder.AddTestSteps()
					VBox testStepVBox = (VBox) testStepChildren.get(0);
					// Get the children of testStepVBox
					ObservableList<Node> testStepVBoxChildren = testStepVBox.getChildren();
					// Get "hbox" added from StepBuilder.AddTestSteps()
					HBox commandHBox = (HBox) testStepVBoxChildren.get(1);
					// Get the children of the commandHBox
					ObservableList<Node> testStepHBoxChildren = commandHBox.getChildren();
					// Get the value of the 'command'
					command = (String) ((ComboBox)testStepHBoxChildren.get(0)).getValue();
					// Get the value of the 'target'
					target = (String) ((TextField)testStepHBoxChildren.get(1)).getText();
					// Get the value of the 'value'
					value = (String) ((TextField)testStepHBoxChildren.get(2)).getText();
					
					// Run the appropriate function for the given Test Step Command
					if (command == "click")
						{
							Boolean clicked = click();
							if (clicked)
								{ result = ":: PASS :: Executed test: click, " + target; }
							else
								{ result = ":: FAIL :: Executed test: click, "+ target + ". Could not find target."; }
						}
					else if (command == "clickAndWait")
						{ result = clickAndWait(); }
					else if (command == "deleteAllVisibleCookies")
						{ result = deleteAllVisibleCookies(); }
					else if (command == "goBack")
						{ result = goBack(); }
					else if (command == "goForward") // NOTE: Not supported in Selenium IDE
						{ result = goForward(); }
					else if (command == "open")
						{
							Boolean opened = open();
							if (opened)
								{ result = ":: PASS :: Executed test: open, " + target; }
							else
								{ result = ":: FAIL :: Executed test: open, "+ target + ". Failed to open target."; }
						}
					else if (command == "openAndWait")
						{ result = openAndWait(); }
					else if (command == "refresh")
						{ result = refresh(); }
					else if (command == "select")
						{ result = select(); }
					else if (command == "type")
						{ result = type(); }
					else if (command == "verifyElementPresent")
						{
							Boolean exists = verifyElementPresent();
							if (exists)
								{ result = ":: PASS :: Executed test: verifyElementPresent, " + target; }
							else
								{ result = ":: FAIL :: Executed test: verifyElementPresent, "+ target + ". Could not find target."; }
						}
					else if (command == "verifyLocation")
						{
							Boolean verified = verifyLocation();
							if(verified)
								{ result = ":: PASS :: Executed test: verifyLocation, " + target; }
							else
								{ result = ":: FAIL :: Executed test: verifyLocation, " + target;}
						}
					else if (command == "verifyText")
						{
							Boolean verified = verifyText();
							if(verified)
								{ result = ":: PASS :: Executed Test Step: verifyText, " + target + ", " + value; }
							else
								{ result =  ":: FAIL :: Executed Test Step: verifyText, " + target + ", " + value;}
						}
					else if (command == "verifyTitle")
						{
							Boolean verified = verifyTitle();
							if(verified)
								{ result = ":: PASS :: Executed Test Step: verifyTitle, " + target + ", " + value; }
							else
								{ result =  ":: FAIL :: Executed Test Step: verifyTitle, " + target + ", " + value;}
						}
					else if (command == "waitForElementPresent")
						{ result = waitForElementPresent(); }
					else if (command == "waitForLocation")
						{ result = waitForLocation(); }
					else if (command == "waitForText")
						{ result = waitForText(); }
					else if (command == "waitForTitle")
						{ result = waitForTitle(); }
					else // Send values to injected script function (/assets/scripts/qAspireCommands.js), runTest()
						{
							if ( (!command.isEmpty()) && (!target.isEmpty()) && (!value.isEmpty()) )
							{
								// runTest("{command}", "{target}", "{value}");
								result = (String) webEngine.executeScript("runTest(\"" + command + "\", \"" + target + "\", \"" + value + "\");");
							}
							else if ( (!command.isEmpty()) && (!target.isEmpty()) && (value.isEmpty()) )
							{
								// runTest("{command}", "{target}");
								result = (String) webEngine.executeScript("runTest(\"" + command + "\", \"" + target + "\");");
							}
							else if ( (!command.isEmpty()) && (target.isEmpty()) && (value.isEmpty()) )
							{
								// runTest("{command}");
								result = (String) webEngine.executeScript("runTest(\"" + command + "\");");
							}
						}
					
					System.out.println("Result: " + result); //DEBUGGING
					
					// Style the test step based on its execution result
					if (! result.contains(":: FAIL ::"))
						{ testStepContainer.setStyle("-fx-base: #b6e7c9;"); }
					else
						{ testStepContainer.setStyle("-fx-base: #e7b6bc;"); }
				}
				
				inPlay = false;
			}
		};
		
		thread.start();
	}

	/**
	 * <b>deleteAllVisibleCookies</b><br>
	 * <br>
	 * Deletes all cookies from the current session.<br>
	 * <br>
	 * @return String - Result
	 */
	public static String deleteAllVisibleCookies()
	{
		java.net.CookieManager manager = new java.net.CookieManager();
		java.net.CookieHandler.setDefault(manager);
		manager.getCookieStore().removeAll();
		
		return ":: PASS :: Executed Test Step: deleteAllVisibleCookies";
	}
	
	/** <b>click</b><br>
	 * <br>
	 * Execute JavaScript command .click() on the given element.<br>
	 * <br>
	 * @return String - Result
	 */
	public static Boolean click()
	{
		// Have only done click with the selectors; id, name (+type,+value), link
		// TODO: Still need selectors; xpath
		
		// Ensure the element exists before trying to click on it.
		Boolean exists = verifyElementPresent();
		if (exists == true)
		{
			if (targetId != null)
				{ javaScript = "$(\"#" + targetId + "\").click();"; }
			else if (targetName != null) // Use CSS selectors with jQuery
				{
					if ((targetType == null) && (targetValue == null))
						{ javaScript = "$(\"[name='" + targetName + "']\").first().click()"; }
					else if ((targetType != null) && (targetValue == null))
						{ javaScript = "$(\"[name='" + targetName + "'][type='" + targetType + "']\").first().click();"; }
					else if ((targetType == null) && (targetValue != null))
						{ javaScript = "$(\"[name='" + targetName + "'][value='" + targetValue + "']\").first().click();"; }
					else if ((targetType != null) && (targetValue != null))
						{ javaScript = "$(\"[name='" + targetName + "'][type='" + targetType + "'][value='" + targetValue + "']\").first().click();"; }
				}
			else if (targetLink != null)
				{ javaScript = "$(\"a:contains('" + targetLink + "')\")[0].click();"; }
			
			// Run JavaScript in main application thread
			Platform.runLater(new Runnable()
			{
				// http://stackoverflow.com/questions/21504681/javafx-webengine-executescript-from-another-thread
			    @Override
			    public void run()
			    {		    	
			    	if (javaScript != null)
			    	{
			    		//System.out.println("[javaScript] " + javaScript);// DEBUGGING
			    		// Execute the JavaScript
			    		webEngine.executeScript(javaScript);
			    	}
			    }
			});
		}
		
		return exists;
	}
	
	/**
	 * <b>clickAndWait</b><br>
	 * <br>
	 * Run click() and if successful, then also run andWait().<br>
	 * @return String - Result
	 */
	public static String clickAndWait()
	{
		Boolean clicked = click();
		
		if (clicked == false)
		{
			return ":: FAIL :: Executed test: clickAndWait, " + target + ". Could not find target.";
		}
		else
		{
			Boolean waited = andWait();
			
			if (waited == false)
			{
				return ":: FAIL :: Executed test: clickAndWait, " + target + ". Page failed to load or finish loading.";
			}
			else
			{
				return ":: PASS :: Executed test: clickAndWait, " + target;
			}
		}
	}
	
	/**
	 * <b>getLocation</b><br>
	 * <br>
	 * Get the current URL (with base address removed).<br>
	 * <br>
	 * @return String - URL of current page (with base address removed)
	 */
	public static String getLocation()
	{
		String baseAddress = TestStepsPane.baseAddresstextField.getText();
		
		// If the pageUrl is blank for whatever reason
		if (pageUrl.isEmpty())
		{ pageUrl = NavigationBar.addressBar.getText(); }
		
		// If the URL begins with the Base Address...
		if (pageUrl.startsWith(baseAddress))
		{
			int length = baseAddress.length();
			// Trim the URL to the bit after the base address
			pageUrl = pageUrl.substring( pageUrl.indexOf(baseAddress)+length );
			// If the URL ends up being blank, set it to "/".
			if (pageUrl.isEmpty()) { pageUrl = "/"; }
		}
		return pageUrl;
	}
	
	/**
	 * <b>goBack</b><br>
	 * <br>
	 * Execute the JavaScript command history.back()<br>
	 * <br>
	 * @return String - Result
	 */
	public static String goBack()
	{
		// http://stackoverflow.com/questions/21504681/javafx-webengine-executescript-from-another-thread
		Platform.runLater(new Runnable()
		{
		    @Override
		    public void run()
		    {
		    	String js = "history.back();";
		    	webEngine.executeScript(js);
		    }
		});
		
		return "Executed Test Step: goBack";
	}
	
	/**
	 * <b>goForward</b><br>
	 * <br>
	 * Execute the JavaScript command history.forward()<br>
	 * <br>
	 * @return String - Result
	 */
	public static String goForward()
	{
		// http://stackoverflow.com/questions/21504681/javafx-webengine-executescript-from-another-thread
		Platform.runLater(new Runnable()
		{
		    @Override
		    public void run()
		    {
		    	String js = "history.forward();";
				webEngine.executeScript(js);
		    }
		});
		
		return "Executed Test Step: goForward";
	}
	
	/**
	 * <b>open</b><br>
	 * <br>
	 * Execute the JavaScript command window.location.assign() using the given target URL.<br>
	 * <br>
	 * @return Boolean - Was the command able to run?
	 */
	public static Boolean open()
	{
		target = BaseUrlHelper.getFullUrlUsingBaseAddress(target);
		
		// Run function in main application thread
		Platform.runLater(new Runnable()
		{
			// http://stackoverflow.com/questions/21504681/javafx-webengine-executescript-from-another-thread
		    @Override
		    public void run()
		    {
		    	//WebViewPane.webView.getEngine().executeScript("window.location.assign(" + target + ")");
		    	WebViewPane.webView.getEngine().load(target);
		    }
		});
		return true;
	}
	
	/**
	 * <b>openAndWait</b><br>
	 * Execute the JavaScript command window.location.assign() using the target URL.<br>
	 * Will also wait until the WebEngine StateProperty Listener reports that a page has loaded,
	 * or it will timeout after 30 seconds.<br>
	 * <br>
	 * @return String - Result
	 */
	public static String openAndWait()
	{
		Boolean opened = open();
		if (opened == true)
			{
				Boolean waited = andWait();
				if (waited == false)
					{ return ":: FAIL :: Executed test: openAndWait, " + target + ". Page failed to load or finish loading."; }
				else
					{ return ":: PASS :: Executed test: openAndWait, " + target; }
			}
		else
			{ return ":: FAIL :: Executed Test Step: openAndWait, " + target + ". Failed to open target."; }
	}
	
	/**
	 * <b>refresh</b></br>
	 * <br>
	 * Refresh the WebView.<br>
	 * @return String - Result
	 */
	public static String refresh()
	{
		// http://stackoverflow.com/questions/21504681/javafx-webengine-executescript-from-another-thread
		Platform.runLater(new Runnable()
		{
		    @Override
		    public void run()
		    {
		    	WebViewPane.webView.getEngine().reload();
		    }
		});
		
		return "Executed Test Step: refresh";
	}
	
	/**
	 * <b>select</b></br>
	 * <br>
	 * Selects a select list item in a select list.<br>
	 * @return String - Result
	 */
	public static String select()
	{
		// Ensure the element exists before trying to click on it.
		Boolean exists = verifyElementPresent();
		if (exists == true)
		{
			String optionValue = value;
			
			// Trim off "label=" if it exists
			if (optionValue.startsWith("label=")) { optionValue = optionValue.substring(6); }
			
			javaScript = "$(\"option:contains('" + optionValue + "')\").prop('selected', true);";
			
			// Run JavaScript in main application thread (assuming the thread is ready)
			Platform.runLater(new Runnable()
			{
				// http://stackoverflow.com/questions/21504681/javafx-webengine-executescript-from-another-thread
			    @Override
			    public void run()
			    {
			    	if (javaScript != null)
			    	{
			    		System.out.println("[javaScript] " + javaScript);// DEBUGGING
			    		// Execute the JavaScript
			    		webEngine.executeScript(javaScript);
			    	}
			    }
			});
			
			return ":: PASS :: Executed Test Step: select " + target + ", " + value;
		}
		else
		{
			return ":: FAIL : Executed Test Step: select " + target + ", " + value + ". Could not find target.";
		}
	}
	
	/**<b>type</b><br>
	 * <br>
	 * Sets the val() of an input field. <br>
	 * @return String - Result
	 */
	public static String type()
	{
		// Ensure the element exists before trying to click on it.
		Boolean exists = verifyElementPresent();
		if (exists == true)
		{
			if (targetId != null)
				{ javaScript = "$(\"#" + targetId + "\").val('" + value + "');"; }
			else if (targetName != null) // Use CSS selectors with jQuery
				{
					if ((targetType == null) && (targetValue == null))
						{ javaScript = "$(\"[name='" + targetName + "']\").first().val('" + value + "')"; }
					else if ((targetType != null) && (targetValue == null))
						{ javaScript = "$(\"[name='" + targetName + "'][type='" + targetType + "']\").first().val('" + value + "');"; }
					else if ((targetType == null) && (targetValue != null))
						{ javaScript = "$(\"[name='" + targetName + "'][value='" + targetValue + "']\").first().val('" + value + "');"; }
					else if ((targetType != null) && (targetValue != null))
						{ javaScript = "$(\"[name='" + targetName + "'][type='" + targetType + "'][value='" + targetValue + "']\").first().val('" + value + "');"; }
				}
			else if (targetLink != null)
				{ javaScript = "$(\"a:contains('" + targetLink + "')\")[0].val(" + value + ");"; }
			
			// Run JavaScript in main application thread (assuming the thread is ready)
			Platform.runLater(new Runnable()
			{
				// http://stackoverflow.com/questions/21504681/javafx-webengine-executescript-from-another-thread
			    @Override
			    public void run()
			    {
			    	if (javaScript != null)
			    	{
			    		System.out.println("[javaScript] " + javaScript);// DEBUGGING
			    		// Execute the JavaScript
			    		webEngine.executeScript(javaScript);
			    	}
			    }
			});
			
			return ":: PASS :: Executed Test Step: type " + target + ", " + value;
		}
		else
		{
			return ":: FAIL :: Executed Test Step: type " + target + ", " + value + ". Could not find target.";
		}
	}
	
	/**
	 * <b>verifyElementPresent</b><br>
	 * <br>
	 * Verifies that element defined in the Test Step's target can be found in the current DOM.<br>
	 * Returns a Boolean because it may be called from another test.<br>
	 * @return Boolean - Is the element not null?
	 */
	public static Boolean verifyElementPresent()
	{
		// TODO: Xpath selector
		// the target can be "id=", "name=(+type,+name)", "link=", xpath
		
		Document document = null;
		// TODO: I am guessing getting the doc from the URL and not the frame will cause issues
		// with elements added via JS
		try { document = Jsoup.connect(NavigationBar.addressBar.getText()).get(); }
		catch (IOException e) { /* TODO Auto-generated catch block */ e.printStackTrace(); }
		//System.out.println(document.outerHtml()); //DEBUGGING
		
		Element targetElement = null;
		
		if (target.startsWith("id="))
		{
			// Trim off the "id=" bit at the front of the string
			targetId = target.substring(3);
			
			// Try to get the element
			targetElement = document.getElementById(targetId);
		}
		else if (target.startsWith("name=")) // name=sex type=radio value=male
		{
			// Trim off the "name=" bit at the front of the string
			targetName = target.substring(5);
			
			// Store and remove the value="" parameter (if it exists)
			if (target.contains("value=")) // this assumes that value is the last field
			{ 
				// Get the value of "value=" parameter before trimming the string
				targetValue = targetName.substring(targetName.indexOf("value=")+6);
				// Trim off the part of the string that starts with "value=" going to the end of the string
				targetName = targetName.substring(0, targetName.indexOf("value=")-1);
			}
			
			// Store and remove the type="" parameter (if it exists)
			if (targetName.contains("type=")) // this assumes that type is the last field (after value= gets removed)
			{
				// Get the value of "type=" parameter before trimming the string
				targetType = targetName.substring(targetName.indexOf("type=")+5);
				// Trim off the part of the string that starts with "type=" going to the end of the string
				targetName = targetName.substring(0, targetName.indexOf("type=")-1);
			}
			
			if ((targetType == null) && (targetValue == null))
			{
				// Get the first element with the matching name=""
				targetElement = document.getElementsByAttributeValue("name", targetName).first();
			}
			else if ((targetType != null) && ( targetValue == null))
			{
				// "[name={name}][type={targetType}]"
				targetElement = document.select("[name=" + targetName + "][type=" + targetType).first();
			}
			else if ((targetType == null) && ( targetValue != null))
			{
				// "[name={name}][value={targetValue}]"
				targetElement = document.select("[name=" + targetName + "][value=" + targetValue + "]").first();
			}
			else if ((targetType != null) && (targetValue != null))
			{
				// "[name={name}][type={targetType}][value={targetValue}]"
				targetElement = document.select("[name=" + targetName + "][type=" + targetType + "][value=" + targetValue + "]").first();
			}
		}
		else if (target.contains("link="))
		{
			// Trim off the "link=" bit at the front of the string
			targetLink = target.substring(5);
			
			// Get all anchors of the page
			Elements elements = document.getElementsByTag("a");
			
			// Loop over each and check it if contains our text content
			for (int i = 0; i < elements.size(); i++)
			{
				// Get the text content
				String textContent = elements.get(i).text();
				
				if (textContent.contains(targetLink))
				{
					targetElement = elements.get(i);
					break;
				}
			}
		}
		
		if (targetElement != null)
			{ return true; }
		else
			{ return false; }
	}
	
	/**
	 * <b>verifyUrl</b></br>
	 * <br>
	 * Check if the given URL is contained in the WebView's current URL.
	 * Contains is used due to the use of BaseAddress. So there is still a possibility of false positives.<br>
	 * @return Boolean - Does the current URL contain the {target}?
	 */
	public static Boolean verifyLocation()
	{
		String currentUrl = getLocation();
		
		if (currentUrl.contains(target))
			{ return true; }
		else
			{ return false; }
	}
	
	/**
	 * <b>verifyText</b><br>
	 * <br>
	 * Check if the given {target}'s text content equals the {target}.<br>
	 * @return Boolean - Does the target element's text content equal {target}?
	 */
	public static Boolean verifyText()
	{
		Boolean present = verifyElementPresent();
		
		if (present == true)
		{
			String text = targetElement.text();
			if (text.equals(target))
				{ return true; }
			else
				{ return false;}
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * <b>verifyTitle</b><br>
	 * <br>
	 * Check if the given title matches the title of the WebView's current page.<br>
	 * @return Boolean - Does the current page title equal the {target}?
	 */
	public static Boolean verifyTitle()
	{	
		if (pageTitle.equals(target))
			{ return true; }
		else
			{ return false; }
	}
	
	/**
	 * <b>waitForElementPresent</b><br>
	 * <br>
	 * Runs verifyElementPresent() until it returns true or it times out (30 seconds).<br>
	 * @return String - Result
	 */
	public static String waitForElementPresent()
	{
		Boolean present = false; 
		
		for (int i = 0; i < 120; i++)
		{
			present = verifyElementPresent();
			if(present == true)
			{
				break;
			}
			else
			{
				try
					{ Thread.sleep(250); }
				catch
					(InterruptedException e) { /* TODO Auto-generated catch block */ e.printStackTrace(); }
			}
		}
		
		if (present == true)
			{ return ":: PASS :: Executed Test Step: waitForElementPresent, " + target; }
		else
			{ return ":: FAIL :: Executed Test Step: waitForElementPresent, " + target + ". Could not find target or timed out."; }
	}
	
	/**
	 * <b>waitForLocation</b><br>
	 * <br>
	 * Runs verifyLocation() until it returns true or it times out (30 seconds).<br>
	 * @return String - Result
	 */
	public static String waitForLocation()
	{
		Boolean verified = false;
		for (int i = 0; i < 120; i++)
		{
			verified = verifyLocation();
			if (verified == true)
			{
				break;
			}
			else
			{
				try
					{ Thread.sleep(250); }
				catch
					(InterruptedException e) { /* TODO Auto-generated catch block */ e.printStackTrace(); }
			}
		}
		
		if (verified == true)
			{ return ":: PASS :: Executed Test Step: waitForLocation, " + target; }
		else
			{ return ":: FAIL :: Executed Test Step: waitForLocation, " + target + ". Page not found or timed out."; }
	}
	
	/**
	 * <b>waitForText</b><br>
	 * <br>
	 * Runs waitForText() until it returns true or it times out (30 seconds).<br>
	 * @return String - Result
	 */
	public static String waitForText()
	{
		Boolean verified = false;
		for (int i = 0; i < 120; i++)
		{
			verified = verifyText();
			if (verified == true)
			{
				break;
			}
			else
			{
				try
					{ Thread.sleep(250); }
				catch
					(InterruptedException e) { /* TODO Auto-generated catch block */ e.printStackTrace(); }
			}
		}
		
		if (verified == true)
			{ return ":: PASS :: Executed Test Step: waitForText, " + target; }
		else
			{ return ":: FAIL :: Executed Test Step: waitForText, " + target + ". Text not found or timed out."; }
	}
	
	/**
	 * <b>waitForTitle</b><br>
	 * Runs verifyTitle() until it returns true or it times out (30 seconds).<br>
	 * Description<br>
	 * @return String - Result
	 */
	public static String waitForTitle()
	{
		Boolean verified = false;
		for (int i = 0; i < 120; i++)
		{
			verified = verifyTitle();
			if (verified == true)
			{
				break;
			}
			else
			{
				try
					{ Thread.sleep(250); }
				catch
					(InterruptedException e) { /* TODO Auto-generated catch block */ e.printStackTrace(); }
			}
		}
		
		if (verified == true)
			{ return ":: PASS :: Executed Test Step: waitForTitle, " + target; }
		else
			{ return ":: FAIL :: Executed Test Step: waitForTitle, " + target + ". Title did not match before timing out."; }
	}
	
	/**
	 * <b>andWait</b><br>
	 * <br>
	 * Waits until pageLoaded is true or times out after 30 seconds.<br>
	 * @return Boolean - Did the page load?
	 */
	public static Boolean andWait()
	{
		// Wait pageLoaded to be true
		for (int i = 0; i < 120; i++) // 120 x 250ms = 30 seconds
		{
			if (pageLoaded == true)
				{ break; }
			else
				{ try { Thread.sleep(250); } catch (InterruptedException e) { /* TODO Auto-generated catch block */ e.printStackTrace(); } }
		}
		
		return pageLoaded;
	}
}