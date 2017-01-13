package net.tompy.tlc.game;

import java.util.ArrayList;
import java.util.List;

import net.tompy.common.CommonException;
import net.tompy.gameai.fsm.FSMCreatorGameImpl;
import net.tompy.gameai.fsm.FiniteStateMachine;
import net.tompy.gameai.fsm.State;
import net.tompy.tl.TompyLandConstants;
import net.tompy.tlc.command.Command;
import net.tompy.tlc.entity.EntityCharacterImpl;

public class TompyLandPlayImpl extends TompyLandBasicImpl implements TompyLandPlay
{
	// Game flow state machine
	private FiniteStateMachine< TompyLandPlay > fsm = null;

//	// Network
//	protected TompyLandClient client;
//	protected TompyLandClientListener clientListener;
	
	// Command List
	private List< Command > commands = new ArrayList< Command >();
	
	// Player
	private EntityCharacterImpl character;
	
	// Entities
	
	public void init()
	{
		fsm = new FSMCreatorGameImpl< TompyLandPlay >().create( this );
		clientListener.setPlay( this );
		character = new EntityCharacterImpl();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update() throws CommonException 
	{
		if ( fsm.isInState( null ) )
		{
			fsm.changeState( (State< TompyLandPlay >)stateFactory.createState( TompyLandConstants.TLPLAY_INIT ) );
		}
		fsm.update();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void changeState(String newState) 
	{
		fsm.changeState( (State< TompyLandPlay >)stateFactory.createState( newState ) );
	}

	@Override
	public void sendCommand(Command c) 
	{
		synchronized( commands )
		{
			commands.add( c );
		}
	}

	@Override
	public void executeCommands() 
	{
		synchronized( commands )
		{
			List< Command > removeList = new ArrayList< Command >();
			for ( Command c : commands )
			{
				c.execute( this, client );
				removeList.add( c );
			}
			commands.removeAll( removeList );
		}
	}

	public EntityCharacterImpl getCharacter() {
		return character;
	}



}
