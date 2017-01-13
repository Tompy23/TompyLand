package net.tompy.tl.packet;

public class PacketMapCreatorRequestIOStatus extends Packet 
{
	private String name;
	private int status;
	private String message;
	
	public PacketMapCreatorRequestIOStatus() {}
	
	public PacketMapCreatorRequestIOStatus( int s, String m, String n )
	{
		name = n;
		status = s;
		message = m;
	}
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
