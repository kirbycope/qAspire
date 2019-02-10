package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SeleniumCommands
{
	static ObservableList<String> options = create();
	
	/**
	 * <b>create</b><br>
	 * <br>
	 * Create a list of all Selenium commands available Selenium IDE.<br>
	 * <br>
	 * @return ObservableList - Contains the ComboBox selection options
	 */
	static ObservableList<String> create()
	{
		if (options == null)
		{
			options = FXCollections.observableArrayList(
					"addLocationStrategy",
					"addScript",
					"addSelection",
					"allowNativeXpath",
					"altKeyDown",
					"altKeyUp",
					"answerOnNextPrompt",
					"assignId",
					"break",
					"captureEntirePageScreenshot",
					"check",
					"chooseCancelOnNextConfirmation",
					"chooseOkOnNextConfirmation",
					"click",
					"clickAt",
					"close",
					"contextMenu",
					"contextMenuAt",
					"controlKeyDown",
					"controlKeyUp",
					"createCookie",
					"deleteAllVisibleCookies",
					"deleteCookie",
					"deselectPopUp",
					"doubleClick ",
					"doubleClickAt",
					"dragAndDrop",
					"dragAndDropToObject",
					"dragdrop",
					"echo",
					"fireEvent",
					"focus",
					"goBack",
					"highlight",
					"ignoreAttributesWithoutValue",
					"keyDown",
					"keyPress",
					"keyUp",
					"metaKeyDown",
					"metaKeyUp",
					"mouseDown",
					"mouseDownAt",
					"mouseDownRight",
					"mouseDownRightAt",
					"mouseMove",
					"mouseMoveAt",
					"mouseOut",
					"mouseOver",
					"mouseUp",
					"mouseUpAt",
					"mouseUpRight",
					"mouseUpRightAt",
					"open",
					"openWindow",
					"pause",
					"refresh",
					"removeAllSelections",
					"removeScript",
					"removeSelection",
					"rollup",
					"runScript",
					"select",
					"selectFrame",
					"selectPopUp",
					"selectWindow",
					"setBrowserLogLevel",
					"setCursorPosition",
					"setMouseSpeed",
					"setSpeed",
					"setTimeout",
					"shiftKeyDown",
					"shiftKeyUp",
					"store",
					"submit",
					"type",
					"typeKeys",
					"uncheck",
					"useXpathLibrary",
					"waitForCondition",
					"waitForFrameToLoad",
					"waitForPageToLoad",
					"waitForPopUp",
					"windowFocus",
					"windowMaximize"
		    );
		}
		
		return options;
	}
}