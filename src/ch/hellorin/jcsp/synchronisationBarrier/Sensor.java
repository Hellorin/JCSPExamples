package ch.hellorin.jcsp.synchronisationBarrier;

import java.util.Random;
import org.jcsp.lang.*;

/**
 * Un processus capteur qui consiste à se synchroniser
 * sur une barriere une fois que ces données sont prêtes
 * et les envoyer à l'appareil de mesure
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
