package net.tompy.tl.data;

import net.tompy.tl.map.FeatureData;

public class FeatureSerialize 
{
	private long id;
	private long type;
	private int state;
	private int side;
	private boolean hexside;
	
	public FeatureSerialize() {}
	
	public FeatureSerialize( FeatureData f )
	{
		id = f.getId();
		
		state = f.getState();
		side = f.getSide();
		hexside = f.isHexside();
		type = f.getType();
	}

	public int getState() {
		return state;
	}

	public int getSide() {
		return side;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public boolean isHexside() {
		return hexside;
	}

	public void setHexside(boolean hexside) {
		this.hexside = hexside;
	}
}
