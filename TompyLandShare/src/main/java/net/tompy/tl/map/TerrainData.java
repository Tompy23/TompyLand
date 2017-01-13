package net.tompy.tl.map;

import java.awt.Graphics2D;

import net.tompy.tl.component.GameEntity;
import net.tompy.tl.component.GameEntityAbstractImpl;

public class TerrainData extends GameEntityAbstractImpl implements Drawable, GameEntity
{
	private boolean passable;
	private int movementCost;
	private int attackMod;
	private int defenseMod;
	private boolean allowFire;  // shooting bows...
	private boolean allowSight;
	private boolean flammable;
	private long postFlameTerrainId;
	
//	public TerrainData( TerrainSerialize newTerrain )
//	{
//		passable = newTerrain.isPassable();
//		movementCost = newTerrain.getMovementCost();
//		attackMod = newTerrain.getAttackMod();
//		defenseMod = newTerrain.getDefenseMod();
//		allowFire = newTerrain.isAllowFire();
//		allowSight = newTerrain.isAllowSight();
//		flammable = newTerrain.isFlammable();
//		postFlameTerrainId = newTerrain.getPostFlameTerrainId();
//	}
	
	@Override
	public void draw( Graphics2D g2d, int x, int y )
	{
		sprite.draw( g2d, x, y );
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	public boolean isPassable() {
		return passable;
	}
	public int getMovementCost() {
		return movementCost;
	}
	public int getAttackMod() {
		return attackMod;
	}
	public int getDefenseMod() {
		return defenseMod;
	}
	public boolean isAllowFire() {
		return allowFire;
	}
	public boolean isAllowSight() {
		return allowSight;
	}
	public boolean isFlammable() {
		return flammable;
	}
	public long getPostFlameTerrainId() {
		return postFlameTerrainId;
	}
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
	public void setMovementCost(int movementCost) {
		this.movementCost = movementCost;
	}
	public void setAttackMod(int attackMod) {
		this.attackMod = attackMod;
	}
	public void setDefenseMod(int defenseMod) {
		this.defenseMod = defenseMod;
	}
	public void setAllowFire(boolean allowFire) {
		this.allowFire = allowFire;
	}
	public void setAllowSight(boolean allowSight) {
		this.allowSight = allowSight;
	}
	public void setFlammable(boolean flammable) {
		this.flammable = flammable;
	}
	public void setPostFlameTerrainId(long postFlameTerrainId) {
		this.postFlameTerrainId = postFlameTerrainId;
	}
}
