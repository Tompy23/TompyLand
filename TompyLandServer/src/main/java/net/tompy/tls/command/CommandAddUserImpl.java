package net.tompy.tls.command;

import net.tompy.tl.component.CharacterData;
import net.tompy.tl.data.CharacterSerialize;
import net.tompy.tl.data.MapSerialize;
import net.tompy.tl.packet.PacketRequestCharacterCreation;
import net.tompy.tl.packet.PacketUserSendMessage;
import net.tompy.tl.packet.PacketUserStartGame;
import net.tompy.tls.game.TompyLandGame;
import net.tompy.tls.network.TompyLandServer;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryonet.Connection;


public class CommandAddUserImpl implements Command 
{
	private final static Logger log = Logger.getLogger( "CommandAddUserImpl" );

	private String userName;
	private Connection connection;
	
	public CommandAddUserImpl( String un, Connection c )
	{
		userName = un;
		connection = c;
	}
	
	@Override
	public void execute(TompyLandGame game, TompyLandServer server) 
	{
		log.debug( "execute()" );
		CharacterData character = game.loadUser( userName );
		if ( null != character )
		{
			log.debug( "loaded " + character.getName() );
			// Sessions are named by map name
			log.debug( "Retrieving/Creating " + character.getMapName() );
			if ( game.prepareSession( character.getMapName() ) )
			{
				// Add user to the session
				log.debug( "Adding " + character.getName() + " to " + character.getMapName() );
				if ( game.addUser( character.getMapName(), character ) )
				{
					// Send the user the Packet with user and map so client can start the game
					server.sendToTCP( connection.getID(), 
							new PacketUserStartGame( new CharacterSerialize( character ), 
									new MapSerialize( game.getMapData( character.getMapName() ) ) ) );
					// Send all other users in the session a heads up that a new character is entering their map
					server.sendToAllExceptTCP( connection.getID(), new PacketUserSendMessage( "User " + character.getName() + " has joined." ) );
				}
				else
				{
					server.sendToTCP( connection.getID(), new PacketUserSendMessage( "Failed to add " + character.getName() + " to server session, user already exists" ) );
				}
			}
			else
			{
				log.debug( "Issues with adding user " + userName );
				// Send user the fact that the session failed
				server.sendToTCP( connection.getID(), new PacketUserSendMessage( "Failed to add " + character.getName() + " to server session" ) );
			}
			
		}
		else
		{
			// This user does not exist
			// 1. Let the client know he needs to create a user and
			//    submit it.
			log.debug( userName + " is a new character, requesting character creation" );
			server.sendToTCP( connection.getID(), new PacketRequestCharacterCreation( userName ) );
		}
	}

}
