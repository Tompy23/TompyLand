package net.tompy.tlc.game;

import javax.swing.JDialog;

import net.tompy.gameai.fsm.State;
import net.tompy.tl.TompyLandConstants;
import net.tompy.tlc.command.Command;
import net.tompy.tlc.command.CommandSendNewCharacterImpl;
import net.tompy.tlc.gui.createcharacter.CreateCharacterDialog;
import net.tompy.tlc.gui.createcharacter.CreateCharacterListener;

import org.apache.log4j.Logger;

public class StateTompyLandPlayNewCharacterImpl extends StateTompyLandPlayAdaptor implements State<TompyLandPlay> 
{
	private final static Logger log = Logger.getLogger( "StateTompyLandPlayNewCharacterImpl" );

	@Override
	public void enter(TompyLandPlay me) 
	{
		log.debug( "enter()" );
		CreateCharacterDialog dialog = new CreateCharacterDialog( new CreateCharacterListener() );
		dialog.setUserName( me.getCharacter().getName() );
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		if ( dialog.isCreated() )
		{
			log.debug( "Character created." );
			me.getCharacter().setName( dialog.getUserName() );
			me.getCharacter().setColor( dialog.getColor() );
			dialog.dispose();
			
			// Create a command to send all info to server as "New Character"
			Command command = new CommandSendNewCharacterImpl();
			me.sendCommand( command );
		}
		else
		{
			log.debug( "Character canceled." );
			me.changeState( TompyLandConstants.TLGAME_INIT );
		}
	}

	@Override
	public void exit(TompyLandPlay me) 
	{
		log.debug( "exit()" );
	}

}
