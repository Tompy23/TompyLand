package net.tompy.tl.data;

import java.awt.Color;

public class DataUtility 
{
	public static SimpleColor ColorToSimpleColor( Color c )
	{
		float[] components = new float[ 4 ];
		c.getRGBComponents( components);
		return new SimpleColor( components );
	}
	
	public static Color SimpleColorToColor( SimpleColor sc )
	{
		float[] components = sc.getComponents();
		return new Color( components[ 0 ], components[ 1 ], components[ 2 ], components[ 3 ] );
	}

}
