package net.tompy.tls.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tompy.common.CommonException;
import net.tompy.gameai.GameAbstractImpl;
import net.tompy.gameai.fsm.FSMCreatorGameImpl;
import net.tompy.gameai.fsm.FiniteStateMachine;
import net.tompy.gameai.fsm.State;
import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.component.CharacterData;
import net.tompy.tl.component.CharacterDataBasicImpl;
import net.tompy.tl.data.CharacterSerialize;
import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.data.MapSerialize;
import net.tompy.tl.map.MapData;
import net.tompy.tl.map.MapRealTimeData;
import net.tompy.tl.map.TerrainDictionary;
import net.tompy.tl.packet.PacketMapCreatorIOPayload;
import net.tompy.tls.command.Command;
import net.tompy.tls.network.TompyLandServer;
import net.tompy.tls.network.TompyLandServerListener;
import net.tompy.tls.session.Session;
import net.tompy.tls.session.SessionBasicImpl;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryonet.Connection;
public class TompyLandGameImpl extends GameAbstractImpl implements TompyLandGame 
{
	private final static Logger log = Logger.getLogger( "TompyLandGameImpl" );
	
	// Game flow state machine
	private FiniteStateMachine< TompyLandGame > fsm = null;
	
	// Network
	private TompyLandServer server;
	private TompyLandServerListener serverListener;
	
	// Command
	private List< Command > commands = new ArrayList< Command >();
	
	// Entities
	private String idFilename;
	private long lastId = 100000;
	private List< CharacterData > users = new ArrayList< CharacterData >();
	
	// Sessions
	private Map< String, Session > sessions = new HashMap< String, Session >();
	
	// Serialization
	private String characterPersistDirectory;
	private String sectorPersistDirectory;
	private Map< String, MapSaver > mapSavers = new HashMap< String, MapSaver >();
	private TerrainDictionary terrainDictionary;
	
