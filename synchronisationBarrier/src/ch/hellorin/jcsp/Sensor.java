package ch.hellorin.jcsp;

import java.util.Random;
import org.jcsp.lang.*;

/**
 * A sensor process that synchronise to a synchronisation barrier
 * once data are available to be send to the measuring device
 * 
 * @author David LAWRENCE
 *
 */
public class Sensor implements CSProcess {
   private ChannelOutput o;
   private Barrier       bar;
   private Integer       id;
   
   public Sensor (Integer id, 
		   ChannelOutput o, 
		   Barrier bar) {
      this.id  = id;
      this.o   = o;
      this.bar = bar;
   }
   
   /** Sensor behavior **/
   public void run() {
      final CSTimer t   = new CSTimer();
      final Random rand = new Random();
      while(true) {
    	 // Sleep a more or less random amount of time
         t.sleep(rand.nextInt(1000)+500);
         
         // Synchronisation on the barrier
         bar.sync();

         o.write(id);
      } 
   }
   
}
