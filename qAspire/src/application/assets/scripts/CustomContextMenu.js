// JavaFX app Step Builder - Global Values
var description;
var action;
var target;
var value;

// http://stackoverflow.com/a/706728/1106708
$(document).ready(function()
{
	// Disable default context menu
	document.oncontextmenu = function() {return false;};
    
	$(document).mousedown(function(e)
    {
		// If mouse right-click was pressed
		if ( e.button == 2 )
		{
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
				{ target = $(e.target).getPath(); }

			// Get Value
			value = $(e.target).text();
			
			// Open context menu at mouse position
			addHTML();
			e.preventDefault();
			$("#cntnr").css("left",e.pageX);
			$("#cntnr").css("top",e.pageY);
			$("#cntnr").fadeIn(500,startFocusOut());
		}
	});
});

function startFocusOut()
{
	$(document).on("click",function()
	{
		$("#cntnr").hide(500);        
		$(document).off("click");
	});
}

function addHTML()
{
	htmlString = 
		  "	<div id='cntnr'>"
		+ "		<ul id='items'>"
		+ "			<li id='ContextMenuItem1'>Add Properties to Step Builder</li>"
		+ "		</ul>"
		+ "	</div>";
		
	// If the element doesn't already exist on the page, then add it
	if( $('#cntnr').length === 0 )
	{
		$(htmlString).appendTo("body");
		$("#items > li").click(function()
		{
			if ( $(this).text() == "Add Properties to Step Builder" )
			{
				app.setTarget(target);
				app.setValue(value);
			}
			else
			{
				app.printToJavaConsole( $(this).text() );
			}
		});
	}
}