package net.tompy.tlc.game.mapcreator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import net.tompy.common.CommonException;
import net.tompy.gameai.fsm.StateFactory;
import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.data.MapSerialize;
import net.tompy.tl.map.MapData;
import net.tompy.tl.map.MapRealTimeData;
import net.tompy.tl.map.TerrainDictionary;
import net.tompy.tl.packet.PacketMapCreatorIOPayload;
import net.tompy.tl.packet.PacketMapCreatorLoadRequest;
import net.tompy.tl.packet.PacketMapCreatorRequestIOStatus;
import net.tompy.tlc.game.TompyLand;
import net.tompy.tlc.gui.mapcreator.MapCreatorApp;
import net.tompy.tlc.gui.mapcreator.MapCreatorLoadDialog;
import net.tompy.tlc.network.TompyLandClient;
import net.tompy.tlc.network.TompyLandClientListener;

public class TompyLandMapCreatorImpl implements TompyLandMapCreator, TompyLand
{
	private final static Logger log = Logger.getLogger( "TompyLandMapCreatorImpl" );

	private boolean useServer = false;
	
	private MapCreatorApp window;
	private TerrainDictionary terrainDictionary;
	
	// Map details
	private MapData map = null;
	
	// Network
	private TompyLandClient client;
	private TompyLandClientListener clientListener;
	
	// Map Saving
	private MapSerialize mapSave;
	private HexSerialize[] hexesSave;
	private int hexesSent;
	private boolean saving = false;
	private int chunkSize = 100;
	
	// Map Loading
	private MapLoader mapLoader = null;
	
	@Override
	public int process(ApplicationContext ctx, Properties properties) 
	{
		if ( useServer )
		{
			initClient();
			clientListener.setPlay( this );
			clientListener.setCreator( this );
			//Log.set( Log.LEVEL_TRACE );
		}

		window.setProcesser( this );
		window.setVisible( true );

		return TompyLandConstants.TL_SUCCESS;
	}

	@Override
	public MapData createMap( int w, int h )
	{
		log.debug( "Creating map with dimensions = [" + w + "," + h + "]");
		map = new MapRealTimeData( terrainDictionary.getByID( 0 ), w, h );	
		
		return map;
	}

	@Override
	public boolean saveMap( String mapName, int status ) 
	{
		boolean returnValue = true;
		
		log.debug( "Saving map [" + map.getName() + "]" );
		
		if ( ! saving )
		{
			saving = true;
			map.setName( mapName );
			mapSave = new MapSerialize( map );
			hexesSave = mapSave.getHexes();
		}
		
		log.debug( "Saving map [" + map.getName() + "], status is " + status + " and " + ( saving ? "saving" : "saved" ) );

		if ( saving && TompyLandConstants.TLMAP_SAVE_CONTINUE == status )
		{
			sendData( mapSave );
		}
		else
		{
			saving = false;
			mapSave = null;
			hexesSave = null;
			hexesSent = 0;
		}
		
		return returnValue;
	}
	
	private int sendData( MapSerialize mapSave )
	{
		log.debug( "Save..." );
		
		HexSerialize[] hexesSend;
		
		if ( hexesSave.length - hexesSent > chunkSize )
		{
			hexesSend = new HexSerialize[ chunkSize ];
		}
		else
		{
			hexesSend = new HexSerialize[ hexesSave.length - hexesSent ];
		}
		
		hexesSend = Arrays.copyOfRange( hexesSave, hexesSent, hexesSent + hexesSend.length );
		
		hexesSent += hexesSend.length;
		
		log.debug( "Sent " + hexesSent + " of " + hexesSave.length );
		
		return client.sendTCP( new PacketMapCreatorIOPayload( mapSave.getName(), mapSave.getWidth(), mapSave.getHeight(), hexesSend ) );		
	}
	

	@Override
	public void requestMapListFromServer() 
	{
		log.debug( "Requesting map list from server" );
		client.sendTCP( new PacketMapCreatorLoadRequest( null ) );
	}
	
	@Override
	public void displayLoadMapDialog(String[] mapList) 
	{
		MapCreatorLoadDialog mcld = new MapCreatorLoadDialog( mapList );
		
		mcld.setVisible( true );
		
		if ( mcld.isLoad() )
		{
			client.sendTCP( new PacketMapCreatorLoadRequest( mcld.getSelectedName() ) );
		}
	}

	@Override
	public int loadMap(String mapName, int width, int height, HexSerialize[] hexes ) 
	{
		if ( null == mapLoader )
		{
			mapLoader = new MapLoader( mapName, width, height );
		}
		
		int status = mapLoader.loadHexes( hexes );
		
		log.debug( "MapLoader status is [" + status + "]" );
		
		if ( TompyLandConstants.TLMAP_SAVE_CONTINUE != status )
		{
			log.debug( "Loading map is complete, removing maploader" );
			buildMap( mapLoader.getMapSerial() );
			mapLoader = null;
		}
		
		// Send the Request for status
		client.sendTCP( new PacketMapCreatorRequestIOStatus( status, mapName + " load " + status, mapName ) );
		
		return status;
	}
	
	private void buildMap( MapSerialize mapSerial )
	{
		map = new MapRealTimeData( mapSerial, terrainDictionary );
		
		// This is where we take the data from the MapSerial and create all the "stuff"
		// or we create a new MapRealTimeData constructor that takes a MapSerialize
		// that's probably better.
		
		window.createMap( map );
	}

	
	public void setClient(TompyLandClient client) {
		this.client = client;
	}

	public void setClientListener(TompyLandClientListener clientListener) {
		this.clientListener = clientListener;
	}

	@Override
	public void initClient()
	{
		try
		{
			client.init( clientListener );
		}
		catch ( IOException ioe )
		{
			ioe.printStackTrace();
			System.exit( TompyLandConstants.TLCLIENT_NO_CONNECT );
		}
	}
	
	// All the below represents normal Game AI stuff that isn't used, FSM, etc.
	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StateFactory getStateFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() throws CommonException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeState(String newState) {
		// TODO Auto-generated method stub
		
	}

	public void setUseServer(boolean useServer) {
		this.useServer = useServer;
	}

	public void setMapCreatorApp(MapCreatorApp window) {
		this.window = window;
	}

	public void setWindow(MapCreatorApp window) {
		this.window = window;
	}

	public void setTerrainDictionary(TerrainDictionary terrainDictionary) {
		this.terrainDictionary = terrainDictionary;
	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}
}
