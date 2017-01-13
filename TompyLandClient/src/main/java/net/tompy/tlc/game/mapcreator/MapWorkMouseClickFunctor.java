package net.tompy.tlc.game.mapcreator;

import java.awt.Point;

import net.tompy.tl.map.FeatureData;
import net.tompy.tl.map.HexData;
import net.tompy.tl.map.MapData;
import net.tompy.tl.map.MapHelper;
import net.tompy.tl.map.MapWorkFunctor;
import net.tompy.tl.map.TerrainData;
import net.tompy.tl.map.TerrainDictionary;

public class MapWorkMouseClickFunctor implements MapWorkFunctor 
{
	private TerrainData terrain;
	private MapData map;
	private FeatureData feature;
	private Point pointer;
	private double zoom;
	private TerrainDictionary dictionary;
	
	public MapWorkMouseClickFunctor( TerrainDictionary td, TerrainData t, MapData md, FeatureData mf, Point b, double c )
	{
		dictionary = td;
		terrain = t;
		map = md;
		feature = mf;
		pointer = b;
		zoom = c;
	}

	@Override
	public void execute(HexData h, int xPanel, int yPanel)
	{
		if ( MapHelper.isMouseInHex( 
				new Point( (int)(( xPanel + 60 ) * zoom ), (int)((yPanel + 51 ) * zoom ) ), 
				pointer, 
				zoom ) )
		{
			if ( null != terrain )
			{
				h.setTerrain( terrain );
			}
			if ( null != feature )
			{
				if ( feature.isHexside() )
				{
					// add feature to hex and appropriate side
					int xc = (int)(( xPanel + 60 ) * zoom );
					int yc = (int)(( yPanel + 60 ) * zoom );
					
					int a = MapHelper.getAdjacentHex( pointer, xc, yc );
					
					HexData[] hexes = MapHelper.getSurroundingHexes( map, h );
					HexData otherHex = hexes[ a ];
					
					h.addFeature( dictionary.createFeature( feature.getType() ), a );
					a += 3;
					otherHex.addFeature( dictionary.createFeature( feature.getType() ), ( a > 5 ) ? a -= 6 : a );
				}
				else
				{
					h.addFeature( dictionary.createFeature( feature.getType() ) );
				}
			}
		}		
	}

}
