package net.tompy.tlc.command;

import net.tompy.tl.packet.PacketClientConnect;
import net.tompy.tlc.game.TompyLandPlay;
import net.tompy.tlc.network.TompyLandClient;

import org.apache.log4j.Logger;

public class CommandSendUserNameImpl implements Command 
{
	private final static Logger log = Logger.getLogger( "CommandSendUserNameImpl" );

	private String userName;
	
	public CommandSendUserNameImpl( String un )
	{
		userName = un;
	}
	
	@Override
	public void execute( TompyLandPlay play, TompyLandClient client ) 
	{
		log.debug( "execute()" );
		play.getCharacter().setName( userName );
		client.sendTCP( new PacketClientConnect( userName ) );
	}

}
