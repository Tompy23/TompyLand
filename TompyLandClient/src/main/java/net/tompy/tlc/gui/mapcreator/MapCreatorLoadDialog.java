package net.tompy.tlc.gui.mapcreator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MapCreatorLoadDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JList< String > listMaps;
	
	private boolean load = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MapCreatorLoadDialog dialog = new MapCreatorLoadDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MapCreatorLoadDialog()
	{
		init( null );
	}
	
	public MapCreatorLoadDialog( String[] mapList )
	{
		init( mapList );
	}
	
	public String getSelectedName()
	{
		return listMaps.getSelectedValue();
	}

	/**
	 * Create the dialog.
	 */
	public void init( String[] mapList ) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			if ( null != mapList )
			{
				DefaultListModel< String > dlm = new DefaultListModel< String >();
				for ( String s : mapList )
				{
					dlm.addElement( s );
				}
				listMaps = new JList< String >( dlm );
			}
			else
			{
				listMaps = new JList< String >();
			}
			listMaps.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//scrollPane.add(listMaps);
		}
		JScrollPane scrollPane = new JScrollPane( listMaps );
		contentPanel.add(scrollPane);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						load = true;
						setVisible( false );
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						load = false;
						setVisible( false );
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public boolean isLoad() {
		return load;
	}

}