	// Map Saving/Loading
	private MapSerialize mapLoad;
	private HexSerialize[] hexesLoad;
	private int hexesSent;
	private boolean loading = false;
	private int chunkSize = 100;
	
	
	public void init()
	{
		fsm = new FSMCreatorGameImpl< TompyLandGame >().create( this );
		serverListener.setGame( this );
		
		try
		{
			File idFile = new File( idFilename );
			if ( idFile.exists() )
			{
				DataInputStream in = new DataInputStream( new FileInputStream( idFile ) );
				lastId = in.readLong();
				in.close();
			}
			else
			{
				DataOutputStream out = new DataOutputStream( new FileOutputStream( idFile ) );
				out.writeLong( lastId );
				out.close();
			}
		}
		catch ( FileNotFoundException fnfe )
		{
			fnfe.printStackTrace();
			System.exit( TompyLandConstants.TLSERVER_IDFILE_ERROR );
		}
		catch ( IOException ioe )
		{
			ioe.printStackTrace();
			System.exit( TompyLandConstants.TLSERVER_IDFILE_ERROR );
		}
		
		log.debug( "last id: " + lastId );
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update() throws CommonException 
	{
		if ( fsm.isInState( null ) )
		{
			fsm.changeState( (State< TompyLandGame >)stateFactory.createState( TompyLandConstants.TLGAME_RUN ) );
		}
		fsm.update();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void changeState( String newState ) 
	{
		fsm.changeState( (State< TompyLandGame >)stateFactory.createState( newState ) );
	}
	
	@Override
	public void initServer()
	{
		server.init( serverListener );
	}
	

	@Override
	public void sendCommand( Command c ) 
	{
		synchronized( commands )
		{
			commands.add( c );
		}
	}

	@Override
	public void executeCommands() 
	{
		synchronized( commands )
		{
			List< Command > removeList = new ArrayList< Command >();
			for ( Command c : commands )
			{
				c.execute( this, server );
				removeList.add( c );
			}
			commands.removeAll( removeList );
		}
	}
	
	@Override
	public CharacterData loadUser( String userName ) 
	{
		CharacterData newUser = null;
		CharacterSerialize cip;
		
		Kryo k = new Kryo();
		//SerialRegistration.register( k );
		
		String filename = getUserFilename( userName );
		
		if ( new File( filename ).exists() )
		{	
			Input in = new Input();
			
			try
			{
				in.setBuffer( new byte[ 500 ] );
				in.setInputStream( new FileInputStream( filename ) );
				cip = k.readObject( in, CharacterSerialize.class );
				in.close();
				
				newUser = new CharacterDataBasicImpl( cip, terrainDictionary );
				
				users.add( newUser );
			}
			catch( FileNotFoundException fnfe )
			{
				log.debug( "Unable to read from " + filename );
				newUser = null;
				fnfe.printStackTrace();			
			}
		}
		
		return newUser;
	}
	
	@Override
	public boolean saveUser( CharacterSerialize user )
	{
		boolean saved = true;
		
		if ( 0 == user.getId() )
		{
			user.setId( getNewEntityId() );
		}

		// Persist the CharacterInfo
		Kryo k = new Kryo();
		
		String filename = getUserFilename( user.getName() );
		
		Output out = new Output();
		try
		{
			out.setBuffer( new byte[ 500 ] );
			out.setOutputStream( new FileOutputStream( filename ) );
			k.writeObject( out, user );
			out.close();
		}
		catch( FileNotFoundException fnfe )
		{
			log.debug( "Unable to write to " + filename );
			saved = false;
			fnfe.printStackTrace();
		}		
		
		return saved;
	}
	

	@Override
	public boolean prepareSession( String mapName ) 
	{
		boolean returnValue = true;
		MapData map = null;
		
		if ( ! sessions.containsKey( mapName ) )
		{
			MapSerialize rawMap = getMapFromFile( getSectorFilename( mapName ) );
			if ( null != rawMap )
			{
				map = new MapRealTimeData( rawMap, terrainDictionary );
				sessions.put( mapName, new SessionBasicImpl( map ) );
			}
			else
			{
				returnValue = false;
			}
		}
		
		return returnValue;
	}

	@Override
	public boolean addUser(String sessionName, CharacterData character) 
	{
		boolean returnValue = false;
		
		if ( ! sessions.containsKey( sessionName ) )
		{
			sessions.get( sessionName ).addCharacter( character );
			returnValue = true;
		}
		
		return returnValue;
	}
	
	private String getUserFilename( String un )
	{
		return characterPersistDirectory + un + ".char";
	}
	
	@Override
	public MapData getMapData(String mapName) 
	{
		MapData map = null;
		
		if ( sessions.containsKey( mapName ) )
		{
			Session s = sessions.get(mapName );
			map = s.getMap();
		}
		
		return map;
	}

	@Override
	public int saveMap( String name, int width, int height, HexSerialize[] hexes ) 
	{
		if ( ! mapSavers.containsKey( name ) )
		{
			mapSavers.put( name, new MapSaver( name, width, height, getSectorFilename( name ), this ) );
		}
		
		int status = mapSavers.get( name ).save( hexes );
		
		log.debug( "MapSaver status is [" + status + "]" );
		
		if ( TompyLandConstants.TLMAP_SAVE_CONTINUE != status )
		{
			log.debug( "Map saver is complete, removing map saver" );
			mapSavers.remove( name );
		}

		return status;
	}
	
	private String getSectorFilename( String un )
	{
		return sectorPersistDirectory + un + ".map";
	}
	
	@Override
	public String[] getMapList() 
	{
		File folder = new File( sectorPersistDirectory );	
		File[] fileList = folder.listFiles();
		String[] returnValue = new String[ fileList.length ];
		int i = 0;
		for ( File f : fileList )
		{
			if ( f.isFile() )
			{
				returnValue[ i++ ] = f.getName().substring( 0, f.getName().indexOf( ".map" ) );
			}
		}
		Arrays.sort( returnValue );
		
		return returnValue;
	}

	@Override
	public void loadMap( String name, int status, Connection connection ) 
	{
		log.debug( "Loading " + name );
			
		if ( ! loading )
		{
			loading = true;
			
//				// Load the map
//				Kryo k = new Kryo();
//				Input in = new Input( 1024*16 );
//				in.setInputStream( new FileInputStream( getSectorFilename( name ) ) );
//				mapLoad = k.readObject( in, MapSerialize.class );	
			mapLoad = getMapFromFile( getSectorFilename( name ) );
			hexesLoad = mapLoad.getHexes();
		}

		if ( loading && TompyLandConstants.TLMAP_SAVE_CONTINUE == status )
		{
			sendData( mapLoad, connection );
		}
		else
		{
			loading = false;
			hexesSent = 0;
		}
	}
	
	private MapSerialize getMapFromFile( String filename )
	{
		MapSerialize returnValue = null;
		
		try
		{
			// Load the map
			Kryo k = new Kryo();
			Input in = new Input( 1024*16 );
			in.setInputStream( new FileInputStream( filename ) );
			returnValue = k.readObject( in, MapSerialize.class );
		}
		catch ( FileNotFoundException fnfe )
		{
			fnfe.printStackTrace();
		}
		
		return returnValue;
	}

	private void sendData( MapSerialize mapLoad, Connection connection )
	{
		log.debug( "Load..." );
		
		HexSerialize[] hexesSend;
		
		if ( hexesLoad.length - hexesSent > chunkSize )
		{
			hexesSend = new HexSerialize[ chunkSize ];
		}
		else
		{
			hexesSend = new HexSerialize[ hexesLoad.length - hexesSent ];
		}
		
		hexesSend = Arrays.copyOfRange( hexesLoad, hexesSent, hexesSent + hexesSend.length );
		
		hexesSent += hexesSend.length;
		
		log.debug( "Sent " + hexesSent + " of " + hexesLoad.length );
		
		server.sendToTCP( connection.getID(), new PacketMapCreatorIOPayload( mapLoad.getName(), mapLoad.getWidth(), mapLoad.getHeight(), hexesSend ) );		
	}

	@Override
	public synchronized long getNewEntityId() 
	{
		try
		{
			DataOutputStream out = new DataOutputStream( new FileOutputStream( new File( idFilename ) ) );
			out.writeLong( lastId );
			out.close();
		}
		catch ( FileNotFoundException fnfe )
		{
			fnfe.printStackTrace();
			System.exit( TompyLandConstants.TLSERVER_IDFILE_ERROR );
		}
		catch ( IOException ioe )
		{
			ioe.printStackTrace();
			System.exit( TompyLandConstants.TLSERVER_IDFILE_ERROR );
		}

		return ++lastId;
	}
	
	public void setServer(TompyLandServer server) {
		this.server = server;
	}

	public void setServerListener(TompyLandServerListener serverListener) {
		this.serverListener = serverListener;
	}

	public void setCharacterPersistDirectory(String characterPersistDirectory) {
		this.characterPersistDirectory = characterPersistDirectory;
	}

	public void setSectorPersistDirectory(String sectorPersistDirectory) {
		this.sectorPersistDirectory = sectorPersistDirectory;
	}

	public void setIdFilename(String idFilename) {
		this.idFilename = idFilename;
	}

	public void setTerrainDictionary(TerrainDictionary terrainDictionary) {
		this.terrainDictionary = terrainDictionary;
	}
}
