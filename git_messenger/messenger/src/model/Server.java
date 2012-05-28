package to.uk.gagandeepbali.swing.messenger.model;

import to.uk.gagandeepbali.swing.messenger.controller.TextFieldActionHandler;
import to.uk.gagandeepbali.swing.messenger.gui.MessengerPanel;

import java.awt.Color;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

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

public class Server
{
	private static Logger logger = Logger.getLogger(Server.class);
	private static final int MAX_CLIENTS = 5;
	private boolean hasFinished;
	private static int clientConnected;
	private Color[] colours;
	private MessengerPanel messengerPanel;
	
	private JTextField chatField;
	private JTextPane chatPane;
	
	private String ipAddress;
	private String port;
	private String name;
	private String id;
	
	private static PrintWriter outgoingChat;
	
	private ServerSocket sSocket;
	private Socket nameSocket;
	private Socket idSocket;
	private Socket chatSocket;
	private Socket logoutSocket;
	
	private List<String> namesList = new ArrayList<String>();
	private List<Socket> nameSockets = new ArrayList<Socket>();
	private List<Socket> chatSockets = new ArrayList<Socket>();
	private List<Socket> logoutSockets = new ArrayList<Socket>();
	
	private AddNameAndDistribute addNameDistribute;
	private ReceiveChatAndDistribute receiveChatAndDistribute;
	private TextFieldActionHandler textFieldActionHandler;
	
	public Server(MessengerPanel mp
				, String ip, String p, String n)
	{
		messengerPanel = mp;		
		hasFinished = false;
		
		ipAddress = ip;
		port = p;
		name = n;
		id = "0";
		clientConnected = 1;
		colours = new Color[]{
								Color.MAGENTA.darker(),
								Color.DARK_GRAY,
								Color.BLUE.darker(),
								Color.RED,
								Color.PINK
							 };
		
		textFieldActionHandler = new TextFieldActionHandler();
		
		chatField = messengerPanel.chatPanel.getChatField();
		chatField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doActionOnField");
		chatField.getActionMap().put("doActionOnField", textFieldActionHandler);		
		
		startServer();
	}
	
	public static PrintWriter getChatOutputStream()
	{
		return outgoingChat;
	}
	
	private void startServer()
	{
		namesList.add("0" + name);
		Thread requestClient = new Thread(new IncomingRequest());
		requestClient.start();
	}
	
	private class IncomingRequest implements Runnable
	{
		public void run()
		{
			try
			{
				sSocket = new ServerSocket(Integer.parseInt(port));
				while (clientConnected <= MAX_CLIENTS)
				{
					nameSocket = sSocket.accept();
					chatSocket = sSocket.accept();
					logoutSocket = sSocket.accept();
					
					nameSockets.add(nameSocket);
					chatSockets.add(chatSocket);
					logoutSockets.add(logoutSocket);
					
					try
					{
						outgoingChat = new PrintWriter(chatSocket.getOutputStream(), true);
						textFieldActionHandler.setNameIDAndOutput(name, id, outgoingChat);
					}
					catch(Exception e)
					{
						logger.error("Unable to initialize Chat Socket\'s "
										+ "OutputStream, inside run() method of "
										+ "IncomingRequest Private Inner Class of Server Class: "
																			+ e.getMessage());
					}
					
					clientConnected++;
					chatPane = messengerPanel.chatPanel.getChatPane();
					
					(addNameDistribute = new AddNameAndDistribute()).execute();
					(receiveChatAndDistribute = new ReceiveChatAndDistribute()).execute();
				}
			}
			catch(Exception e)
			{
				System.err.println("Error in Incoming Request : " + e);
				logger.error("Error in Incoming Request : " + e);
			}
		}
	}
	
	private class AddNameAndDistribute extends SwingWorker<Void, String>
	{		
		private Logger logger = Logger.getLogger(AddNameAndDistribute.class);
		private BufferedReader incoming;
		private InputStreamReader input;
		private PrintWriter outgoing;
		private String name;
		private JTextPane namePane;
		
