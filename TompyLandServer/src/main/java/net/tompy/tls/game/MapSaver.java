package net.tompy.tls.game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.data.FeatureSerialize;
import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.data.MapSerialize;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class MapSaver
{
	private final static Logger log = Logger.getLogger( "MapSaver" );

	private String name;
	private int width;
	private int height;
	private String filename;
	private int totalHexes;
	private int writtenHexes = 0;
	private HexSerialize[] hexes;
	
	private TompyLandGame game;
	
	public MapSaver( String n, int w, int h, String f, TompyLandGame g )
	{
		name = n;
		width = w;
		height = h;
		filename = f;
		totalHexes = w * h;
		hexes = new HexSerialize[ totalHexes ];
		game = g;
	}
	
	public int save( HexSerialize[] newHexes )
	{
		int returnValue = TompyLandConstants.TLMAP_SAVE_CONTINUE;

		for ( int i = 0; i < newHexes.length; i++ )
		{
			hexes[ writtenHexes++ ] = newHexes[ i ];
		}
		
		log.debug( "Saving " + name + ".  " + writtenHexes + " of " + totalHexes + " saved so far." );

		if ( writtenHexes == totalHexes )
		{
			log.debug( "MapSaver's job is complete" );
			returnValue = TompyLandConstants.TLMAP_SAVE_SUCCESS;
			writtenHexes = 0;
			
			// Assign ids to features
			assignFeatureId( hexes );
			
			Kryo k = new Kryo();
			Output out = new Output();
			
			try
			{
				MapSerialize map = new MapSerialize();
				map.setName( name );
				map.setWidth( width );
				map.setHeight( height );
				map.setHexes( hexes );
				
				out.setBuffer( new byte[ 5000 ] );
				out.setOutputStream( new FileOutputStream( filename ) );
				k.writeObject( out, map );
				out.close();
			}
			catch( FileNotFoundException fnfe )
			{
				log.debug( "Unable to write to " + filename );
				returnValue = TompyLandConstants.TLMAP_SAVE_FAILED;
				fnfe.printStackTrace();
			}
		}
		
		return returnValue;
	}	
	
	private void assignFeatureId( HexSerialize[] hexes )
	{
		for( HexSerialize h : hexes )
		{
			for ( FeatureSerialize f : h.getFeatures() )
			{
				if ( 0 == f.getId() )
				{
					f.setId( game.getNewEntityId() );
				}
			}
		}
	}
}
