package net.tompy.tlc.network;

import javax.swing.JOptionPane;

import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.packet.Packet;
import net.tompy.tl.packet.PacketMapCreatorConnect;
import net.tompy.tl.packet.PacketMapCreatorIOPayload;
import net.tompy.tl.packet.PacketMapCreatorLoadList;
import net.tompy.tl.packet.PacketMapCreatorRequestIOStatus;
import net.tompy.tl.packet.PacketRequestCharacterCreation;
import net.tompy.tl.packet.PacketStartCharacterGame;
import net.tompy.tl.packet.PacketUserCreationComplete;
import net.tompy.tl.packet.PacketUserStartGame;
import net.tompy.tlc.game.TompyLand;
import net.tompy.tlc.game.mapcreator.TompyLandMapCreator;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class TompyLandClientListener extends Listener 
{
	private final static Logger log = Logger.getLogger( "TompyLandClientListener" );
	
	private TompyLand play;
	private TompyLandMapCreator creator;

	@Override
	public void received( Connection connection, Object object )
	{
		if ( object instanceof Packet )
		{
			if ( object instanceof PacketMapCreatorConnect )
			{
				log.debug( "PacketMapCreatorConnect" );
			}
			else if ( object instanceof PacketMapCreatorRequestIOStatus )
			{
				log.debug( "PacketMapCreatorRequestStatus" );
				PacketMapCreatorRequestIOStatus pmcrs = (PacketMapCreatorRequestIOStatus)object;
				log.debug( "[" + pmcrs.getStatus() + "]: " + pmcrs.getMessage() );
				creator.saveMap( pmcrs.getName(), pmcrs.getStatus() );
			}
			else if ( object instanceof PacketMapCreatorLoadList )
			{
				log.debug( "PacketMapCreatorLoadList" );
				PacketMapCreatorLoadList pmcll = (PacketMapCreatorLoadList)object;
				creator.displayLoadMapDialog( pmcll.getMapList() );
			}
			else if ( object instanceof PacketMapCreatorIOPayload )
			{
				log.debug("PacketMapCreatorIOPayload" );
				PacketMapCreatorIOPayload pmcpl = (PacketMapCreatorIOPayload)object;
				creator.loadMap( pmcpl.getName(), pmcpl.getWidth(), pmcpl.getHeight(), pmcpl.getHexes() );
			}
			else if ( object instanceof PacketRequestCharacterCreation )
			{
				log.debug( "PacketRequestCharacterCreation" );
				play.changeState( TompyLandConstants.TLPLAY_NEW_CHARATER );
			}
			else if ( object instanceof PacketStartCharacterGame )
			{
				log.debug( "PacketStartCharacterGame" );
				play.changeState( TompyLandConstants.TLPLAY_RUN );
			}
			else if ( object instanceof PacketUserCreationComplete )
			{
				PacketUserCreationComplete packet = (PacketUserCreationComplete) object;
				if ( packet.isCreated() )
				{
					JOptionPane.showMessageDialog( null, 
							packet.getUserName() + " is created.  You may now log in as this user and play." );
				}
				else
				{
					JOptionPane.showMessageDialog( null,
							packet.getUserName() + " is not created.  Please try again later.",
							"Character not created",
							JOptionPane.INFORMATION_MESSAGE );
										
				}
				log.debug( "PacketUserCreationComplete" );
				play.changeState( TompyLandConstants.TLGAME_INIT );
			}
			else if ( object instanceof PacketUserStartGame )
			{
				PacketUserStartGame packet = (PacketUserStartGame) object;
				log.debug( "PacketStartCharacterGame" );
				log.debug( packet.getCharacter().getName() );
				// 1. Convert all the network data to the game data
				// 2. Build all the Game data necessary to handle and assign to "play" object
				// 3. Change state to RunImpl, where all the actual game start things happen
				//    (See state for details)
			}
			// Future Packets
			// Update Map
			// * Will contain changes to the map sine the last time the info was sent
			// Remap Map
			// * Send the entire map data, could be quite different than what user has (just an option)
			// Update Character
			// * Updates the character itself with new info
			// * This could include the characters hirelings too
		}
	}

	public void setPlay(TompyLand play) {
		this.play = play;
	}

	public void setCreator(TompyLandMapCreator creator) {
		this.creator = creator;
	}
}
