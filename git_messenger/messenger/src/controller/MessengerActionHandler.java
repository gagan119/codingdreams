package to.uk.gagandeepbali.swing.messenger.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;

public class MessengerActionHandler extends AbstractAction
{
	private String command;
	private JMenuItem menuItem;
	private JButton button;
	
	public MessengerActionHandler(String title, ImageIcon image
								, String tooltipText, Integer mnemonic)
	{
		super(title, image);
		putValue(SHORT_DESCRIPTION, tooltipText);
		putValue(MNEMONIC_KEY, mnemonic);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{	
		if (ae.getSource() instanceof JMenuItem)
		{
			menuItem = (JMenuItem) ae.getSource();
			command = menuItem.getName();
			System.out.println("MenuItem Name : " + command);
		}
		else if (ae.getSource() instanceof JButton)
		{
			button = (JButton) ae.getSource();			
			command = button.getName();
			System.out.println("Button Name : " + command);
		}
		
		if (command.equals("Exit"))
		{
			System.exit(0);
		}		
	}
}