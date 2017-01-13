package net.tompy.tl.data;

import net.tompy.tl.map.FeatureData;
import net.tompy.tl.map.HexData;

public class HexSerialize 
{
	private TerrainSerialize terrain;
	private FeatureSerialize[] features;
	private boolean slippery;
	private boolean flame;
	
	
	public HexSerialize() {}
	
	public HexSerialize( HexData h )
	{
		slippery = h.isSlippery();
		flame = h.isFlame();
		terrain = new TerrainSerialize( h.getTerrain() );
		features = new FeatureSerialize[ h.getFeatures().size() ];
		int i = 0;
		for ( FeatureData f : h.getFeatures() )
		{
			features[ i++ ] = new FeatureSerialize( f );
		}
	}

	public TerrainSerialize getTerrain() {
		return terrain;
	}

	public FeatureSerialize[] getFeatures() {
		return features;
	}

	public boolean isSlippery() {
		return slippery;
	}

	public boolean isFlame() {
		return flame;
	}

	public void setTerrain(TerrainSerialize terrain) {
		this.terrain = terrain;
	}

	public void setFeatures(FeatureSerialize[] features) {
		this.features = features;
	}

	public void setSlippery(boolean slippery) {
		this.slippery = slippery;
	}

	public void setFlame(boolean flame) {
		this.flame = flame;
	}
}
