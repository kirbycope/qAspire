function runTest(command, target, value)
{
	// Clear testRunner in case another test is still executing
	clearTimeout(testRunner);
	
    // TESTING GLOBALS
	var passOrFail = "test did not execute"; 	// used for test result
	var intervalTime = 250; 	// used for setInterval time interval
	var triesUntilFail = 8; 	// used to limit setIterval
	var triesSoFar = 0; 		// used to track setInterval iterations
	var testRunner; 			// used for the setInterval target
    
    // http://www.w3schools.com/jsref/met_win_setinterval.asp
    if (command == "click")
    { click(target); }
	else if (command == "deleteAllVisibleCookies")
    { deleteAllVisibleCookies(); }
    else if (command == "goBack")
    { goBack(); }
    else if (command == "open")
    { open(target); }
    else if (command == "refresh")
    { refresh(); }
    else if (command == "waitForElementPresent")
    { testRunner = setInterval(function(){waitForElementPresent(target);}, intervalTime); }
    else if (command == "waitForText")
    { testRunner = setInterval(function(){waitForText(target, value);}, intervalTime); }
	else if (command == "waitForTitle")
	{ testRunner = setInterval(function(){waitForTitle(target, value);}, intervalTime); }
	
	return passOrFail;
	
	/* Selenium IDE Command - "click" */
	function click(target)
	{
		var locator = target;
		if (locator.substring(0, 2) == "id") // id=gb_1
		{
			locator = locator.substring(3);
		}
		$("\"#" + locator + "\"").click(); passOrFail = "pass";
	}
	
	/* Selenium IDE Command - "deleteAllVisibleCookies" */
	function deleteAllVisibleCookies()
	{
		http://stackoverflow.com/a/5886746/1106708
		// This function will attempt to remove a cookie from all paths.
		var pathBits = location.pathname.split('/');
		var pathCurrent = ' path=';

		// do a simple pathless delete first.
		document.cookie = name + '=; expires=Thu, 01-Jan-1970 00:00:01 GMT;';

		for (var i = 0; i < pathBits.length; i++) {
			pathCurrent += ((pathCurrent.substr(-1) != '/') ? '/' : '') + pathBits[i];
			document.cookie = name + '=; expires=Thu, 01-Jan-1970 00:00:01 GMT;' + pathCurrent + ';';
		}
		
		passOrFail = "pass";
	}

	/* Selenium IDE Command - "goBack" */
	function goBack()
	{ history.back(); passOrFail = "pass"; }

	/* Selenium IDE Command - "open" */
	function open(target)
	{ window.location.assign(target); passOrFail = "pass"; }

	/* Selenium IDE Command - "refresh" */
	function refresh()
	{ location.reload(); passOrFail = "pass"; }

	/* Selenium IDE Command - "waitForElementPresent" */
	function waitForElementPresent(target)
	{
		// Check if the element exists
		if ( $(target).length > 0 )
		{
			//console.log("Element present!");
			passOrFail = "pass";
		}
		
		// Check to see if the test is complete
		checkTestStatus();
	}

	/* Selenium IDE Command - "waitForText" */
	function waitForText(target, value)
	{
		// Check if the element exists
		if ( $(target).length > 0)
		{
			// check that the text content matches the supplied value
			if ( $(target).text() == value)
			{
				//console.log("Text matches!");
				passOrFail = "pass";
			}
		}
		
		// Check to see if the test is complete
		checkTestStatus();
	}

	/* Selenium IDE Command - "waitForTitle" */
	function waitForTitle(target)
	{
		// check the page title
		if (document.title == target)
		{
			//console.log("Title matches!");
			passOrFail = "pass";
		}
		
		// Check to see if the test is complete
		checkTestStatus();
	}
	
	/* Check to see if the test needs to keep running */
	function checkTestStatus()
	{
		triesSoFar++;
		
		// If the test has exhausted its attempts OR has passed
		if ( (triesSoFar >= triesUntilFail) || (passOrFail == "pass") )
		{
			// Stop the setInterval
			clearTimeout(testRunner);
		}
	}
}