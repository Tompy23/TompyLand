package net.tompy.tlc.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.tompy.tl.data.CharacterSerialize;
import net.tompy.tl.data.DataUtility;
import net.tompy.tl.packet.PacketSubmitNewCharacter;
import net.tompy.tlc.game.TompyLandPlay;
import net.tompy.tlc.network.TompyLandClient;



public class CommandSendNewCharacterImpl implements Command 
{
	private final static Logger log = LogManager.getLogger( "CommandSendNewCharacterImpl" );

	@Override
	public void execute(TompyLandPlay play, TompyLandClient client) 
	{
		log.debug( "execute()" );
		CharacterSerialize ci = new CharacterSerialize();
		ci.setColor( DataUtility.ColorToSimpleColor( play.getCharacter().getColor() ) );
		ci.setName( play.getCharacter().getName() );
		
		client.sendTCP( new PacketSubmitNewCharacter( ci ) );
	}

}
