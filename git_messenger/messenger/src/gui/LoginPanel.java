package to.uk.gagandeepbali.swing.messenger.gui;

import to.uk.gagandeepbali.swing.messenger.controller.ButtonMouseHandler;
import to.uk.gagandeepbali.swing.messenger.controller.ClientServerHandler;
import to.uk.gagandeepbali.swing.messenger.controller.MessengerActionHandler;
import to.uk.gagandeepbali.swing.messenger.controller.TextFieldActionHandler;
import to.uk.gagandeepbali.swing.messenger.controller.TextFieldFocusHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.KeyEvent;

import java.net.URL;

import javax.swing.Action;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

public class LoginPanel extends JPanel
{
	private static final int GAP = 10;
	private static final int BORDER_GAP = 5;
	
	private TextFieldFocusHandler textFieldFocusHandler;
	
	private static final String DEFAULT_IP_STRING = "gagandeepbali.uk.to";
	private static final String DEFAULT_PORT_STRING = "100";
	public static final String DEFAULT_NAME_STRING = "Please Enter your Name Or Press ALT + N";
	
	private JTextField ipField;
	private JTextField portField;
	private JTextField nameField;
	
	private JToggleButton clientButton;
	private JToggleButton serverButton;
	
	public static ImageIcon clientImage;
	public static ImageIcon serverImage;	
	public static ImageIcon loginImage;
	public static ImageIcon exitImage;	
	
	private JButton loginButton;
	private JButton exitButton;
	
	private MessengerPanel messengerPanel;
	
	private URL url;
	
	protected static  Action messengerActionHandler;
	private Action clientServerHandler;
	
	public LoginPanel(MessengerPanel mp)
	{
		messengerPanel = mp;
		textFieldFocusHandler = new TextFieldFocusHandler();
		
		loginImage = new ImageIcon(
				getClass().getResource(
					"/images/loginicon32.png"));
		exitImage = new ImageIcon(
				getClass().getResource(
					"/images/exit.png"));	
		clientImage = new ImageIcon(
				getClass().getResource(
					"/images/client.png"));			
		serverImage = new ImageIcon(
				getClass().getResource(
					"/images/server32.png"));	
		messengerActionHandler = new MessengerActionHandler(
							"Exit", exitImage, "Click to Exit the Program."
											, KeyEvent.VK_Q);										
	}
	
