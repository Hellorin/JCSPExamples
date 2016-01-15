package ch.hellorin.jcsp;

import org.jcsp.lang.*;

/**
 * Wait on an alternative construct for either a data from
 * the data channel or for a timeout. If a timeout is reached
 * the data channel is poisoned to avoid that the data channel
 * blocks on a writing.
 *  
 * @author David LAWRENCE
 * @version 1.0
 */
public class Processing implements CSProcess {
   private final AltingChannelInput chDataIn;
   private final int 				timeout_time;
   
   /** Constructor **/
   public Processing (AltingChannelInput inData, final int timeout_time) {
      chDataIn = inData;
      this.timeout_time = timeout_time;
   }
   
   @Override
   public void run() {
      // Set the timeout to 100 ms
      final CSTimer timer_timeout = new CSTimer ();
      final long timeout_time = this.timeout_time; //ms
      timer_timeout.setAlarm(timeout_time + timer_timeout.read());

      // Define an alternative barrier with two channels and a timer
      final Alternative alt =
         new Alternative (new Guard[] {chDataIn, timer_timeout});
      final int DATA = 0, TIMEOUT = 1;      
      
      // Wait for one of the alternative to be activated
      switch (alt.priSelect()) {
      
      	case DATA:
      		// Normal data received
      		chDataIn.read();
        	System.out.println("** Normal data received");
            break;
      		
      	case TIMEOUT:
      		// Timeout reached
      		System.out.println("** Timeout reached");
      		// Poison the data channel to avoid letting
      		// any data to be sent
            chDataIn.poison(1);
            break;
      }
   }
   
}
