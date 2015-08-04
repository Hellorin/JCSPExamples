package ch.hellorin.jcsp.synchronisationBarrier;

import java.util.Scanner;
import org.jcsp.lang.*;

/**
 * A process reset enable the user to reinit the measuring device
 * 
 * @author David LAWRENCE
 * @version 1.0
 */
public class Reset implements CSProcess {
   private ChannelOutput rst;
   
   public Reset(ChannelOutput o) {
      rst = o;
   }

   /** Reinitialisation module behavior **/
   public void run() {
      final Scanner scan = new Scanner(System.in);
      while(true) {
         scan.nextLine();
         rst.write("rst");
      }
   }
   
}
