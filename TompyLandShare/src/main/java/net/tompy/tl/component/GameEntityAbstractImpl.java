package net.tompy.tl.component;

import net.tompy.ggt.component.Sprite;

public abstract class GameEntityAbstractImpl 
{
	protected long id;
	protected String name;
	protected Sprite sprite;
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	public String getImageFilename() {
		return sprite.getImageName();
	}
}
