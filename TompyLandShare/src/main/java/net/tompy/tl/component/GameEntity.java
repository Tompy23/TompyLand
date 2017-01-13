package net.tompy.tl.component;


public interface GameEntity 
{
	public long getId();
	public String getName();
	
	public void setId( long id );
	public void setName( String name );
	
	public String getImageFilename();
}
