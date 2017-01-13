package net.tompy.tls.network;

import java.io.IOException;

import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.serialization.SerialRegistration;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryonet.Server;

public class TompyLandServer extends Server 
{
	private final static Logger log = Logger.getLogger( "TompyLandServer" );

	private int port;
	
	public TompyLandServer()
	{
		super( 1024*16, 1024*16 );
	}
	
	public void init( TompyLandServerListener listener )
	{
		log.debug( "port: " + port );
		try
		{
			SerialRegistration.register( getKryo() );
			start();
			bind( port );
			addListener( listener );
		}
		catch ( IOException ioe )
		{
			ioe.printStackTrace();
			System.exit( TompyLandConstants.TLSERVER_NOT_INITIALIZED );
		}		
	}
	
	
		
	public void deinit()
	{
		stop();
		close();
	}

	public void setPort(int port) {
		this.port = port;
	}
}
