package net.tompy.tls.sector;

import java.util.Date;

import net.tompy.tl.map.MapData;

public class SectorRealTimeImpl extends SectorAbstractImpl implements Sector 
{
	// List of users in sector (never persisted, so should be set to null prior to saving)
	//private List< ServerUser > users;
	
	// Stats about sector
	private String dateCreated;
	private String dateModified = "NA";
	
	private MapData map;
	
	public SectorRealTimeImpl( MapData md )
	{
		map = md;
		Date d = new Date( System.currentTimeMillis() );
		dateCreated = d.toString();
	}

	public MapData getMap() {
		return map;
	}
}
