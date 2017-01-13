package net.tompy.tlc.gui.mapcreator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import net.tompy.tl.map.FeatureData;
import net.tompy.tl.map.HexData;
import net.tompy.tl.map.MapData;
import net.tompy.tl.map.MapHelper;
import net.tompy.tl.map.TerrainData;
import net.tompy.tl.map.TerrainDictionary;
import net.tompy.tlc.game.mapcreator.MapWorkDrawCreatorFunctor;
import net.tompy.tlc.game.mapcreator.MapWorkMouseClickFunctor;

@SuppressWarnings("serial")
public class MapPanel extends JPanel implements MouseListener, MouseMotionListener
{
	private MapData map = null;
	private Point pointer;
	private TerrainData selectedTerrain = null;
	private FeatureData selectedFeature = null;
	private double zoom = 1;
	private TerrainDictionary terrainDictionary;
	
	public MapPanel()
	{
		super();
	}
	
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
		
		MapHelper.workOnSubMap( map, vr, zoom, new MapWorkDrawCreatorFunctor( g2d, selectedTerrain, selectedFeature, pointer, zoom ) );
		
	}
		
	public void addSelectedTerrain( HexData h )
	{
		if ( null != selectedTerrain )
		{
			h.setTerrain( selectedTerrain );
		}
	}
	
	public void addSelectedFeature( HexData h )
	{
		if ( null != selectedFeature )
		{
			h.addFeature( selectedFeature );
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		MapPanel mapPanel = (MapPanel)arg0.getComponent();
		Rectangle vr = mapPanel.getVisibleRect();
		
		MapHelper.workOnSubMap( map, vr, zoom, new MapWorkMouseClickFunctor( terrainDictionary, selectedTerrain, map, selectedFeature, arg0.getPoint(), zoom ) );
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
		MapPanel map = (MapPanel)arg0.getComponent();
		map.setPointer( arg0.getPoint() );
		map.repaint();
	}


	public void setMap(MapData map) {
		this.map = map;
	}

	public void setPointer(Point pointer) {
		this.pointer = pointer;
	}

	public void setSelectedTerrain(TerrainData selectedTerrain) {
		this.selectedTerrain = selectedTerrain;
	}

	public MapData getMap() {
		return map;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
		resize();
	}

	public void setSelectedFeature(FeatureData selectedFeature) {
		this.selectedFeature = selectedFeature;
	}

	public void setTerrainDictionary(TerrainDictionary terrainDictionary) {
		this.terrainDictionary = terrainDictionary;
	}
}
