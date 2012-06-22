package test;

import java.awt.*;
import javax.swing.*;

public class CustomFrame extends JFrame
{
	public CustomFrame(String title)
	{
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		JLabel label = new JLabel(
			"I am the Inbuild JLabel in JFrame", JLabel.CENTER);
		panel.add(label);	
		getContentPane().add(panel, BorderLayout.PAGE_START);
	}
}