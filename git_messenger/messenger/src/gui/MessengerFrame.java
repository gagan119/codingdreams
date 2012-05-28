package to.uk.gagandeepbali.swing.messenger.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

public class MessengerFrame extends JFrame
{
	private static Logger logger = Logger.getLogger(MessengerFrame.class);
	private static final String NIMBUS = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	//private static final String NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	
	private MessengerPanel contentPane;	
	
	public MessengerFrame()
	{
		super("Messenger");
	}
	
	private void createAndDisplayGUI()
	{
		contentPane = new MessengerPanel();
		contentPane.createGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(contentPane.createMenuBar());
		setContentPane(contentPane);
		pack();
		//setResizable(false);
		setLocationByPlatform(true);
		setVisible(true);
		System.out.println("Size : " + getSize());
	}
	
	public static void main(String... args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					UIManager.setLookAndFeel(NIMBUS);
				}
				catch(UnsupportedLookAndFeelException ulafe)
				{
					logger.error("Error at MessengerFrame class inside main method\n" +
									"regarding LookAndFeel SetUp : " + ulafe);
					ulafe.printStackTrace();
				}
				catch(ClassNotFoundException cnfe)
				{
					logger.error("Error at MessengerFrame class inside main method\n" +
									"regarding LookAndFeel SetUp : " + cnfe);
					cnfe.printStackTrace();
				}
				catch(InstantiationException ie)
				{
					logger.error("Error at MessengerFrame class inside main method\n" +
									"regarding LookAndFeel SetUp : " + ie);
					ie.printStackTrace();
				}
				catch(IllegalAccessException iae)
				{
					logger.error("Error at MessengerFrame class inside main method\n" +
									"regarding LookAndFeel SetUp : " + iae);
					iae.printStackTrace();
				}
				new MessengerFrame().createAndDisplayGUI();
			}
		});
	}
}