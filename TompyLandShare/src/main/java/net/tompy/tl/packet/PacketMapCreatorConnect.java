package net.tompy.tl.packet;

public class PacketMapCreatorConnect extends Packet 
{
	private String mapName;
	
	public PacketMapCreatorConnect() {}
	
	public PacketMapCreatorConnect( String mn )
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
