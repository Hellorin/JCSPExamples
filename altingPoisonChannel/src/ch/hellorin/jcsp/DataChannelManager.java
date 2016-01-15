package ch.hellorin.jcsp;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.CSTimer;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.PoisonException;

/**
 * Process that has got the responsibility to
 * write on the data channel after a certain amount
 * of time pre defined
 *  
 * @author David LAWRENCE
 * @version 1.0
 */
public class DataChannelManager implements CSProcess {
   private final ChannelOutput chOut;
   private final int timeBeforeWrite;
   
   /**
    * Constructor
    * 
    *  @param ch
    *  		Output of the data channel where the data is written
    *  @param timeBeforeWrite
    *  		Waiting time defined before writing to the channel 
    **/
   public DataChannelManager (ChannelOutput ch, final int timeBeforeWrite) {
      chOut = ch;
      this.timeBeforeWrite = timeBeforeWrite;
   }
   
   @Override
   /**
    * Execute the process
    */
   public void run() {
	   // Wait a short amount of time before writing to the channel
      final CSTimer tim = new CSTimer ();
      long timeout = tim.read ();
      timeout += this.timeBeforeWrite;
      tim.after (timeout);
      
      // Write some data on the channel
      try {
    	 System.out.println("** Data sent on the data channel");
         chOut.write("Some random datas");
      } catch (PoisonException e) {
    	  // If the timeout has been reached, writing on the channel will raise a PoisonException
         System.out.println("** The channel has been poisoned and nothing can be written on it");
      }
   }

}
