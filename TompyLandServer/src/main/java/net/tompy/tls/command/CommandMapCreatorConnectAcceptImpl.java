package net.tompy.tls.command;

import org.apache.log4j.Logger;

import net.tompy.tl.packet.PacketMapCreatorConnect;
import net.tompy.tls.game.TompyLandGame;
import net.tompy.tls.network.TompyLandServer;

import com.esotericsoftware.kryonet.Connection;

public class CommandMapCreatorConnectAcceptImpl implements Command 
{
	private final static Logger log = Logger.getLogger( "CommandMapCreatorConnectAcceptImpl" );

	private String mapName;
	private Connection connection;
	
	public CommandMapCreatorConnectAcceptImpl( String mn, Connection c )
	{
		mapName = mn;
		connection = c;
	}
	
	@Override
	public void execute(TompyLandGame game, TompyLandServer server) 
	{
		log.debug( "execute" );
		log.debug( "Accepting connection for " + mapName );
		
		server.sendToTCP( connection.getID(), new PacketMapCreatorConnect( mapName ) );
	}

}
