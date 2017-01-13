package net.tompy.tlc.gui.mapcreator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import net.tompy.tl.TompyLandConstants;
import net.tompy.tl.map.FeatureData;
import net.tompy.tl.map.MapData;
import net.tompy.tl.map.TerrainData;
import net.tompy.tl.map.TerrainDictionary;
import net.tompy.tlc.game.mapcreator.TompyLandMapCreator;

public class MapCreatorApp implements ActionListener, TreeSelectionListener, ChangeListener
{
	private int frameX1, frameX2, frameY1, frameY2;
	private int zoomMin, zoomMax, zoomStart;
	private int initMapWidth, initMapHeight;

	private JFrame frame;
	private MapPanel mapPanel;
	private JSlider sliderZoom;

	private TompyLandMapCreator processer = null;

	private TerrainDictionary terrainDictionary = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapCreatorApp window = new MapCreatorApp();
					window.init();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void init()
	{
		//listener.setWindow( this );
		initialize();
	}

	/**
	 * Create the application.
	 */
	public MapCreatorApp() {
	}
	
	public void setVisible( boolean b )
	{
		frame.setVisible( b );
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(frameX1, frameY1, frameX2, frameY2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem menuItemNew = new JMenuItem("New Map...");
		menuItemNew.addActionListener( this );
		menuItemNew.setActionCommand("NewMap");
		mnFile.add(menuItemNew);
		
		JMenuItem menuItemLoad = new JMenuItem("Load Map...");
		menuItemLoad.addActionListener( this );
		menuItemLoad.setActionCommand("OpenMap");
		mnFile.add(menuItemLoad);
		
		JMenuItem menuItemSave = new JMenuItem("Save Map...");
		menuItemSave.addActionListener( this );
		menuItemSave.setActionCommand("SaveMap");
		mnFile.add(menuItemSave);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem menuItemQuit = new JMenuItem("Quit");
		menuItemQuit.addActionListener( this );
		menuItemQuit.setActionCommand( "Quit" );
		mnFile.add(menuItemQuit);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmZoomIn = new JMenuItem("Zoom in");
		mntmZoomIn.setActionCommand("ZoomIn");
		mntmZoomIn.addActionListener( this );
		mnView.add(mntmZoomIn);
		
		JMenuItem mntmZoomOut = new JMenuItem("Zoom out");
		mntmZoomOut.setActionCommand("ZoomOut");
		mntmZoomOut.addActionListener( this );
		mnView.add(mntmZoomOut);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewMap = new JButton("");
		btnNewMap.setToolTipText("New Map...");
		btnNewMap.setIcon(new ImageIcon(MapCreatorApp.class.getResource("/icons/New.gif")));
		btnNewMap.setActionCommand("NewMap");
		toolBar.add(btnNewMap);
		
		sliderZoom = new JSlider();
		sliderZoom.setPreferredSize(new Dimension(40, 16));
		sliderZoom.setValue( zoomStart );
		sliderZoom.setMinimum( zoomMin );
		sliderZoom.setMaximum( zoomMax );
		sliderZoom.addChangeListener( this );
		
		JButton btnOpenMap = new JButton("");
		btnOpenMap.setIcon(new ImageIcon(MapCreatorApp.class.getResource("/icons/Open.gif")));
		btnOpenMap.setActionCommand("OpenMap");
		toolBar.add(btnOpenMap);
		
		JButton btnSaveMap = new JButton("");
		btnSaveMap.setIcon(new ImageIcon(MapCreatorApp.class.getResource("/icons/Save.gif")));
		btnSaveMap.setActionCommand("SaveMap");
		toolBar.add(btnSaveMap);
		toolBar.add(sliderZoom);
		btnNewMap.addActionListener( this );
		btnOpenMap.addActionListener( this );
		btnSaveMap.addActionListener( this );
		
		mapPanel = new MapPanel();
		mapPanel.setTerrainDictionary( terrainDictionary );

		JTree tree = new JTree( loadTerrainTree() );
		tree.addTreeSelectionListener( this );
		tree.setCellRenderer( new TerrainTreeCellRenderer( terrainDictionary.getTerrainList(), terrainDictionary.getFeatureList() ) );
		
		JScrollPane scrollPaneTerrain = new JScrollPane( tree );
		
		mapPanel.addMouseListener( mapPanel );
		mapPanel.addMouseMotionListener( mapPanel );
		
		JScrollPane scrollPaneMap = new JScrollPane( mapPanel );
		scrollPaneMap.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
		scrollPaneMap.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
				
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent( scrollPaneTerrain );
		splitPane.setRightComponent( scrollPaneMap );
		
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if ( "NewMap".equals( arg0.getActionCommand() ) )
		{
			MapCreatorNewDialog mcnd = new MapCreatorNewDialog();
			
			mcnd.setWidthValue( initMapWidth );
			mcnd.setHeightValue( initMapHeight );
			
			mcnd.setVisible( true );
			
			if ( mcnd.isCreate() )
			{
				createMap( mcnd.getWidthValue(), mcnd.getHeightValue() );
			}
			
			mcnd.dispose();	
		}
		else if ( "OpenMap".equals( arg0.getActionCommand() ) )
		{
			// TODO
			// Send a request to the server, getting a list of strings (map names)
			processer.requestMapListFromServer();
			// Add the list to the load dialog.
			// show the load dialog
			// get selection (and check for "OK")
			//  request the server to load the map.
		}
		else if ( "SaveMap".equals( arg0.getActionCommand() ) )
		{
			MapCreatorSaveDialog mcsd = new MapCreatorSaveDialog();
			
			mcsd.setVisible( true );
			
			if ( mcsd.isSave() )
			{
				processer.saveMap( mcsd.getMapName(), TompyLandConstants.TLMAP_SAVE_CONTINUE );
			}
			
			mcsd.dispose();
		}
		else if ( "ZoomIn".equals( arg0.getActionCommand() ) )
		{
			double z = mapPanel.getZoom() + .15;
			if ( z > zoomMax )
			{
				z = zoomMax;
			}
			mapPanel.setZoom( z );
			sliderZoom.setValue( (int)z );
		}
		else if ( "ZoomOut".equals( arg0.getActionCommand() ) )
		{
			double z = mapPanel.getZoom() - .15;
			if ( z < zoomMin )
			{
				z = zoomMin;
			}
			mapPanel.setZoom( z );
			sliderZoom.setValue( (int)z );
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) 
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.getNewLeadSelectionPath().getLastPathComponent();
		if ( node.isLeaf() )
		{
			mapPanel.setSelectedFeature( null );
			mapPanel.setSelectedTerrain( null );
			Object nodeInfo = node.getUserObject();
			if ( nodeInfo instanceof TerrainData )
			{
				mapPanel.setSelectedTerrain( (TerrainData)nodeInfo );
			}
			if ( nodeInfo instanceof FeatureData )
			{
				mapPanel.setSelectedFeature( (FeatureData)nodeInfo );
			}
		}
	}

	
	private DefaultMutableTreeNode loadTerrainTree()
	{
		DefaultMutableTreeNode top = new DefaultMutableTreeNode( "Terrain" );
		DefaultMutableTreeNode category = null;
		
		for ( String c : terrainDictionary.getTerrainList().keySet() )
		{
			category = new DefaultMutableTreeNode( c );
			top.add( category );
			for ( TerrainData t : terrainDictionary.getTerrainList().get( c ) )
			{
				category.add( new DefaultMutableTreeNode( t ) );
			}
		}
		
		for ( String c : terrainDictionary.getFeatureList().keySet() )
		{
			category = new DefaultMutableTreeNode( c );
			top.add( category );
			for ( FeatureData me : terrainDictionary.getFeatureList().get( c ) )
			{
				category.add( new DefaultMutableTreeNode( me ) );
			}
		}

		return top;
	}
	

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		if ( e.getSource() instanceof JSlider )
		{
			JSlider slider = (JSlider)e.getSource();
			
			if ( ! slider.getValueIsAdjusting() )
			{
				mapPanel.setZoom( slider.getValue()/100.0 );
			}
		}
	}

