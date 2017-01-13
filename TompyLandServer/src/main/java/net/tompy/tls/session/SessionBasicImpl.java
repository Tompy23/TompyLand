package net.tompy.tls.session;

import java.util.ArrayList;
import java.util.List;

import net.tompy.tl.component.CharacterData;
import net.tompy.tl.map.MapData;

public class SessionBasicImpl implements Session 
{
	protected MapData map;
	protected List< CharacterData > users = new ArrayList< CharacterData >();
	
	public SessionBasicImpl( MapData newMap )
	{
		map = newMap;
	}

	@Override
	public void addCharacter( CharacterData newCharacter) 
	{
		users.add( newCharacter );
	}

	@Override
	public MapData getMap() 
	{
		return map;
	}
}
