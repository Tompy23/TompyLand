package net.tompy.tlc.game;

import org.apache.log4j.Logger;

import net.tompy.gameai.fsm.State;

public abstract class StateTompyLandPlayAdaptor implements State<TompyLandPlay> 
{
	private final static Logger log = Logger.getLogger( "StateTompyLandPlayAdaptor" );
	
	long start = 0;

	@Override
	public void execute(TompyLandPlay me) 
	{
		if ( System.currentTimeMillis() >= start + 5000 )
		{
			log.debug( "execute()" );
			start = System.currentTimeMillis();
		}
		
		me.executeCommands();
	}
}