	public void createMap( int w, int h )
	{
		mapPanel.setMap( processer.createMap(w, h) );
		fixupMap( w, h );
	}
	
	public void createMap( MapData newMap )
	{
		mapPanel.setMap( newMap );
		fixupMap( newMap.getWidth(), newMap.getHeight() );
	}
	
	private void fixupMap( int w, int h )
	{
		mapPanel.invalidate();
		mapPanel.setPreferredSize( new Dimension( (int)(mapPanel.getZoom() * w * 120 + 45),(int)(mapPanel.getZoom() * h * 104 + 80) ) );
		mapPanel.revalidate();
		mapPanel.repaint();		
	}

	public TompyLandMapCreator getProcesser() {
		return processer;
	}

	public void setProcesser(TompyLandMapCreator processer) {
		this.processer = processer;
	}

	public void setMapPanel(MapPanel mapPanel) {
		this.mapPanel = mapPanel;
	}

	public void setFrameX1(int frameX1) {
		this.frameX1 = frameX1;
	}

	public void setFrameX2(int frameX2) {
		this.frameX2 = frameX2;
	}

	public void setFrameY1(int frameY1) {
		this.frameY1 = frameY1;
	}

	public void setFrameY2(int frameY2) {
		this.frameY2 = frameY2;
	}

	public void setZoomMin(int zoomMin) {
		this.zoomMin = zoomMin;
	}

	public void setZoomMax(int zoomMax) {
		this.zoomMax = zoomMax;
	}

	public void setZoomStart(int zoomStart) {
		this.zoomStart = zoomStart;
	}

	public void setInitMapWidth(int initMapWidth) {
		this.initMapWidth = initMapWidth;
	}

	public void setInitMapHeight(int initMapHeight) {
		this.initMapHeight = initMapHeight;
	}

	public void setTerrainDictionary(TerrainDictionary terrainDictionary) {
		this.terrainDictionary = terrainDictionary;
	}
}
