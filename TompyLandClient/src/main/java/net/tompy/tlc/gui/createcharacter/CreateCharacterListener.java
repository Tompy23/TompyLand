package net.tompy.tlc.gui.createcharacter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCharacterListener implements ActionListener 
{
	CreateCharacterDialog ccd = null;

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if ( "OK".equals( arg0.getActionCommand() ) )
		{
			ccd.setCreated( true );
			ccd.setVisible( false );
		}
		else if ( "Cancel".equals( arg0.getActionCommand() ) )
		{
			ccd.setCreated( false );
			ccd.setVisible( false );
		}
	}

	public void setCcd(CreateCharacterDialog ccd) {
		this.ccd = ccd;
	}

}
