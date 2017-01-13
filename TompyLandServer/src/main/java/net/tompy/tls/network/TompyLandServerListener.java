package net.tompy.tls.network;

import java.util.HashMap;
import java.util.Map;

import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.packet.Packet;
import net.tompy.tl.packet.PacketClientConnect;
import net.tompy.tl.packet.PacketMapCreatorConnect;
import net.tompy.tl.packet.PacketMapCreatorIOPayload;
import net.tompy.tl.packet.PacketMapCreatorLoadRequest;
import net.tompy.tl.packet.PacketMapCreatorRequestIOStatus;
import net.tompy.tl.packet.PacketSubmitNewCharacter;
import net.tompy.tls.command.CommandAddUserImpl;
import net.tompy.tls.command.CommandLoadMapImpl;
import net.tompy.tls.command.CommandLoadRequestImpl;
import net.tompy.tls.command.CommandMapCreatorConnectAcceptImpl;
import net.tompy.tls.command.CommandSaveMapImpl;
import net.tompy.tls.command.CommandSaveSubmittedUser;
import net.tompy.tls.game.TompyLandGame;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class TompyLandServerListener extends Listener
{
	private final static Logger log = Logger.getLogger( "TompyLandServerListener" );

	private Map< String, Connection > clients = new HashMap< String, Connection >();
	
	private TompyLandGame game;
	
	@Override
	public void received( Connection connection, Object object )
	{
		if ( object instanceof Packet )
		{
			if ( object instanceof PacketClientConnect )
			{
				PacketClientConnect pcc = (PacketClientConnect)object;
				log.debug( pcc.getUserName() + " connected" );
				
				connection.setName( pcc.getUserName() );
				clients.put( pcc.getUserName(), connection );
				
				game.sendCommand( new CommandAddUserImpl( pcc.getUserName(), connection ) );
			}
			else if ( object instanceof PacketSubmitNewCharacter )
			{
				PacketSubmitNewCharacter psnc = (PacketSubmitNewCharacter)object;
				log.debug( psnc.getInfo().getName() + " submitted new character" );
				
				game.sendCommand( new CommandSaveSubmittedUser( psnc.getInfo(), connection ) );
			}
			else if ( object instanceof PacketMapCreatorConnect )
			{
				PacketMapCreatorConnect pmcc = (PacketMapCreatorConnect)object;
				log.debug( pmcc.getMapName() + " connected" );
				
				game.sendCommand( new CommandMapCreatorConnectAcceptImpl( pmcc.getMapName(), connection ) );
			}
			else if ( object instanceof PacketMapCreatorIOPayload )
			{
				PacketMapCreatorIOPayload pmcs = (PacketMapCreatorIOPayload)object;
				log.debug( pmcs.getName() + " save requested" );
				
				// Gotta send the whole packet and let the command sort it out.
				game.sendCommand( new CommandSaveMapImpl( pmcs, connection ) );
			}
			else if ( object instanceof PacketMapCreatorLoadRequest )
			{
				PacketMapCreatorLoadRequest pmclr = (PacketMapCreatorLoadRequest)object;
				log.debug( "PacketMapCreatorLoadRequest" );
				
				if ( null == pmclr.getMapName() )
				{
					log.debug( "Requesting list..." );
					game.sendCommand( new CommandLoadRequestImpl( connection ) );
				}
				else
				{
					log.debug( "Requesting map " + pmclr.getMapName() );
					game.sendCommand( new CommandLoadMapImpl( pmclr.getMapName(), TompyLandConstants.TLMAP_SAVE_CONTINUE, connection ) );
				}
			}
			else if ( object instanceof PacketMapCreatorRequestIOStatus )
			{
				PacketMapCreatorRequestIOStatus pmcrs = (PacketMapCreatorRequestIOStatus)object;
				log.debug( "PacketMapCreatorRequestIOStatus" );
				log.debug( "Loading " + pmcrs.getMessage() + "[" + pmcrs.getStatus() + "]" );
				game.sendCommand( new CommandLoadMapImpl( pmcrs.getName(), pmcrs.getStatus(), connection ) );
			}
		}
	}
	
	@Override
	public void disconnected( Connection connection )
	{
		clients.remove( connection.toString() );
	}

	public void setGame(TompyLandGame game) {
		this.game = game;
	}
}
