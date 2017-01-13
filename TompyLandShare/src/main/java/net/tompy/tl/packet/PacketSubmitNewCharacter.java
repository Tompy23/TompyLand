package net.tompy.tl.packet;

import net.tompy.tl.data.CharacterSerialize;

public class PacketSubmitNewCharacter extends Packet
{
	private CharacterSerialize info;
	
	public PacketSubmitNewCharacter() {}
	
	public PacketSubmitNewCharacter( CharacterSerialize ci )
	{
		info = ci;
	}

	public CharacterSerialize getInfo() {
		return info;
	}

	public void setInfo(CharacterSerialize info) {
		this.info = info;
	}
}
