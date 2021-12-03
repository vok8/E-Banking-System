import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;
public class PropertiesMetalTheme extends DefaultMetalTheme 
{
	private String name="Custom Theme";
	private ColorUIResource primary1;
	private ColorUIResource primary2;
	private ColorUIResource primary3;
	private ColorUIResource secondary1;
	private ColorUIResource secondary2;
	private ColorUIResource secondary3;
	private ColorUIResource black;
	private ColorUIResource white;
	public PropertiesMetalTheme(InputStream stream) 
	{
		initColors();
		loadProperties(stream);
	}

	private void initColors() 
	{
		primary1=super.getPrimary1();
		primary2=super.getPrimary2();
		primary3=super.getPrimary3();
		secondary1=super.getSecondary1();
		secondary2=super.getSecondary2();
		secondary3=super.getSecondary3();
		black=super.getBlack();
		white=super.getWhite();
	}

	private void loadProperties(InputStream stream) 
	{
		Properties prop=new Properties();		
		try 
		{
			prop.load(stream);
		}
		catch(IOException e) //Catch IOException
		{
			System.out.println(e);
		}
		Object temp=prop.get("name");
		if(temp!=null) 
		{
			name=temp.toString();
		}
		Object color_string=null;
		color_string=prop.get("primary1");		
		if(color_string!=null)
		{
			primary1=parseColor(color_string.toString());
		}
		color_string=prop.get("primary2");
		if(color_string!=null) 
		{
			primary2=parseColor(color_string.toString());
		}
		color_string=prop.get("primary3");
		if(color_string!=null) 
		{
			primary3=parseColor(color_string.toString());
		}
		color_string=prop.get("secondary1");
		if(color_string!=null) 
		{
			secondary1=parseColor(color_string.toString());
		}
		color_string=prop.get("secondary2");
		if(color_string!=null) 
		{
			secondary2=parseColor(color_string.toString());
		}
		color_string=prop.get("secondary3");
		if(color_string!=null) 
		{
			secondary3=parseColor(color_string.toString());
		}
		color_string=prop.get("black");
		if(color_string!=null) 
		{
			black=parseColor(color_string.toString());
		}
		color_string=prop.get("white");
		if(color_string!=null) 
		{
			white=parseColor(color_string.toString());
		}
	}

	public String getName()
	{ 
		return name; 
	}

	protected ColorUIResource getPrimary1() 
	{ 
		return primary1; 
	}
	protected ColorUIResource getPrimary2() 
	{ 
		return primary2; 
	}
	protected ColorUIResource getPrimary3() 
	{ 
		return primary3; 
	}
	protected ColorUIResource getSecondary1() 
	{ 
		return secondary1; 
	}
	protected ColorUIResource getSecondary2() 
	{ 
		return secondary2; 
	}
	protected ColorUIResource getSecondary3() 
	{ 
		return secondary3; 
	}
	protected ColorUIResource getBlack() 
	{ 
		return black; 
	}
	protected ColorUIResource getWhite() 
	{ 
		return white; 
	}

	private ColorUIResource parseColor(String s) 
	{
		int red=0;
		int green=0;
		int blue=0;
		try 
		{
			StringTokenizer st=new StringTokenizer(s,",");
			red=Integer.parseInt(st.nextToken());
			green=Integer.parseInt(st.nextToken());
			blue=Integer.parseInt(st.nextToken());
		}
		catch(Exception e) //Catch Exception
		{
			System.out.println(e);
			System.out.println("Couldn't parse Color :" + s);
		}
		return new ColorUIResource(red, green, blue);
	}
}