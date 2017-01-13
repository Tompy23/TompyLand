package net.tompy.tl.map;

import java.awt.Graphics2D;

import net.tompy.tl.component.GameEntity;
import net.tompy.tl.component.GameEntityAbstractImpl;
import net.tompy.tl.data.FeatureSerialize;

public class FeatureData extends GameEntityAbstractImpl implements Drawable, GameEntity
{
	private long type = 0;
	
	private int state = 0;
	private int side = 0;
	private boolean hexside = false;
	
	public FeatureData() {}
	
	public FeatureData( FeatureData mf )
	{
		sprite = mf.getSprite();
		type = mf.getType();
		id = mf.getId();
		hexside = mf.isHexside();
	}
	
	public FeatureData( FeatureSerialize newFeature )
	{
		hexside = newFeature.isHexside();
		type = newFeature.getType();
		id = newFeature.getId();
	}
	
	@Override
	public void draw(Graphics2D g2d, int x, int y) 
	{
		g2d.rotate( side*Math.PI/3.0, x + 60, y + 60  );
		sprite.draw( g2d, x, y );
		g2d.rotate( -1*side*Math.PI/3.0, x + 60, y + 60  );
	}

	@Override
	public String toString()
	{
		return name;
	}

	public long getType() {
		return type;
	}

	public int getState() {
		return state;
	}

	public void setType(long type) {
		this.type = type;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public boolean isHexside() {
		return hexside;
	}

	public void setHexside(boolean hexside) {
		this.hexside = hexside;
	}
}
