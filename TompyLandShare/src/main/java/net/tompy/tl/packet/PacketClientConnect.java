package net.tompy.tl.packet;

public class PacketClientConnect extends Packet 
{
	private String userName;
	
	public PacketClientConnect() {}
	
	public PacketClientConnect( String un )
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
