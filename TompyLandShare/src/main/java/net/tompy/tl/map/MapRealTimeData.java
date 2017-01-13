package net.tompy.tl.map;

import java.util.ArrayList;
import java.util.List;

import net.tompy.tl.data.FeatureSerialize;
import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.data.MapSerialize;

public class MapRealTimeData implements MapData
{
	private List< HexData > hexes = new ArrayList< HexData >();
	private int width;
	private int height;
	private String name;
	
	public MapRealTimeData() {}
	
	public MapRealTimeData( int w, int h )
	{
		width = w;
		height = h;
	}
	
	public MapRealTimeData( TerrainData t, int w, int h )
	{
		width = w;
		height = h;

		for ( int y = 0; y < height; y++ )
		{
			for ( int x = 0; x < width; x++ )
			{
				HexData newHex = new HexData();
				hexes.add( newHex );
				newHex.setX( x );
				newHex.setY( y );
				newHex.setTerrain( t );
			}
		}
	}
	
	public MapRealTimeData( MapSerialize newMap, TerrainDictionary terrainDictionary )
	{
		name = newMap.getName();
		width = newMap.getWidth();
		height = newMap.getHeight();
		
		HexSerialize[] newHexes = newMap.getHexes();
		
		for ( int y = 0; y < height; y++ )
		{
			for ( int x = 0; x < width; x++ )
			{
				HexSerialize newHex = newHexes[ ( width * y ) + x ];
				
				HexData mapHex = new HexData();
				hexes.add( mapHex );
				mapHex.setX( x );
				mapHex.setY( y );
				mapHex.setTerrain( terrainDictionary.getByID( newHex.getTerrain().getId() ) );
				
				for ( int i = 0; i < newHex.getFeatures().length; i++ )
				{
					FeatureSerialize newFeature = newHex.getFeatures()[ i ];
					if ( newFeature.getType() > 0 )
					{
						FeatureData mapFeature = terrainDictionary.createFeature( newFeature.getType() ); 
						mapHex.addFeature( mapFeature );
						mapFeature.setId( newFeature.getId() );
						mapFeature.setState( newFeature.getState() );
						mapFeature.setSide( newFeature.getSide() );
						mapFeature.setHexside( newFeature.isHexside() );
					}
				}
			}
		}
	}

	public HexData getHex( int w, int h )
	{
		HexData returnValue = null;
		
		if ( w <= width && h <= height && w >= 0 && h >= 0 )
		{
			returnValue = hexes.get( ( width * h ) + w );
		}
		
		return returnValue;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HexData> getHexes() {
		return hexes;
	}
}
