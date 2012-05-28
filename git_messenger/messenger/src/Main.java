import to.uk.gagandeepbali.swing.messenger.gui.MessengerFrame;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main 
{
    private static Logger logger = Logger.getLogger(Main.class);
    
    public static void main(String[] args) 
	{
        
        long time = System.currentTimeMillis();
        logger.info("main method called..");
        logger.info("another informative message");
        logger.warn("This one is a warning!");
		MessengerFrame.main("Messenger Application");
        logger.log(Level.TRACE, 
                "And a trace message using log() method.");
        long logTime = System.currentTimeMillis() - time;
        
        logger.debug("Time taken to log the previous messages: " 
                + logTime + " msecs");

        // Exception logging example:
        try
		{
            String subs = "hello".substring(6);
        }
		catch (Exception e)
		{
            logger.error("Error in main() method:", e);
        }                    
    }
}