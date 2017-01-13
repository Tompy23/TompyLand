package net.tompy.tl.packet;

public class PacketUserSendMessage extends Packet 
{
	private String message;
	
	public PacketUserSendMessage() {} 
	
	public PacketUserSendMessage( String m )
	{
		message = m;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
