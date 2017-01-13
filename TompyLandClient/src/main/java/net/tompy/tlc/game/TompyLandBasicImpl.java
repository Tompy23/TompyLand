package net.tompy.tlc.game;

import java.io.IOException;

import net.tompy.gameai.GameAbstractImpl;
import net.tompy.tl.TompyLandConstants;
import net.tompy.tlc.network.TompyLandClient;
import net.tompy.tlc.network.TompyLandClientListener;

public abstract class TompyLandBasicImpl extends GameAbstractImpl implements TompyLand
{
	// Network
	protected TompyLandClient client;
	protected TompyLandClientListener clientListener;
	
	@Override
	public void initClient()
	{
		try
		{
			client.init( clientListener );
		}
		catch ( IOException ioe )
		{
			ioe.printStackTrace();
			System.exit( TompyLandConstants.TLCLIENT_NO_CONNECT );
		}
	}

	public void setClient(TompyLandClient client) {
		this.client = client;
	}

	public void setClientListener(TompyLandClientListener clientListener) {
		this.clientListener = clientListener;
	}
}
