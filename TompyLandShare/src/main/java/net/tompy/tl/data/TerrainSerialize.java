package net.tompy.tl.data;

import net.tompy.tl.map.TerrainData;

public class TerrainSerialize 
{
	private long id;
	private boolean passable;
	private int movementCost;
	private int attackMod;
	private int defenseMod;
	private boolean allowFire;  // shooting bows...
	private boolean allowSight;
	private boolean flammable;
	private long postFlameTerrainId;
	
	public TerrainSerialize() {}
	
	public TerrainSerialize( TerrainData t )
	{
		id = t.getId();
		passable = t.isPassable();
		movementCost = t.getMovementCost();
		attackMod = t.getAttackMod();
		defenseMod = t.getDefenseMod();
		allowFire = t.isAllowFire();
		allowSight = t.isAllowSight();
		flammable = t.isFlammable();
		postFlameTerrainId = t.getPostFlameTerrainId();
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
