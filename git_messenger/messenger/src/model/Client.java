package to.uk.gagandeepbali.swing.messenger.model;

import to.uk.gagandeepbali.swing.messenger.controller.TextFieldActionHandler;
import to.uk.gagandeepbali.swing.messenger.gui.MessengerPanel;

import java.awt.Color;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;


import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleConstants;

import org.apache.log4j.Logger;

public class Client
{
	private static Logger logger = Logger.getLogger(Client.class);
	/* This variable will close all while loops
	 * when the Client will Logout
	 */
	private boolean hasFinished;
	private MessengerPanel messengerPanel;
	
	private JTextField chatField;
	private JTextPane chatPane;
	
	private Color[] colours;
	
	private PrintWriter outgoingChat;
	
	private String ipAddress;
	private String port;
	private String name;
	private String id;
	
	private SocketChannel nameSocket;
	private SocketChannel chatSocket;
	private SocketChannel logoutSocket;
	
	private List<String> namesList = new ArrayList<String>();
	
	private MaintainNamesList maintainNamesList;
	private ReceiveChat receiveChat;
	private TextFieldActionHandler textFieldActionHandler;
	
	public Client(MessengerPanel mp, String ip, String p, String n)
	{
		messengerPanel = mp;
		hasFinished = false;
		colours = new Color[]{
								Color.MAGENTA.darker(),
								Color.DARK_GRAY,
								Color.BLUE.darker(),
								Color.RED,
								Color.PINK
							 };
		
		ipAddress = ip;
		port = p;
		name = n;
		
		startClient();
	}
	
	private void startClient()
	{
		try
		{
			//nameSocket = new Socket(ipAddress, Integer.parseInt(port));
			nameSocket = SocketChannel.open(new InetSocketAddress(ipAddress, Integer.parseInt(port)));
			chatSocket = SocketChannel.open(new InetSocketAddress(ipAddress, Integer.parseInt(port)));
			logoutSocket = SocketChannel.open(new InetSocketAddress(ipAddress, Integer.parseInt(port)));
			try
			{
				outgoingChat = new PrintWriter(
								Channels.newOutputStream(chatSocket), true);				
			}
			catch(Exception e)
			{
				logger.error("Class : Client\n"
							+ "Method : startClient()\n"
							+ "Unable to initiate OutputStream for Chatting : " + e);
			}
		}
		catch(Exception e)
		{
			logger.error("Unable to open SocketChannels inside startClient() method"
						+ " of the Client Class." + e);
		}
		
		//Thread maintainNames = new Thread(new MaintainNamesList());
		//maintainNames.start();
		textFieldActionHandler = new TextFieldActionHandler();
		
		chatField = messengerPanel.chatPanel.getChatField();
		chatField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doActionOnField");
		chatField.getActionMap().put("doActionOnField", textFieldActionHandler);
		
		chatPane = messengerPanel.chatPanel.getChatPane();
		
		(maintainNamesList = new MaintainNamesList()).execute();
		(receiveChat = new ReceiveChat()).execute();
	}
	
	private class MaintainNamesList extends SwingWorker<Void, String>
	{
		private Logger logger = Logger.getLogger(MaintainNamesList.class);
		
		private BufferedReader incoming;
		private PrintWriter outgoing;
				
		private String clientNames;
		
		private JTextPane namePane;
		
		public MaintainNamesList()
		{			
			clientNames = "";
			namePane = messengerPanel.chatPanel.getNamePane();
			try
			{
				incoming = new BufferedReader(
								new InputStreamReader(
									Channels.newInputStream(nameSocket)));
				/*incoming = new BufferedReader(
								new InputStreamReader(
											nameSocket.getInputStream()));
				outgoing = new PrintWriter(nameSocket.getOutputStream(), true);*/	
				outgoing = new PrintWriter(
								Channels.newOutputStream(nameSocket), true);	
			}
			catch(Exception e)
			{
				logger.error("Unable to initiate Input and Output Streams"
							+ " for the MaintainNamesList Class : " + e);
			}
		}
		
