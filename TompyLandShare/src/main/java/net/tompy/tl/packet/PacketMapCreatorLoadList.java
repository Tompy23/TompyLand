package net.tompy.tl.packet;

public class PacketMapCreatorLoadList extends Packet 
{
	private String[] mapList;
	
	public PacketMapCreatorLoadList() {}
	
	public PacketMapCreatorLoadList( String[] un )
	{
		mapList = un;
	}

	public String[] getMapList() {
		return mapList;
	}

	public void setMapList(String[] mapList) {
		this.mapList = mapList;
	}

}
