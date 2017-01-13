package net.tompy.tls.game;

import org.apache.log4j.Logger;

import net.tompy.gameai.fsm.State;
 
public class StateTompyLandGameRunImpl implements State< TompyLandGame > 
{
	private final static Logger log = Logger.getLogger( "StateTompyLandGameRunImpl" );
	
	private long updateDuration = 40;
	private long accumulatedTime = 0;
	
	@Override
	public void execute(TompyLandGame me) 
	{
		// This guy calls the big loop
		// me.run();
		long startTime = System.currentTimeMillis();
		
		// Constant updates, normally this would be painting, but
		// for a server, What would we do?
		// Possibly do a full game update?
		
		if ( accumulatedTime >= updateDuration )
		{
			me.executeCommands();
//			me.updateEntities();
			// Also I think this is where we update the clients?
			// or should we do this more frequently?
			// I feel this loop (here and outside) is where the 
			// learning will happen.
			accumulatedTime -= updateDuration;
		}
//		
		accumulatedTime += System.currentTimeMillis() - startTime;
	}

	@Override
	public void enter(TompyLandGame me) 
	{
		log.debug( "enter()" );
		me.initServer();
	}

	@Override
	public void exit(TompyLandGame me)
	{		
		log.debug( "exit()" );
	}

	public void setUpdateDuration(long updateDuration) {
		this.updateDuration = updateDuration;
	}
}
