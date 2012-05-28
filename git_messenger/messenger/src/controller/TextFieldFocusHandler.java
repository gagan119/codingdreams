package to.uk.gagandeepbali.swing.messenger.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class TextFieldFocusHandler implements FocusListener
{
	@Override
	public void focusGained(FocusEvent fe)
	{
		JTextField tfield = (JTextField) fe.getSource();
		tfield.selectAll();
	}
	
	@Override
	public void focusLost(FocusEvent fe)
	{
	}
}