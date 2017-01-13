package net.tompy.tl.component;

import java.awt.Color;
import java.util.List;

import net.tompy.tl.map.FeatureData;

public interface CharacterData extends GameEntity
{
	public int getX();
	public int getY();
	public int getFacing();
	public String getMapName();
	public Color getColor();
	public List<FeatureData> getInventory();
	public void setX(int x);
	public void setY(int y);
	public void setFacing(int facing);
	public void setMapName(String mapName);
	public void setColor(Color color);

	public void setInventory(List<FeatureData> inventory);

}