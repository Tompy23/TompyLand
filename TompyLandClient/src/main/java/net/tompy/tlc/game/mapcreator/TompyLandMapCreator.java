package net.tompy.tlc.game.mapcreator;

import net.tompy.common.CommonProcess;
import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.map.MapData;

public interface TompyLandMapCreator extends CommonProcess
{
	public MapData createMap( int w, int h );
	
	public boolean saveMap( String mapName, int status );
	
	public void requestMapListFromServer();
	public void displayLoadMapDialog( String[] mapList );
	public int loadMap( String mapName, int width, int height, HexSerialize[] hexes );
}
