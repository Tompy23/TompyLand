package net.tompy.tls.command;

import net.tompy.tls.game.TompyLandGame;
import net.tompy.tls.network.TompyLandServer;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryonet.Connection;

public class CommandLoadMapImpl implements Command 
{
	private final static Logger log = Logger.getLogger( "CommandLoadMapImpl" );

	private Connection connection;
	private String mapName;
	private int status;
	
	public CommandLoadMapImpl( String mn, int s, Connection c )
	{
		connection = c;
		mapName = mn;
		status = s;
	}
	
	@Override
	public void execute(TompyLandGame game, TompyLandServer server) 
	{
		log.debug( "execute" );
		log.debug( "Loading " + mapName + ", status is " + status );
		
		game.loadMap( mapName, status, connection );
	}

}
