package net.tompy.tl.packet;

import net.tompy.tl.data.HexSerialize;

public class PacketMapCreatorIOPayload extends Packet 
{
	private String name;
	private int width;
	private int height;
	
	private HexSerialize[] hexes;
	
	public PacketMapCreatorIOPayload() {}
	
	public PacketMapCreatorIOPayload( String n, int w, int h, HexSerialize[] hs )
	{
		name = n;
		width = w;
		height = h;
		hexes = hs;
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
