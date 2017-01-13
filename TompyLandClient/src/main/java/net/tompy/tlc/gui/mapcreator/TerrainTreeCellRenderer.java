package net.tompy.tlc.gui.mapcreator;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.component.GameEntity;
import net.tompy.tl.map.FeatureData;
import net.tompy.tl.map.TerrainData;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class TerrainTreeCellRenderer extends DefaultTreeCellRenderer 
{
	private final static Logger log = Logger.getLogger( "TerrainTreeCellRenderer" );

	Map< String, Icon > iconMap = new HashMap< String, Icon >();
	
	public TerrainTreeCellRenderer( Map< String, List< TerrainData > > terrainList, Map< String, List< FeatureData > > entityList )
	{
		super();
		for ( String c : terrainList.keySet() )
		{
			for ( TerrainData t : terrainList.get( c ) )
			{
				buildMap( t.getImageFilename(), t.getName() );
			}
		}		
		for ( String c : entityList.keySet() )
		{
			for ( FeatureData t : entityList.get( c ) )
			{
				buildMap( t.getImageFilename(), t.getName() );
			}
		}		
	}

	private void buildMap( String imageName, String iconName )
	{
		try
		{
			log.debug( "Loading image: " + imageName );
			BufferedImage bi = ImageIO.read( getClass().getClassLoader().getResourceAsStream( imageName ) );

			BufferedImage sbi = new BufferedImage( 30, 30, BufferedImage.TYPE_INT_ARGB );
			Graphics2D g2d = (Graphics2D)sbi.getGraphics();	
			g2d.drawImage( bi, 0, 0, 30, 30, null );

			Icon icon = new ImageIcon( sbi );
			iconMap.put( iconName, icon );
		}
		catch( IOException ioe )
		{
			ioe.printStackTrace();
			System.exit( TompyLandConstants.TL_INIT_FAIL );
		}		
	}
	public Component getTreeCellRendererComponent(
		JTree tree,
		Object value,
		boolean sel,
		boolean expanded,
		boolean leaf,
		int row,
		boolean hasFocus) 
	{
		super.getTreeCellRendererComponent(
			tree, value, sel,
			expanded, leaf, row,
			hasFocus);
		
		
		Object o = ((DefaultMutableTreeNode)value).getUserObject();
		if ( o instanceof GameEntity )
		{
			GameEntity t = (GameEntity)o;
			setIcon( iconMap.get( t.getName() ) );
		}
		
		return this;
	}
}
