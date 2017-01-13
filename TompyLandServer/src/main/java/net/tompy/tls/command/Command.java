package net.tompy.tls.command;

import net.tompy.tls.game.TompyLandGame;
import net.tompy.tls.network.TompyLandServer;

public interface Command 
{
	public void execute( TompyLandGame game, TompyLandServer server );
}
