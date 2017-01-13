package net.tompy.tl.data;

import net.tompy.tl.component.CharacterData;
import net.tompy.tl.map.FeatureData;

public class CharacterSerialize
{
	private long id;
	private String name;
	private String mapName;
	private int x;
	private int y;
	private int facing;
	private SimpleColor color;
	
	private FeatureSerialize[] inventory;
	
	public CharacterSerialize() {}
	
	public CharacterSerialize( CharacterData character )
	{
		id = character.getId();
		name = character.getName();
		mapName = character.getMapName();
		x = character.getX();
		y = character.getY();
		facing = character.getFacing();
		color = DataUtility.ColorToSimpleColor( character.getColor() );
		inventory = new FeatureSerialize[ character.getInventory().size() ];
		int i = 0;
		for ( FeatureData f : character.getInventory() )
		{
			inventory[ i++ ] = new FeatureSerialize( f );
		}
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMapName() {
		return mapName;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getFacing() {
		return facing;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

	public void setInventory(FeatureSerialize[] inventory) {
		this.inventory = inventory;
	}

	public SimpleColor getColor() {
		return color;
	}

	public FeatureSerialize[] getInventory() {
		return inventory;
	}

	public void setColor(SimpleColor color) {
		this.color = color;
	}
}
