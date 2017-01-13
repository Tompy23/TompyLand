package net.tompy.tl.packet;

import net.tompy.tl.data.CharacterSerialize;
import net.tompy.tl.data.MapSerialize;

public class PacketUserStartGame extends Packet 
{
	private CharacterSerialize character;
	private MapSerialize map;

	public PacketUserStartGame() {}
	
	public PacketUserStartGame( CharacterSerialize c, MapSerialize m )
	{
		character = c;
		map = m;
	}

	public CharacterSerialize getCharacter() {
		return character;
	}

	public MapSerialize getMap() {
		return map;
	}

	public void setCharacter(CharacterSerialize character) {
		this.character = character;
	}

	public void setMap(MapSerialize map) {
		this.map = map;
	}
	
}
