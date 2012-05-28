package to.uk.gagandeepbali.swing.messenger.controller;

import to.uk.gagandeepbali.swing.messenger.gui.LoginPanel;
import to.uk.gagandeepbali.swing.messenger.gui.MessengerPanel;
import to.uk.gagandeepbali.swing.messenger.model.Client;
import to.uk.gagandeepbali.swing.messenger.model.Server;

import java.awt.CardLayout;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class ClientServerHandler extends AbstractAction
{
	private CardLayout cardLayout;
	private MessengerPanel messengerPanel;
	private LoginPanel loginPanel;
	
	private JTextField ipField;
	private JTextField portField;
	private JTextField nameField;
	
	private JToggleButton clientButton;
	private JToggleButton serverButton;
	
	public ClientServerHandler(MessengerPanel mp
										, String title
										, ImageIcon image
										, String desc
										, Integer mnemonic
										, LoginPanel lp)
	{
		super(title, image);		
		putValue(SHORT_DESCRIPTION, desc);
		putValue(MNEMONIC_KEY, mnemonic);
		
		messengerPanel = mp;
		loginPanel = lp;
		
		ipField = loginPanel.getIPField();
		portField = loginPanel.getPortField();
		nameField = loginPanel.getNameField();
		clientButton = loginPanel.getClientButton();
		serverButton = loginPanel.getServerButton();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		String ipAddress =  ipField.getText().trim();
		String port = portField.getText().trim();
		String name = nameField.getText();
		
		if (ipAddress.length() > 0 && port.length() > 0 
								   && name.length() > 0
								   && (clientButton.isSelected() || serverButton.isSelected()))
		{
			if (!(name.equals(LoginPanel.DEFAULT_NAME_STRING)))
			{
				System.out.println("I am WORKING, if ");
				if (clientButton.isSelected())
				{
					System.out.println("Start Client");
					messengerPanel.statusLabel.setText("Connecting to Server...");
					Client client = new Client(messengerPanel, ipAddress, port, name);
				}
				else if (serverButton.isSelected())
				{
					System.out.println("Start Server");
					messengerPanel.statusLabel.setText("Listening to Client Requests...");
					Server server = new Server(messengerPanel, ipAddress, port, name);					
					//server.startServer(messengerPanel, ipAddress, port, name);
				}
				cardLayout = (CardLayout) messengerPanel.cardPanel.getLayout();
				cardLayout.next(messengerPanel.cardPanel);
			}
			else
			{
				JOptionPane.showMessageDialog(
								 messengerPanel.getParent()								
								, "Please Provide your Name, before proceeding :-)"
								, "Invalid Input Credentials"
								, JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (ipAddress.length() == 0)
		{
			JOptionPane.showMessageDialog(
								 messengerPanel.getParent()								
								, "Please Provide IP Address, before proceeding :-)"
								, "Invalid Input Credentials"
								, JOptionPane.ERROR_MESSAGE);
		}
		else if (port.length() == 0)
		{
			JOptionPane.showMessageDialog(
								 messengerPanel.getParent()								
								, "Please Provide a PORT, before proceeding :-)"
								, "Invalid Input Credentials"
								, JOptionPane.ERROR_MESSAGE);
		}
		else if (!clientButton.isSelected() && !serverButton.isSelected())
		{
			JOptionPane.showMessageDialog(
								 messengerPanel.getParent()								
								, "Please select either Client or Server, before proceeding :-)"
								, "Invalid Input Credentials"
								, JOptionPane.ERROR_MESSAGE);
		}
	}
}