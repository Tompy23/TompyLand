package net.tompy.tl.data;

import net.tompy.tl.map.HexData;
import net.tompy.tl.map.MapData;


public class MapSerialize 
{
	private String name;
	private int width;
	private int height;
	
	private HexSerialize[] hexes;
	
	public MapSerialize() {}
	
	public MapSerialize( MapData map )
	{
		name = map.getName();
		width = map.getWidth();
		height = map.getHeight();
		hexes = new HexSerialize[ width * height ];
		int i = 0;
		for ( HexData h : map.getHexes() )
		{
			hexes[ i++ ] = new HexSerialize( h );
		}
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public HexSerialize[] getHexes() {
		return hexes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setHexes(HexSerialize[] hexes) {
		this.hexes = hexes;
	}
}
