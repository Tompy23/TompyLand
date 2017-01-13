package net.tompy.tlc.network;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esotericsoftware.kryonet.Client;

import net.tompy.tl.serialization.SerialRegistration;

public class TompyLandClient extends Client
{
	private final static Logger log = LogManager.getLogger( "TompyLandClient" );

	private String host;
	private int port;
	
	public TompyLandClient()
	{
		super( 1024*16, 1024*16 );
	}
	
	public void init( TompyLandClientListener listener ) throws IOException
	{
		log.debug( "init()" );
		SerialRegistration.register( getKryo() );
		addListener( listener );
		start();
		connect( 5000, host, port );
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
