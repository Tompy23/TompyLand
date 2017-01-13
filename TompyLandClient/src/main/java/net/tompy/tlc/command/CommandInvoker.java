package net.tompy.tlc.command;

public interface CommandInvoker 
{
	public void sendCommand( Command c );
	public void executeCommands();	

}
