package to.uk.gagandeepbali.swing.messenger.controller;

import to.uk.gagandeepbali.swing.messenger.gui.LoginPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ButtonMouseHandler extends MouseAdapter
{
	@Override
	public void mouseEntered(MouseEvent me)
	{
		JButton button = (JButton) me.getSource();
		String command = (String) button.getActionCommand();
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	@Override
	public void mouseExited(MouseEvent me)
	{
		JButton button = (JButton) me.getSource();
		String command = (String) button.getActionCommand();
		
		if (command.equals("LOGIN"))
		{
			button.setBorder(
				BorderFactory.createBevelBorder(
					1, Color.BLUE.darker(), Color.WHITE));
		}
		else if (command.equals("EXIT"))
		{
			button.setBorder(
				BorderFactory.createBevelBorder(
					1, Color.RED.darker(), Color.WHITE));
		}
	}
}