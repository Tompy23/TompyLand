package net.tompy.tl;

public class TompyLandConstants 
{
	// Game constants
	public static final String TLSERVER_STARTING_MAP = "0000_default";
	public static final int TLSERVER_STARTX = 3;
	public static final int TLSERVER_STARTY = 11;
	

	

	// Game states (for server)
	public static final String TLGAME_INIT = "tompyLandGameInit";
	public static final String TLGAME_RUN = "tompyLandGameRun";
	public static final String TLGAME_CREATE_USER = "tompyLandGameCreateUser";
	
	// Play states (for client)
	public static final String TLPLAY_INIT = "tompyLandPlayInit";
	public static final String TLPLAY_RUN = "tompyLandPlayRun";
	public static final String TLPLAY_NEW_CHARATER = "tompyLandPlayNewCharacter";

	
	
	// Map Constants
	public static final int TLMAP_SAVE_CONTINUE = 0;
	public static final int TLMAP_SAVE_SUCCESS = 1;
	public static final int TLMAP_SAVE_FAILED = 2;
	
	// Map types
	public static final int TLMAPTYPE_REALTIME = 0;
	public static final int TLMAPTYPE_TURNBASED = 1;
		
	// Entity names
	public static final int TLMAPENTITY_ENTITY_CLEAR = 0;
	public static final int TLMAPENTITY_ENTITY_IMPASSABLE = 1;
	public static final int TLMAPENTITY_ENTITY_TREASURECHEST = 2;
	public static final int TLMAPENTITY_ENTITY_WOODENDOOR = 3;
	public static final int TLMAPENTITY_ENTITY_KEY = 4;
	public static final int TLMAPENTITY_ENTITY_COINS = 5;
	
	
	
	// Return codes
	public static final int TL_SUCCESS = 0;
	public static final int TLSERVER_NOT_INITIALIZED = 1;
	public static final int TLCLIENT_NO_CONNECT = 2;
	public static final int TLCLIENT_CHARACTER_CANCELED = 3;
	public static final int TL_INIT_FAIL = 4;
	public static final int TLSERVER_IDFILE_ERROR = 5;
}
