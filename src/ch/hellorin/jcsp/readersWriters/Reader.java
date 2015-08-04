package ch.hellorin.jcsp.readersWriters;

import java.util.Random;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.CSTimer;
import org.jcsp.lang.ChannelOutput;

/**
 * Reader of the book
 *  
 * @author David LAWRENCE
 * @version 1.0
 */
public class Reader implements CSProcess {
   private String id;
   private ChannelOutput ouverture;
   private ChannelOutput fermeture;
   
   public Reader (String id, ChannelOutput o, ChannelOutput f) {
      this.id         = id;
      this.ouverture  = o;
      this.fermeture  = f;
   }
   
   @Override
   public void run() {
      Random rand = new Random();
      final CSTimer t = new CSTimer();      
      
      while(true) {
         t.sleep((long)rand.nextInt(4000)+1000);
         ouverture.write("lecture");
         t.after(t.read() + 500);
         System.out.println("DŽbut: Lecture lecteur " + id);
         t.sleep((long)rand.nextInt(1000)+200);
         fermeture.write("fin");
         System.out.println("\t\tFin: Lecture lecteur " + id);
      }
      
   }

}
