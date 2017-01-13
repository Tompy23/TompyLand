package net.tompy.tlc.game;

import net.tompy.tlc.command.CommandInvoker;
import net.tompy.tlc.entity.EntityCharacterImpl;

public interface TompyLandPlay extends TompyLand, CommandInvoker 
{	
	// User/Character
	public EntityCharacterImpl getCharacter();
}