		public AddNameAndDistribute()
		{
			try
			{
				incoming = new BufferedReader(
					new InputStreamReader(nameSocket.getInputStream()));
				outgoing = new PrintWriter(nameSocket.getOutputStream(), true);		
			}
			catch(Exception e)
			{
				logger.error("Unable to initialize the input/output"
						+ "for the names Socket : " + e);
				e.printStackTrace();		
			}
		}
		
		@Override
		protected Void doInBackground()
		{
			try
			{
				name = incoming.readLine().trim();
				System.out.println("Client Name : " + name);					
				System.out.println("Client\'s ID : " + (clientConnected - 1));
				outgoing.println(clientConnected - 1);
				outgoing.flush();
				SwingUtilities.invokeAndWait(new Runnable()
                {
                    public void run()
                    {
                        messengerPanel.statusLabel.setText("Sending ID to the Client.");
                    }
                });
				namesList.add((clientConnected - 1) + name);
				String[] names = new String[namesList.size()];
				for (int i = 0; i < names.length; i++)
					names[i] = namesList.get(i);
				tellAll();
				namePane = messengerPanel.chatPanel.getNamePane();
				//publish(names);
			}
			catch(Exception e)
			{
				logger.error("Unable to Recieve Name from Client"
						+ " and Distribute to others, inside"
						+ "doInBackground() method of"
						+ "AddNameAndDistribute Class : " + e);
			}
			return null;
		}
		
		private void tellAll() throws IOException
		{
			Iterator<Socket> it = nameSockets.iterator();
			Socket s;
			int count = 0;
			
			while (it.hasNext())
			{
				System.out.println("I am working : " + (++count));
				s = (Socket) it.next();
				outgoing = new PrintWriter(s.getOutputStream(), true);
				outgoing.println(namesList);
				outgoing.flush();
				/* Never close the connection, as it might lead
				 * to unexpected behaviour, do closing things
				 * at the very end.
				 */
			}
			addNameDistribute.cancel(true);
		}
		
		@Override
		protected void done()
		{
			namePane = messengerPanel.chatPanel.getNamePane();
			System.out.println("List : " + namesList);
			namePane.setText("");
			for (int i = 0; i < namesList.size(); i++)
			{
				String text = namesList.get(i);
				int colourIndex = Integer.parseInt(text.substring(0, 1));
				String name = text.substring(1, text.length());
				appendToTextPane(namePane, name + "\n", colours[colourIndex]);
			}
			messengerPanel.statusLabel.setText("");
		}
		
		/*@Override
		protected void process(List<String> namesList)
		{
			namePane = messengerPanel.chatPanel.getNamePane();
			System.out.println("List : " + namesList);
			namePane.setText("");
			for (int i = 0; i < namesList.size(); i++)
			{
				String text = (String)namesList.get(i);
				int colourIndex = Integer.parseInt(text.substring(0, 1));
				String name = text.substring(1, text.length());
				appendToTextPane(namePane, name + "\n", colours[colourIndex]);
			}
		}*/
	}
	
	private class ReceiveChatAndDistribute extends SwingWorker<Void, String>
	{		
		private BufferedReader incoming;
		private PrintWriter outgoing;		
		
		@Override
		protected Void doInBackground()
		{
			try
			{
				incoming = new BufferedReader(
							new InputStreamReader(
								chatSocket.getInputStream()));
				while (!hasFinished)
				{
					String msg = (incoming.readLine()).trim();
					System.out.println("Message : " + msg);
					distributeToAll(msg);
					publish(msg);
				}
			}
			catch(Exception e)
			{
				logger.error("Unable to Receive or Send Messages "
					+ "to Clients. Inside ReceiveChatAndDistribute "
					+ "Class\'s, doInBackground() : " + e);
			}
			return null;
		}
		
		private void distributeToAll(String msg)
		{
			Iterator<Socket> it = chatSockets.iterator();
			Socket s;
			
			try
			{
				while (it.hasNext())
				{
					s = (Socket) it.next();
					outgoing = new PrintWriter(s.getOutputStream(), true);
					outgoing.println(msg);
					outgoing.flush();				
				}
			}
			catch(IOException ioe)
			{
				logger.error("Inside Class : Server\n"
						+ "Inner-Class : ReceiveChatAndDistribute\n"
						+ "Method : distributeToAll(String msg)\n"
						+ "Unable to initiate Output Stream : " + ioe);
			}
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