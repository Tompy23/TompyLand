package net.tompy.tls.command;

import org.apache.log4j.Logger;

import net.tompy.tl.packet.PacketMapCreatorLoadList;
import net.tompy.tls.game.TompyLandGame;
import net.tompy.tls.network.TompyLandServer;

import com.esotericsoftware.kryonet.Connection;

public class CommandLoadRequestImpl implements Command 
{
	private final static Logger log = Logger.getLogger( "CommandLoadRequestImpl" );

	private Connection connection;
	
	public CommandLoadRequestImpl( Connection c )
	{
		connection = c;
	}
	
	@Override
	public void execute(TompyLandGame game, TompyLandServer server) 
	{
		log.debug( "exeucte" );
		server.sendToTCP( connection.getID(), new PacketMapCreatorLoadList( game.getMapList() ) );
	}

}