		@Override
		public Void doInBackground()
		{
			try
			{
				System.out.println("Client\'s Name : " + name);
				SwingUtilities.invokeAndWait(new Runnable()
				{
					public void run()
					{
						messengerPanel.statusLabel.setText("Sending Name to the Server.");
					}
				});
				outgoing.println(name);
				outgoing.flush();				
				SwingUtilities.invokeAndWait(new Runnable()
				{
					public void run()
					{
						messengerPanel.statusLabel.setText("Receiving ID from the Server.");
					}
				});
				id = (incoming.readLine()).trim();
				System.out.println("Client\'s ID : " + id);
				SwingUtilities.invokeAndWait(new Runnable()
				{
					public void run()
					{
						messengerPanel.statusLabel.setText("");
					}
				});
				textFieldActionHandler.setNameIDAndOutput(name, id, outgoingChat);
				
				while (!hasFinished)
				{
					clientNames = incoming.readLine();
					System.out.println("Clients : " + clientNames);
					if (clientNames != null && clientNames.length() > 1)
					{
						/*String text = clientNames.substring(1, clientNames.length() - 1);
						String names[] = text.split(", ");
						namesList.clear();
						for (int i = 0; i < names.length; i++)
						{
							namesList.add(names[i]);
						}
						SwingUtilities.invokeAndWait(new Runnable()
						{
							public void run()
							{
								namePane.setText("");								
								for (int i = 0; i < namesList.size(); i++)
								{
									String text = namesList.get(i);
									int colourIndex = Integer.parseInt(text.substring(0, 1));
									String name = text.substring(1, text.length());
									appendToTextPane(namePane, name + "\n", colours[colourIndex]);
								}
							}
						});*/
						publish(clientNames);
					}
				}				
			}
			catch(Exception e)
			{
				logger.error("Unable to send the Name of Client"
							+ " to the server : " + e);
			}
			return null;
		}
		
		@Override
		protected void process(List<String> clientList)
		{
			String clientNames = clientList.get(clientList.size() - 1);
			String textNames = clientNames.substring(1, clientNames.length() - 1);
			String names[] = textNames.split(", ");
			namesList.clear();
			for (int i = 0; i < names.length; i++)
			{
				namesList.add(names[i]);
			}					
			namePane.setText("");
			for (int i = 0; i < namesList.size(); i++)
			{
				String text = namesList.get(i);
				int colourIndex = Integer.parseInt(text.substring(0, 1));
				String name = text.substring(1, text.length());
				appendToTextPane(namePane, name + "\n", colours[colourIndex]);
			}
		}
	}
	
	private class ReceiveChat extends SwingWorker<Void, String>
	{
		private Logger logger = Logger.getLogger(ReceiveChat.class);
		private BufferedReader incoming;		
		
		public ReceiveChat()
		{
			try
			{
				incoming = new BufferedReader(
								new InputStreamReader(
									Channels.newInputStream(chatSocket)));
			}
			catch(Exception e)
			{
				logger.error("Unable to initiate Input Streams"
							+ " for the ReceiveChat Class : " + e.getMessage());
			}
		}
		
		@Override
		protected Void doInBackground()
		{
			try
			{
				while (!hasFinished)
				{
					String msg = (incoming.readLine()).trim();
					System.out.println("Message : " + msg);
					if (msg != null && msg.length() > 1)
						publish(msg);
					msg = null;	
				}
			}
			catch(IOException ioe)
			{
				logger.error("Inside Class : Client\n Inner Class "
						+ ": ReceiveChat\n Method : doInBackground()\n"
						+ "Unable to receive messages from the Server : " + ioe);
			}
			return null;
		}
		
		@Override
		protected void process(List<String> chatMessages)
		{							
			String text = chatMessages.get(chatMessages.size() - 1);
			int colourIndex = Integer.parseInt(text.substring(0, 1));
			String message = text.substring(1, text.length());
			System.out.println("Message : " + message);
			appendToTextPane(chatPane, message + "\n", colours[colourIndex]);
		}
	}
	
	private void appendToTextPane(JTextPane tPane, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        
        int len = tPane.getDocument().getLength();
        tPane.setCaretPosition(len);
        tPane.setCharacterAttributes(aset, false);
        tPane.replaceSelection(msg);
    }
}