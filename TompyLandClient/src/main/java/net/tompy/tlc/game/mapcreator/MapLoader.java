package net.tompy.tlc.game.mapcreator;

import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.data.MapSerialize;

import org.apache.log4j.Logger;

public class MapLoader 
{
	private final static Logger log = Logger.getLogger( "MapSaver" );

	private String name;
	private int width;
	private int height;
	private MapSerialize mapSerial;
	private int totalHexes;
	private int loadedHexes = 0;
	private HexSerialize[] hexes;

	public MapLoader( String n, int w, int h )
	{
		width = w;
		height = h;
		totalHexes = w * h;
		hexes = new HexSerialize[ totalHexes ];
	}
	
	public int loadHexes( HexSerialize[] newHexes )
	{
		int returnValue = TompyLandConstants.TLMAP_SAVE_CONTINUE;

		for ( int i = 0; i < newHexes.length; i++ )
		{
			hexes[ loadedHexes++ ] = newHexes[ i ];
		}
		
		log.debug( "Loading " + name + ".  " + loadedHexes + " of " + totalHexes + " loaded so far." );

		if ( loadedHexes == totalHexes )
		{
			log.debug( "MapLoader's job is complete" );
			returnValue = TompyLandConstants.TLMAP_SAVE_SUCCESS;
			mapSerial = new MapSerialize();
			mapSerial.setName( name );
			mapSerial.setWidth( width );
			mapSerial.setHeight( height );
			mapSerial.setHexes( hexes );
			loadedHexes = 0;
		}
		
		return returnValue;
	}

	public MapSerialize getMapSerial() {
		return mapSerial;
	}
}
