package actualtest;

import test.CustomFrame;

import java.awt.*;
import javax.swing.*;


// http://stackoverflow.com/a/11150286/1057230
public class CustomPanel extends JPanel
{
	private CustomFrame frame;
	
	public CustomPanel()
	{
		setOpaque(true);
		setBackground(Color.DARK_GRAY);
		
		JLabel label = new JLabel(
			"I am a JLabel from Java Swing", JLabel.CENTER);
		label.setOpaque(false);	
		label.setForeground(Color.WHITE);
		add(label);					
	}
	
	private void createAndDisplayGUI()
	{
		frame = new CustomFrame("Testing Jar Implementation");
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return (new Dimension(500, 300));
	}
	
	public static void main(String... args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new CustomPanel().createAndDisplayGUI();
			}
		});
	}
}