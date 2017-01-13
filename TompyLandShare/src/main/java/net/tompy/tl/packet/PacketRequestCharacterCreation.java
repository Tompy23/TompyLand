package net.tompy.tl.packet;

public class PacketRequestCharacterCreation extends Packet 
{
	private String userName;
	
	public PacketRequestCharacterCreation() {}
	
	public PacketRequestCharacterCreation( String un )
	{
		userName = un;
	}

	public String getUserName() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}
}
