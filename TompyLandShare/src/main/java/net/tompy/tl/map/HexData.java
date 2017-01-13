package net.tompy.tl.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class HexData implements Drawable
{
	private TerrainData terrain;
	private boolean flame = false;
	private boolean slippery = false;
	private int x;
	private int y;
	
	private List< FeatureData > features = new ArrayList< FeatureData >();
	
	public HexData() {}
	
	public void setTerrain(TerrainData t) 
	{
		terrain = t;
	}

	public TerrainData getTerrain() 
	{
		return terrain;
	}
	
	public void addFeature( FeatureData newFeature )
	{
		features.add( newFeature );
	}

	public void addFeature( FeatureData newFeature, int side )
	{
		newFeature.setHexside( true );
		newFeature.setSide( side );
		features.add( newFeature );
	}

	@Override
	public void draw( Graphics2D g2d, int x, int y )
	{
		terrain.draw( g2d, x, y );
		for ( FeatureData mf : features )
		{
			mf.draw( g2d, x, y );
		}
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<FeatureData> getFeatures() {
		return features;
	}

	public boolean isFlame() {
		return flame;
	}

	public boolean isSlippery() {
		return slippery;
	}

	public void setFlame(boolean flame) {
		this.flame = flame;
	}

	public void setSlippery(boolean slippery) {
		this.slippery = slippery;
	}
}
