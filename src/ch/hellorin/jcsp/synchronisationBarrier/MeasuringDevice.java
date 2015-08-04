package ch.hellorin.jcsp.synchronisationBarrier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jcsp.lang.*;

/**
 * A processus that represent a measuring device.
 * 
 * @author David LAWRENCE
 *
 */
public class MeasuringDevice implements CSProcess {
   private AltingChannelInput i;
   private AltingChannelInput rst;
   private int                nbCapteurs;
   
   public MeasuringDevice(AltingChannelInput i, 
                    AltingChannelInput rst, 
                    int nCapteurs) {
      this.i     = i;
      this.rst   = rst;
      nbCapteurs = nCapteurs;
   }

   /** Measuring device behavior **/ 
   public void run() {
      final Alternative alt = 
         new Alternative(new Guard[] {rst, i});
      final int RESET       = 0, INPUT = 1;
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = 
         new SimpleDateFormat("H:mm:ss");
      int iteration = 1;
      Integer id;
      
      while(true) {
         switch(alt.priSelect()) {
         	// Wait for a reset signal
            case RESET:
               rst.read();
               iteration = 1;
               System.out.println("Temps " 
                     + sdf.format(cal.getTime())
                     + " => rst");
               break;
              
            // Wait for all inputs
            case INPUT:
               System.out.println("------------");
               System.out.println("Iteration " + iteration);
               for (int nCapteurs=0; 
                    nCapteurs<nbCapteurs; 
                    nCapteurs++) {
                  
                  id = (Integer)i.read();
                  System.out.println("Temps " 
                        + sdf.format(cal.getTime()) 
                        + " => capteur " + id);
               }
               iteration++;                    
               cal = Calendar.getInstance();
               
               break;
         }
      }
   }

}
