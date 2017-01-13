package net.tompy.tlc.game;

import javax.swing.JOptionPane;

import net.tompy.gameai.fsm.State;
import net.tompy.tl.TompyLandConstants;
import net.tompy.tlc.command.CommandSendUserNameImpl;

import org.apache.log4j.Logger;

public class StateTompyLandPlayInitImpl extends StateTompyLandPlayAdaptor implements State<TompyLandPlay> 
{
	private final static Logger log = Logger.getLogger( "StateTompyLandPlayInitImpl" );
	   
	@Override
	public void enter(TompyLandPlay me) 
	{
		log.debug( "enter()" );
		me.initClient();
		String userName = JOptionPane.showInputDialog( "Enter user name" );
		if ( null != userName )
		{
			me.sendCommand( new CommandSendUserNameImpl( userName ) );
		}
		else
		{
			System.exit( TompyLandConstants.TLCLIENT_CHARACTER_CANCELED );
		}
	}

	@Override
	public void exit(TompyLandPlay me) 
	{
		log.debug( "exit()" );
	}

}
