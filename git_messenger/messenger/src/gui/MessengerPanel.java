package to.uk.gagandeepbali.swing.messenger.gui;

import to.uk.gagandeepbali.swing.messenger.gui.ChatPanel;
import to.uk.gagandeepbali.swing.messenger.gui.LoginPanel;
import to.uk.gagandeepbali.swing.messenger.controller.MessengerActionHandler;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

public class MessengerPanel extends JPanel
{
	private static Logger logger = Logger.getLogger(MessengerPanel.class);
	private static final int GAP = 10;
	private static final int BORDER_GAP = 5;
	
	private final String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
	private final String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	private final String NIMBUS = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	private final String SYNTH = "javax.swing.plaf.synth.SynthLookAndFeel";
	
	public static final String LOGIN_PANEL = "Login Panel";
	public static final String CHAT_PANEL = "Chat Panel";
	
	private ImageIcon questionImage;
	private ImageIcon aboutImage;
	
	public JPanel cardPanel;
	public ChatPanel chatPanel;
	private LoginPanel loginPanel;
	
	private JMenuItem exitItem;
	private JMenuItem howToItem;
	private JMenuItem aboutItem;
	
	public JLabel statusLabel;
	
	public MessengerPanel()
	{
		try
		{
			questionImage = new ImageIcon(
				MessengerPanel.class.getResource(
				"/images/questionmark32.gif"));
			aboutImage = new ImageIcon(
				MessengerPanel.class.getResource(
				"/images/about32.png"));	
		}
		catch(Exception e)
		{
			logger.error("Error inside MessengerPanel's Constructor : " + e);
			e.printStackTrace();
		}
	}
	
	protected void createGUI()
	{
		setOpaque(true);
		setLayout(new BorderLayout(GAP, GAP));
		setBorder(BorderFactory.createTitledBorder(
										"Messenger"));
		setBackground(Color.WHITE);								
		
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout(GAP, GAP));
		cardPanel.setOpaque(true);
		cardPanel.setBackground(Color.WHITE);
		
		chatPanel = new ChatPanel();
		chatPanel.createGUI();
		loginPanel = new LoginPanel(this);
		loginPanel.createGUI();
		
		cardPanel.add(loginPanel, LOGIN_PANEL);
		cardPanel.add(chatPanel, CHAT_PANEL);
		
		statusLabel = new JLabel("Messenger Status Label", JLabel.CENTER);
		
		add(cardPanel, BorderLayout.CENTER);
		add(statusLabel, BorderLayout.PAGE_END);
	}
	
	protected JMenuBar createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(Color.WHITE);
		
		JMenu optionsMenu = new JMenu("Option");
		optionsMenu.setOpaque(true);
		optionsMenu.setBackground(Color.WHITE);
		exitItem = new JMenuItem(LoginPanel.messengerActionHandler);
		exitItem.setOpaque(true);
		exitItem.setBackground(Color.WHITE);
		exitItem.setName("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		optionsMenu.add(exitItem);
		
		JMenu helpMenu = new JMenu("Help");	
		helpMenu.setOpaque(true);
		helpMenu.setBackground(Color.WHITE);	
		howToItem = new JMenuItem("How To...", KeyEvent.VK_H);
		howToItem.setOpaque(true);
		howToItem.setBackground(Color.WHITE);
		howToItem.setIcon(questionImage);
		howToItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
		aboutItem = new JMenuItem("About", KeyEvent.VK_A);
		aboutItem.setOpaque(true);
		aboutItem.setBackground(Color.WHITE);
		aboutItem.setIcon(aboutImage);
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		helpMenu.add(howToItem);
		helpMenu.add(aboutItem);
		//helpMenu.add(cbmi);
		
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		
		return menuBar;
	}
}