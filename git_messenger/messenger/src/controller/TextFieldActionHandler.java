package to.uk.gagandeepbali.swing.messenger.controller;

import to.uk.gagandeepbali.swing.messenger.model.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.URL;

import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.log4j.Logger;

public class TextFieldActionHandler extends AbstractAction
{
	private static Logger logger = Logger.getLogger(TextFieldActionHandler.class);
	private PrintWriter outgoingChat;
	private String name;
	private String id;
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		JTextField tfield = (JTextField) ae.getSource();
		String command = (String) tfield.getName();
		
		if (command.equals("IP Address"))
		{
			tfield.setText(getIP());
		}
		else if (command.equals("Name"))
		{
			tfield.setText(System.getProperty("user.name"));
		}
		else if (command.equals("Chat"))
		{
			if (tfield.getDocument().getLength() > 0)
			{
				System.out.println(tfield.getText());
			
				outgoingChat.println(id + name + " : " + tfield.getText());
				outgoingChat.flush();
				tfield.selectAll();
			}
		}
	}
	
	public void setNameIDAndOutput(String n, String code, PrintWriter out)
	{
		name = n;
		id = code;
		outgoingChat = out;
		System.out.println("Name : " + name + "\nID : " + id);
	}
	
	// Method to get the IP Address of the Host.
	private String getIP()
	{
		// This try will give the Public IP Address of the Host.
		try
		{
			URL url = new URL("http://automation.whatismyip.com/n09230945.asp");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String ipAddress = new String();
			ipAddress = (in.readLine()).trim();
			if (!(ipAddress.length() > 0))
			{
				try
				{
					InetAddress ip = InetAddress.getLocalHost();
					System.out.println((ip.getHostAddress()).trim());
					return ((ip.getHostAddress()).trim());
				}
				catch(Exception ex)
				{
					logger.error("Unable to get your PUBLIC IP, from whatismyip.com : " + ex);
					return "ERROR";
				}
			}
			logger.info("IP Address is : " + ipAddress);
			System.out.println("IP Address is : " + ipAddress);
			
			return (ipAddress);
		}
		catch(Exception e)
		{
			logger.error("Unable to get your PUBLIC IP, from whatismyip.com : " + e);
			// This try will give the Private IP of the Host.
			try
			{
				InetAddress ip = InetAddress.getLocalHost();
				System.out.println((ip.getHostAddress()).trim());
				return ((ip.getHostAddress()).trim());
			}
			catch(Exception ex)
			{
				logger.error("Unable to get your PRIVATE IP : " + ex);
				return "ERROR";
			}
		}
	}
}