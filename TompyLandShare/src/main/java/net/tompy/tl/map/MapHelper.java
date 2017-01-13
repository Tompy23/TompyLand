package net.tompy.tl.map;

import java.awt.Point;
import java.awt.Rectangle;

public class MapHelper 
{
	public static void workOnSubMap( MapData map, Rectangle visibleRect, double zoom, MapWorkFunctor mwf )
	{
		if ( null != map )
		{
			for ( int x = 0; x < map.getWidth(); x++ )
			{
				for ( int y = 0; y < map.getHeight(); y++ )
				{
					HexData h = map.getHex( x, y );
					double yd = y * 51.9615*2.0;
					double xd = x * 90;
					if ( 0 != x%2 )
					{
						yd += 51.9615;
					}
					int xi = (int)xd;
					int yi = (int)yd;
				
					if ( xi > ( visibleRect.x - 120 ) / zoom
							&& xi < ( visibleRect.width + 120 + visibleRect.x ) / zoom
							&& yi > ( visibleRect.y - 120 ) / zoom
							&& yi < ( visibleRect.height + 120 + visibleRect.y ) / zoom )
					{
						mwf.execute( h, xi, yi );
					}
				}
			}
		}
	}
	
	public static boolean isMouseInHex( Point p, Point pointer, double zoom )
	{
		return ( ( 52 * zoom ) > distance( p, pointer ) );
	}
	
	public static Point getPanelCoordsFromHex( HexData h, double zoom )
	{
		Point p = new Point( (int)(h.getX() * 90 * zoom), (int)(h.getY() * 51.9615*2.0 * zoom) );
		
		return p;
	}
	
	public static int getAdjacentHex( Point pointer, int xc, int yc )
	{
		double angle = Math.atan2( pointer.getY() - yc, pointer.getX() - xc );
		int a = (int)( 57.3248407643 * angle / 60 );
		if ( angle < 0 )
		{
			a += 1;
			a = ( a < 0 ) ? 5 : a;
		}
		else
		{
			a += 2;
		}
		
		return a;
	}
	
	public static HexData[] getSurroundingHexes( MapData map, HexData h )
	{
		HexData[] hexes = new HexData[ 6 ];
		
		hexes[ 0 ] = map.getHex( h.getX(), h.getY() - 1 );
		hexes[ 3 ] = map.getHex( h.getX(), h.getY() + 1 );
		if ( 0 == h.getX()%2 )
		{
			hexes[ 1 ] = map.getHex( h.getX() + 1, h.getY() - 1 );
			hexes[ 2 ] = map.getHex( h.getX() + 1, h.getY() );
			hexes[ 4 ] = map.getHex( h.getX() - 1, h.getY() );
			hexes[ 5 ] = map.getHex( h.getX() - 1, h.getY() - 1 );
		}
		else
		{
			hexes[ 1 ] = map.getHex( h.getX() + 1, h.getY() );
			hexes[ 2 ] = map.getHex( h.getX() + 1, h.getY() + 1 );
			hexes[ 4 ] = map.getHex( h.getX() - 1, h.getY() + 1 );
			hexes[ 5 ] = map.getHex( h.getX() - 1, h.getY() );
		}
		
		return hexes;
	}
	
	public static double distance( Point a, Point b )
	{
		double returnValue = 0.0;
		
		returnValue = Math.sqrt( ( ( b.x - a.x ) * ( b.x - a.x ) ) + ( ( b.y - a.y ) * ( b.y - a.y ) ) );
		
		return returnValue;
	}
}
