package net.tompy.tls.game;

import net.tompy.gameai.Game;
import net.tompy.tl.component.CharacterData;
import net.tompy.tl.data.CharacterSerialize;
import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.map.MapData;
import net.tompy.tls.command.Command;

import com.esotericsoftware.kryonet.Connection;

public interface TompyLandGame extends Game
{
	public void initServer();
	
	public long getNewEntityId();
	
	public void sendCommand( Command c );
	public void executeCommands();
	
	// Game prepration
	public boolean prepareSession( String mapName );
	public boolean addUser( String sessionName, CharacterData character );
	public MapData getMapData( String mapName );
	
	// User initialization
	public CharacterData loadUser( String userName );
	public boolean saveUser( CharacterSerialize user );
	
	// Map Creation
	public int saveMap( String name, int width, int height, HexSerialize[] hexes );

	public String[] getMapList();
	public void loadMap( String name, int status, Connection connection );
}
