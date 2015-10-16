package copyleaks.sdk.api.helpers;

import java.io.File;

public final class FileHelpers
{
	public static String getFileName(File file)
	{
		String name = file.getName();
		try
		{
			return name.substring(0, name.lastIndexOf("."));
		}
		catch (Exception e)
		{
			return "";
		}
	}
	
	public static String getFileExtension(File file)
	{
		String name = file.getName();
		try
		{
			return name.substring(name.lastIndexOf(".") + 1);
		}
		catch (Exception e)
		{
			return "";
		}
	}
}
