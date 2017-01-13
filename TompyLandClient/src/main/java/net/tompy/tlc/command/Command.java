package net.tompy.tlc.command;

import net.tompy.tlc.game.TompyLandPlay;
import net.tompy.tlc.network.TompyLandClient;

public interface Command 
{
	public void execute( TompyLandPlay play, TompyLandClient client );
}
