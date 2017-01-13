package net.tompy.tlc.game;

import net.tompy.gameai.fsm.State;

import org.apache.log4j.Logger;

public class StateTompyLandPlayRunImpl extends StateTompyLandPlayAdaptor implements State<TompyLandPlay> 
{
	private final static Logger log = Logger.getLogger( "StateTompyLandPlayRunImpl" );
	
	@Override
	public void execute(TompyLandPlay me) 
	{
		// THIS FUNCTION OBVIOUSLY IS GOING TO HANDLE THE MAIN LOOP, 
		// SO, PREPARE TO DO A LOT OF WORK HERE.  :)
		if ( System.currentTimeMillis() >= start + 5000 )
		{
			log.debug( "execute()" );
			start = System.currentTimeMillis();
		}
		
		me.executeCommands();
	}
	
	public void enter(TompyLandPlay me) 
	{
		log.debug( "enter()" );
		// 1. Create the window
		// 2. set all the necessary variables
		// 3. Build the map data as necessary
		// 4. Set up all the entities (you and remember including other characters) in game lists
		// 5. show the map
		// 6. Hook up all the necessary listeners for input (possibly as part of the window setup)
	}

	@Override
	public void exit(TompyLandPlay me) 
	{
		log.debug( "exit()" );
		
		// Gracefully exit the game by telling the server the character is leaving the game,
		// the server will need to persist the character, then remove the character from
		// the sector lists, et.
	}

}
