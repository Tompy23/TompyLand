package net.tompy.tlc.game.mapcreator;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import net.tompy.tl.map.FeatureData;
import net.tompy.tl.map.HexData;
import net.tompy.tl.map.MapHelper;
import net.tompy.tl.map.MapWorkFunctor;
import net.tompy.tl.map.TerrainData;

public class MapWorkDrawCreatorFunctor implements MapWorkFunctor 
{
	private Graphics2D g2d;
	private TerrainData terrain;
	private FeatureData feature;
	private Point pointer;
	private double zoom;
	
	public MapWorkDrawCreatorFunctor( Graphics2D g, TerrainData t, FeatureData mf, Point p, double z )
	{
		g2d = g;
		terrain = t;
		feature = mf;
		pointer = p;
		zoom = z;
	}
	
	@Override
	public void execute(HexData h, int xPanel, int yPanel)
	{
		AffineTransform tx = g2d.getTransform();
		g2d.scale( zoom, zoom );
		h.draw( g2d, xPanel, yPanel );
		if ( MapHelper.isMouseInHex( 
				new Point( (int)(( xPanel + 60 ) * zoom ), (int)(( yPanel + 51 ) * zoom )), 
				pointer, 
				zoom ) )
		{
			g2d.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.5f ) );
			if ( null != terrain )
			{
				// Draw the Terrain in hex half transparent
				terrain.draw( g2d, xPanel, yPanel );
			}
			if ( null != feature )
			{
				if ( feature.isHexside() )
				{
					// Determine which hexside is correct.
					// Rotate image appropriately
					int xc = (int)(( xPanel + 60 ) * zoom );
					int yc = (int)(( yPanel + 60 ) * zoom );
					
					int a = MapHelper.getAdjacentHex( pointer, xc, yc );
					
					g2d.rotate( a*Math.PI/3.0, xPanel + 60, yPanel + 60  );
					feature.draw( g2d, xPanel, yPanel );
					g2d.rotate( -1*a*Math.PI/3.0, xPanel + 60, yPanel + 60  );
				
				}
				else
				{
					feature.draw( g2d, xPanel, yPanel );
				}
			}
			g2d.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1.0f ) );
		}
		g2d.drawString( h.getX() + ", " + h.getY(), xPanel + 40, yPanel + 40 );
		g2d.setTransform( tx );
	}
}
