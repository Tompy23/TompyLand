package net.tompy.tl.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.tompy.tl.data.CharacterSerialize;
import net.tompy.tl.data.DataUtility;
import net.tompy.tl.data.FeatureSerialize;
import net.tompy.tl.map.FeatureData;
import net.tompy.tl.map.TerrainDictionary;


public class CharacterDataBasicImpl extends GameEntityAbstractImpl implements CharacterData 
{
	private int x;
	private int y;
	private int facing;
	private String mapName;
	private Color color;
	
	private List< FeatureData > inventory = new ArrayList< FeatureData >();
	
	public CharacterDataBasicImpl( CharacterSerialize info, TerrainDictionary terrainDictionary )
	{
		id = info.getId();
		name = info.getName();
		x = info.getX();
		y = info.getY();
		facing = info.getFacing();
		mapName = info.getMapName();
		color = DataUtility.SimpleColorToColor( info.getColor() );
		if ( null != info.getInventory() )
		{
			for ( int i = 0; i < info.getInventory().length; i++ )
			{
				FeatureSerialize newFeature = info.getInventory()[ i ];
				if ( newFeature.getType() > 0 )
				{
					FeatureData mapFeature = terrainDictionary.createFeature( newFeature.getType() ); 
					inventory.add( mapFeature );
					mapFeature.setId( newFeature.getId() );
					mapFeature.setState( newFeature.getState() );
					mapFeature.setSide( newFeature.getSide() );
					mapFeature.setHexside( newFeature.isHexside() );
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#getY()
	 */
	@Override
	public int getY() {
		return y;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#getFacing()
	 */
	@Override
	public int getFacing() {
		return facing;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#getMapName()
	 */
	@Override
	public String getMapName() {
		return mapName;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#getColor()
	 */
	@Override
	public Color getColor() {
		return color;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#getInventory()
	 */
	@Override
	public List<FeatureData> getInventory() {
		return inventory;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#setId(long)
	 */
	@Override
	public void setId(long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#setY(int)
	 */
	@Override
	public void setY(int y) {
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#setFacing(int)
	 */
	@Override
	public void setFacing(int facing) {
		this.facing = facing;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#setMapName(java.lang.String)
	 */
	@Override
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#setColor(net.tompy.tl.data.SimpleColor)
	 */
	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see net.tompy.tl.map.CharacterData2#setInventory(java.util.List)
	 */
	@Override
	public void setInventory(List<FeatureData> inventory) {
		this.inventory = inventory;
	}
}