	protected void createGUI()
	{
		setOpaque(true);
		setLayout(new BorderLayout(GAP, GAP));		
		setBorder(BorderFactory.createTitledBorder(
										"LOGIN"));
		setBackground(Color.WHITE);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(true);
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(
				BorderFactory.createEmptyBorder(
								GAP, GAP, GAP, GAP));
		JPanel ipPanel = new JPanel();
		ipPanel.setOpaque(true);
		ipPanel.setBackground(Color.GREEN.darker());
		ipPanel.setLayout(new FlowLayout(
							FlowLayout.LEFT, GAP, GAP));	
		ipPanel.setBorder(
				BorderFactory.createTitledBorder(
										"IP Address"));
		
		JLabel ipLabel = new JLabel("IP Address : ", JLabel.CENTER);
		ipField = new JTextField(20);
		ipField.setText(DEFAULT_IP_STRING);
		ipField.setToolTipText("Press I to get your IP Address Or Enter the IP of the Server");
		ipField.addFocusListener(textFieldFocusHandler);
		ipField.setName("IP Address");
		ipField.getInputMap().put(KeyStroke.getKeyStroke('i'), "doActionOnField");
		ipField.getActionMap().put("doActionOnField", new TextFieldActionHandler());
		ipPanel.add(ipLabel);
		ipPanel.add(ipField);
		
		JPanel portPanel = new JPanel();
		portPanel.setOpaque(true);
		portPanel.setBackground(Color.WHITE);
		portPanel.setLayout(new FlowLayout(
							FlowLayout.LEFT, GAP, GAP));	
		portPanel.setBorder(
				BorderFactory.createTitledBorder(
										"Port Number"));	
		JLabel portLabel = new JLabel("PORT : ", JLabel.CENTER);
		portField = new JTextField(20);
		portField.setText(DEFAULT_PORT_STRING);
		portField.setToolTipText("Use default or else use any PORT Number greater than 1024.");
		portField.addFocusListener(textFieldFocusHandler);
		portPanel.add(portLabel);
		portPanel.add(portField);
		
		JPanel namePanel = new JPanel();
		namePanel.setOpaque(true);
		namePanel.setBackground(Color.ORANGE);
		namePanel.setLayout(new FlowLayout(
							FlowLayout.LEFT, GAP, GAP));	
		namePanel.setBorder(
				BorderFactory.createTitledBorder(
										"Name"));	
		JLabel nameLabel = new JLabel("Name : ", JLabel.CENTER);
		nameField = new JTextField(20);
		nameField.setText(DEFAULT_NAME_STRING);
		nameField.setName("Name");
		nameField.setToolTipText(DEFAULT_NAME_STRING);
		nameField.getInputMap().put(KeyStroke.getKeyStroke("alt N"), "doActionOnField");
		nameField.getActionMap().put("doActionOnField", new TextFieldActionHandler());
		nameField.addFocusListener(textFieldFocusHandler);
		namePanel.add(nameLabel);
		namePanel.add(nameField);		
		
		centerPanel.setLayout(new GridLayout(3, 1, GAP, GAP));
		centerPanel.add(ipPanel);
		centerPanel.add(portPanel);
		centerPanel.add(namePanel);
		
		add(centerPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(true);
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setBorder(
			BorderFactory.createTitledBorder(""));
		bottomPanel.setLayout(new GridLayout(2, 2, GAP, GAP));
		
		clientButton = new JToggleButton("CLIENT", clientImage);
		clientButton.setOpaque(true);
		clientButton.setBorder(
			BorderFactory.createBevelBorder(
					1, Color.MAGENTA.darker(), Color.WHITE));
		clientButton.setBackground(Color.WHITE);						
		clientButton.setForeground(Color.BLACK);
		
		serverButton = new JToggleButton("SERVER", serverImage);
		serverButton.setOpaque(true);
		serverButton.setBorder(
			BorderFactory.createBevelBorder(
					1, Color.DARK_GRAY.darker(), Color.WHITE));
		serverButton.setBackground(Color.WHITE);						
		serverButton.setForeground(Color.BLACK);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(clientButton);
		bg.add(serverButton);
		
		clientServerHandler = new ClientServerHandler(messengerPanel
										  , "Login", loginImage
										  , "Click to Login or Press ALT + L"
										  , KeyEvent.VK_L, this);
		loginButton = new JButton(clientServerHandler);
		loginButton.setActionCommand("LOGIN");
		loginButton.setOpaque(true);
		loginButton.setBorder(
			BorderFactory.createBevelBorder(
				1, Color.BLUE.darker(), Color.WHITE));						
		loginButton.setBackground(Color.WHITE);
		loginButton.setForeground(Color.BLACK);
		loginButton.addMouseListener(new ButtonMouseHandler());

		exitButton = new JButton(messengerActionHandler);
		exitButton.setName("Exit");
		exitButton.setOpaque(true);
		exitButton.setBorder(
			BorderFactory.createBevelBorder(
				1, Color.RED.darker(), Color.WHITE));								
		exitButton.setBackground(Color.WHITE);
		exitButton.setForeground(Color.BLACK);
		exitButton.addMouseListener(new ButtonMouseHandler());

		bottomPanel.add(clientButton);
		bottomPanel.add(serverButton);
		bottomPanel.add(loginButton);
		bottomPanel.add(exitButton);
		
		add(bottomPanel, BorderLayout.PAGE_END);
		exitButton.requestFocusInWindow();
	}
		
	public JTextField getIPField()
	{
		return ipField;
	}

	public JTextField getPortField()
	{
		return portField;
	}			
	
	public JTextField getNameField()
	{
		return nameField;
	}		
	
	public JToggleButton getClientButton()
	{
		return clientButton;
	}

	public JToggleButton getServerButton()
	{
		return serverButton;
	}		
}