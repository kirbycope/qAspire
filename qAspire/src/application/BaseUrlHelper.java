package application;

public class BaseUrlHelper
{
	/**
	 * <b>getFullUrlUsingBaseAddress</b><br>
	 * <br>
	 * Prepend the target with the BaseAddress.<br>
	 * <br>
	 * @param target - The test step's target value
	 * @return (String) baseAddress + target
	 */
	public static String getFullUrlUsingBaseAddress(String target)
	{
		// If the target URL starts with "/"...
		if (target.startsWith("/"))
		{
			// Get BaseAddress
			String baseAddress = TestStepsPane.baseAddresstextField.getText();
			// If base address ends with "/" and the target starts with "/", then remove the leading "/" from the target
			if (baseAddress.endsWith("/")) { target = target.substring(1); }
			// Add Base Address to any URL that starts with "/"
			target = baseAddress + target;
		}
		
		return target;
	}
	
	/**
	 * <b>removeBaseAddress</b><br>
	 * <br>
	 * Removes the Base Address from a given URL, if the URL starts with the Base Address.<br>
	 * <br>
	 * @param Url - The URL to trim
	 * @return
	 */
	public static String removeBaseAddress(String Url)
	{
		// Get the Base Address
		String baseAddress = TestStepsPane.baseAddresstextField.getText();
		
		// If the URL begins with the Base Address...
		if (Url.startsWith(baseAddress))
		{
			int length = baseAddress.length();
			// Trim the URL to the bit after the base address
			Url = Url.substring( Url.indexOf(baseAddress)+length );
			// If the URL ends up being blank, set it to "/".
			if (Url.isEmpty()) { Url = "/"; }
		}
		
		return Url;
	}
}