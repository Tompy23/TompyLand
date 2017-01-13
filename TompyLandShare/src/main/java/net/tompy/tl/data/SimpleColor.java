package net.tompy.tl.data;


public class SimpleColor 
{
	private float[] components = new float[4]; 
	
	public SimpleColor() {}
	
	public SimpleColor( float[] comp )
	{
		components = comp;
	}
	
	public SimpleColor( float r, float g, float b, float a )
	{
		components[ 0 ] = r;
		components[ 1 ] = g;
		components[ 2 ] = b;
		components[ 3 ] = a;
	}
	
	public float[] getComponents()
	{
		return components;
	}
}
