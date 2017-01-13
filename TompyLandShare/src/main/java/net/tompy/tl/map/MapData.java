package net.tompy.tl.map;

import java.util.List;

public interface MapData 
{
	public HexData getHex( int x, int y );
	public int getWidth();
	public int getHeight();
	public String getName();
	
	public void setName( String name );
	
	public List< HexData > getHexes();
}
