package net.tompy.tlc.gui.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

import net.tompy.tl.map.MapData;
import net.tompy.tl.map.MapHelper;

@SuppressWarnings("serial")
public class GamePanel extends JPanel 
{
	private MapData map = null;
	private Point pointer;
	private double zoom = 1;
	//private TerrainDictionary terrainDictionary;

	public void resize()
	{
		invalidate();
		setPreferredSize( new Dimension( (int)(( map.getWidth() * 120.0 * zoom )  + 45), 
				(int)(( map.getHeight() * 104 * zoom ) + 80) ) );
		this.getParent().revalidate();
		revalidate();
		repaint();
	}
	
	@Override
	public void paintComponent( Graphics g )
	{
		Graphics2D g2d = (Graphics2D)g;
		Rectangle vr = getVisibleRect();
		g2d.clearRect( vr.x, vr.y, vr.width + vr.x, vr.height  + vr.y );
		
		MapHelper.workOnSubMap( map, vr, zoom, new MapWorkDrawGameFunctor( g2d, pointer, zoom ) );
		
	}

}
