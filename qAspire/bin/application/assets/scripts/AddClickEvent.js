$('body').on('click', function(e)
{
	if (e.target.id != "ContextMenuItem1")
	{
		// https://api.jquery.com/event.stoppropagation/
		//e.stopPropagation();
		
		/******************* Get the Selenium IDE locator **********************/
			// Create a variable to hold the target (locator)
			var target;
			
			// Locating by Id - http://www.seleniumhq.org/docs/02_selenium_ide.jsp#locating-by-id
			if ( !!$(e.target).attr("id") )
				{ target = "id=" + $(e.target).attr("id"); }
			// Locating by Name - http://www.seleniumhq.org/docs/02_selenium_ide.jsp#locating-by-name
			else if ( !!$(e.target).attr("name"))
				{
					target = "name=" + $(e.target).attr("name");
					
					if ( !!$(e.target).attr("type") )
						{ target = target + " type="+ $(e.target).attr("type"); }
					if ( !!$(e.target).attr("value") )
						{ target = target + " value=" + $(e.target).attr("value"); }
				}
			// Locating Hyperlinks by Link Text - http://www.seleniumhq.org/docs/02_selenium_ide.jsp#locating-hyperlinks-by-link-text
			else if ((e.target.tagName == "A") && (!!$(e.target).text()))
				{ target = "link=" + $(e.target).text(); }
			// Locating by XPath - http://www.seleniumhq.org/docs/02_selenium_ide.jsp#locating-by-xpath
			else
				{ target = $(e.target).getPath() }
		/***********************************************************************/
		
		/********************* Communicate with JavaFX app *********************/
			// Set Description
			app.setDescription("Click " + target);
			
			// Set Action
			app.setAction("click");
			
			// Set Target
			app.setTarget(target);
			
			// Set Value to blank as it is not needed for click event
			app.setValue("");
			
			// Add test step
			app.addTestSteps();
		/*************************************************************************/
	}
});