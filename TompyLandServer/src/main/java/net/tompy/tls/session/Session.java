package net.tompy.tls.session;

import net.tompy.tl.component.CharacterData;
import net.tompy.tl.map.MapData;

public interface Session 
{
	public void addCharacter( CharacterData newCharacter );
	public MapData getMap();
}
