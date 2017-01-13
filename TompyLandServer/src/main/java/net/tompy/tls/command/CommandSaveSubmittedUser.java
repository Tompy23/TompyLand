package net.tompy.tls.command;

import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.data.CharacterSerialize;
import net.tompy.tl.packet.PacketUserCreationComplete;
import net.tompy.tls.game.TompyLandGame;
import net.tompy.tls.network.TompyLandServer;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryonet.Connection;

public class CommandSaveSubmittedUser implements Command 
{
	private final static Logger log = Logger.getLogger( "CommandSaveSubmittedUser" );
	
	private CharacterSerialize characterInfo;
	private Connection connection;
	
	public CommandSaveSubmittedUser( CharacterSerialize ci, Connection c )
	{
		characterInfo = ci;
		connection = c;
		
		// Defaults
		characterInfo.setMapName( TompyLandConstants.TLSERVER_STARTING_MAP );
		characterInfo.setX( TompyLandConstants.TLSERVER_STARTX );
		characterInfo.setY( TompyLandConstants.TLSERVER_STARTY );
	}
	
	@Override
	public void execute(TompyLandGame game, TompyLandServer server) 
	{
		log.debug( "execute()" );
		
		// Save the user
		boolean saved = game.saveUser( characterInfo );
		
		// Send back that user is saved or that user is not saved
		server.sendToTCP( connection.getID(), new PacketUserCreationComplete( saved, characterInfo.getName() ) );
	}
}
