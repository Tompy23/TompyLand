package net.tompy.tlc.gui.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import net.tompy.tl.map.HexData;
import net.tompy.tl.map.MapHelper;
import net.tompy.tl.map.MapWorkFunctor;

public class MapWorkDrawGameFunctor implements MapWorkFunctor {

	private Graphics2D g2d;
	private Point pointer;
	private double zoom;
	
	public MapWorkDrawGameFunctor( Graphics2D g, Point p, double z )
	{
		g2d = g;
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
			//TODO
			// This is where drawing in the hex where the mouse points is done.
			// We could then ask the hex what is in it and then do things
			// like highlight the hex for selecting a player, NPC, etc.
		}
		// Maybe create a boolean flag for the following hex id.
		//g2d.drawString( h.getX() + ", " + h.getY(), xPanel + 40, yPanel + 40 );
		g2d.setTransform( tx );
	}

}
