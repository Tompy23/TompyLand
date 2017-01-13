package net.tompy.tl.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerrainDictionary 
{
	private Map< String, List< TerrainData > > terrainList = null;
	private Map< Long, TerrainData > terrainListId = null;
	
	private Map< String, List< FeatureData > > featureList = null;
	private Map< Long, FeatureData > featureListId = null;
	
	public void init()
	{
		terrainListId = new HashMap< Long, TerrainData >();
		for ( List< TerrainData > tList : terrainList.values() )
		{
			for ( TerrainData t : tList )
			{
				terrainListId.put( t.getId(), t );
			}
		}
		
		featureListId = new HashMap< Long, FeatureData >();
		for ( List< FeatureData > meList : featureList.values() )
		{
			for ( FeatureData me : meList )
			{
				featureListId.put( me.getType(), me );
			}
		}
	}
	
	public TerrainData getByID( long id )
	{
		return terrainListId.get( id );
	}
	
	public FeatureData createFeature( long type )
	{
		return new FeatureData( featureListId.get( type ) );
	}	

	public Map<String, List<TerrainData>> getTerrainList() {
		return terrainList;
	}

	public void setTerrainList(Map<String, List<TerrainData>> terrainList) {
		this.terrainList = terrainList;
	}

	public Map<String, List<FeatureData>> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(Map<String, List<FeatureData>> featureList) {
		this.featureList = featureList;
	}
}
