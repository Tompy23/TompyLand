package net.tompy.tl.packet;

import net.tompy.tl.component.CharacterData;

public class PacketStartCharacterGame extends Packet 
{
	private CharacterData info;
	
	public PacketStartCharacterGame() {}
	
	public PacketStartCharacterGame( CharacterData ci )
	{
		info = ci;
	}

	public CharacterData getInfo() {
		return info;
	}

	public void setInfo(CharacterData info) {
		this.info = info;
	}
}
