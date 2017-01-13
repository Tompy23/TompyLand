package net.tompy.tlc.gui.game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class TompyLandApp implements PropertyChangeListener
{

	private JFrame frame;
	private JSplitPane splitPane;
	private GamePanel panelGame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TompyLandApp window = new TompyLandApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TompyLandApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_com = new JPanel();
		splitPane.setLeftComponent(panel_com);
		panel_com.setLayout(new BorderLayout(0, 0));
		
		JTextArea textInfo = new JTextArea();
		panel_com.add(textInfo, BorderLayout.CENTER);
		
		JPanel panel_input = new JPanel();
		panel_com.add(panel_input, BorderLayout.SOUTH);
		panel_input.setLayout(new BorderLayout(0, 0));
		
		JButton btnSend = new JButton("Send");
		panel_input.add(btnSend, BorderLayout.EAST);
		
		JTextField textMessage = new JTextField();
		panel_input.add(textMessage, BorderLayout.CENTER);

		panelGame = new GamePanel();
		splitPane.setRightComponent(panelGame);
		splitPane.setDividerLocation(50);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
	{
		if ( JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY == arg0.getPropertyName() )
		{
			if ( splitPane.getDividerLocation() < 200 )
			{
				splitPane.setDividerLocation( 200 );
			}
		}
	}

}
