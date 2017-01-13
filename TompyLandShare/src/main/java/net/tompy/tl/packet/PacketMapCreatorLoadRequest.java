package net.tompy.tl.packet;

public class PacketMapCreatorLoadRequest extends Packet 
{
	private String mapName;
	
	public PacketMapCreatorLoadRequest() {}
	
	public PacketMapCreatorLoadRequest( String mn )
	{
		mapName = mn;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
}
