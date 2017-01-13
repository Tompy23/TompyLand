package net.tompy.tls.command;

import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.packet.PacketMapCreatorRequestIOStatus;
import net.tompy.tl.packet.PacketMapCreatorIOPayload;
import net.tompy.tls.game.TompyLandGame;
import net.tompy.tls.network.TompyLandServer;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryonet.Connection;

public class CommandSaveMapImpl implements Command 
{
	private final static Logger log = Logger.getLogger( "CommandSaveMapImpl" );
	
	private String name;
	private int width;
	private int height;
	private HexSerialize[] hexes;
	private Connection connection;
	
	public CommandSaveMapImpl( PacketMapCreatorIOPayload pmcs, Connection c )
	{
		name = pmcs.getName();
		width = pmcs.getWidth();
		height = pmcs.getHeight();
		hexes = pmcs.getHexes();
		connection = c;
	}

	@Override
	public void execute(TompyLandGame game, TompyLandServer server) 
	{
		log.debug( "execute()" );
		
		// Collect the info and pass it along to the game object
		// when the game object has collected all the hexes, it will
		// do the save.
		
		log.debug( "Saving " + name );
		
		int status = game.saveMap( name, width, height, hexes );
		
		server.sendToTCP(connection.getID(), 
				new PacketMapCreatorRequestIOStatus( status, 
				name + " save " + status,
				name ) );
	}

}
