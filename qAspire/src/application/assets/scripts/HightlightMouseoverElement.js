// http://stackoverflow.com/a/6735955/1106708
prevElement = null;
document.addEventListener('mousemove', function(e)
{
	if (e.target.id != "cntnr")
	{
		var elem = e.target || e.srcElement;
		if (prevElement!= null)
		{
			prevElement.classList.remove("mouseOn");
		}
		elem.classList.add("mouseOn");
		
		if (prevElement != elem)
		{
			//CommunicateWithJavaFxApp();
		}
		
		prevElement = elem;
	}
	
	function CommunicateWithJavaFxApp()
	{
		// Check if the target.id has value, 
		if((e.target.id !== null) && (!!e.target.id))
			// If so set the 'target' in the JavaFX application to use the id
			{ app.setTarget("id=" + e.target.id); }
		// try the name attribute  
		else if ((e.target.name !== null) && (!!e.target.name))
			{ app.setTarget("name=" + e.target.name); }
		// try by href attribute
		else if ((e.target.href !== null) && (!!e.target.href))
			{ app.setTarget("link=" + e.target.href); }
		// other wise, send the css path instead
		else { var path = $(e.target).getPath(); app.setTarget("css=" + path); }
		
		// Get the target's text value, if any, and set as the 'value' in the JavaFX application
		app.setValue(e.target.text());
	}
  
	// http://stackoverflow.com/a/2068381/1106708
	jQuery.fn.getPath = function ()
	{
		if (this.length != 1) throw 'Requires one element.';
		
		var path, node = this;
		while (node.length)
		{
			var realNode = node[0], name = realNode.localName;
			if (!name) break;
			name = name.toLowerCase();
			
			var parent = node.parent();
			
			var siblings = parent.children(name);
			if (siblings.length > 1)
			{ 
				name += ':eq(' + siblings.index(realNode) + ')';
			}
			
		path = name + (path ? '>' + path : '');
		node = parent;
		}
		
		return path;
	};
},true);