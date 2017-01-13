package net.tompy.tlc.gui.mapcreator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class MapCreatorNewDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean create;
	private JTextField textWidth;
	private JTextField textHeight;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MapCreatorNewDialog dialog = new MapCreatorNewDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MapCreatorNewDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("New Map");
		setBounds(100, 100, 250, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("Width:");
		contentPanel.add(lblNewLabel, "2, 2");
		
		textWidth = new JTextField();
		contentPanel.add(textWidth, "4, 2, fill, default");
		textWidth.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Height:");
		contentPanel.add(lblNewLabel_1, "2, 4");
		
		textHeight = new JTextField();
		contentPanel.add(textHeight, "4, 4, fill, default");
		textHeight.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						create = true;
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
						create = false;
						setVisible( false );
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void setWidthValue( int w )
	{
		textWidth.setText( Integer.toString( w ) );
	}
	
	public int getWidthValue()
	{
		return Integer.parseInt( textWidth.getText() );
	}
	
	public void setHeightValue( int h )
	{
		textHeight.setText( Integer.toString( h ) );
	}
	
	public int getHeightValue()
	{
		return Integer.parseInt( textHeight.getText() );
	}

	public boolean isCreate() {
		return create;
	}
}
