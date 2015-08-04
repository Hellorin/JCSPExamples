package ch.hellorin.jcsp.altingPoisonChannel;

import org.jcsp.lang.*;

public class StopChannelManager implements CSProcess {
   final ChannelOutput chOut;
   
   public StopChannelManager (ChannelOutput ch) {
      chOut = ch;
   }
   
   @Override
   public void run() {
	  // Wait 500 ms before sending a stop signal
      final CSTimer tim = new CSTimer ();
      long timeout = tim.read ();
      timeout += 1000;                 // set the next (absolute) timeout
      tim.after (timeout);             
      
      // Send a stop signal
      chOut.write("Stop");
   }

}
