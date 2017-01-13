package net.tompy.tl.packet;

public class PacketUserCreationComplete extends Packet 
{
	private boolean created;
	private String userName;
	
	public PacketUserCreationComplete() {}
	
	public PacketUserCreationComplete( boolean c, String un )
	{
		created = c;
		userName = un;
	}
	
	public boolean isCreated() {
		return created;
	}
	public void setCreated(boolean created) {
		this.created = created;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
