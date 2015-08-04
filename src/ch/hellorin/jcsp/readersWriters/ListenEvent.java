package ch.hellorin.jcsp.readersWriters;

import org.jcsp.lang.*;

/**
 * Event listeners for the graphical interface connected
 * to channels
 *  
 * @author David LAWRENCE
 * @version 1.0
 */
public class ListenEvent implements CSProcess {
   private AltingChannelInput inEvent;
   
   public ListenEvent (AltingChannelInput i) {
      inEvent = i;
   }
   
   /**
    * Check if the button has been pressed
    **/
   public void run() {
      One2OneChannel temp = Channel.one2one();
      final Alternative alt = new Alternative(new Guard[] {inEvent,temp.in()});
      switch (alt.priSelect()) {
         case 0 :
            System.out.println("Exit");
            System.exit(0);
            break;
         case 1 :
            break;
            
      }

   }

}
