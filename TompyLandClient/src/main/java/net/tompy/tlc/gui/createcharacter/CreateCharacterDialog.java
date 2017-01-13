package net.tompy.tlc.gui.createcharacter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

@SuppressWarnings("serial")
public class CreateCharacterDialog extends JDialog {

	private JPanel contentPanel = new JPanel();
	private JTextField textCharacterName;
	private JButton btnChooseColor;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel lblCharacterName;
	private JLabel lblColor = new JLabel("Player Color");

	private String userName;
	private Color color;
	private boolean created;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CreateCharacterDialog dialog = new CreateCharacterDialog( new CreateCharacterListener() );
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CreateCharacterDialog( CreateCharacterListener listener ) {
		setTitle("Create New Character");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		{
			lblCharacterName = new JLabel("Character Name");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblCharacterName, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblCharacterName, 12, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, lblCharacterName, -278, SpringLayout.EAST, contentPanel);
			contentPanel.add(lblCharacterName);
		}
		{
			textCharacterName = new JTextField();
			sl_contentPanel.putConstraint(SpringLayout.NORTH, textCharacterName, -2, SpringLayout.NORTH, lblCharacterName);
			sl_contentPanel.putConstraint(SpringLayout.WEST, textCharacterName, 6, SpringLayout.EAST, lblCharacterName);
			sl_contentPanel.putConstraint(SpringLayout.EAST, textCharacterName, 335, SpringLayout.WEST, contentPanel);
			contentPanel.add(textCharacterName);
			textCharacterName.setColumns(10);
		}
		{
			btnChooseColor = new JButton("Choose Color");
			sl_contentPanel.putConstraint(SpringLayout.WEST, btnChooseColor, 0, SpringLayout.WEST, lblCharacterName);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, btnChooseColor, -10, SpringLayout.SOUTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, btnChooseColor, 140, SpringLayout.WEST, contentPanel);
			btnChooseColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					color = JColorChooser.showDialog( null, "Character Color", new Color( 0, 0, 0 ) );
					lblColor.setForeground( color );
				}
			});
			btnChooseColor.setActionCommand("ChooseColor");
			contentPanel.add(btnChooseColor);
		}
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblColor, 0, SpringLayout.NORTH, btnChooseColor);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblColor, 50, SpringLayout.EAST, btnChooseColor);		
		contentPanel.add(lblColor);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener( listener );
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener( listener );
			}
		}
		getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textCharacterName, btnChooseColor, okButton, cancelButton}));
		listener.setCcd( this );
	}

	public JTextField getTextCharacterName() {
		return textCharacterName;
	}

	public void setTextCharacterName(JTextField textCharacterName) {
		this.textCharacterName = textCharacterName;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		textCharacterName.setText( userName );
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}
}
