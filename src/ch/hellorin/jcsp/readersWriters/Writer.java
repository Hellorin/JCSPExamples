package ch.hellorin.jcsp.readersWriters;

import java.util.Random;
import java.util.Scanner;

import org.jcsp.lang.*;

/**
 * Writer of the book
 *  
 * @author David LAWRENCE
 * @version 1.0
 */
public class Writer implements CSProcess {
   private ChannelOutput  toBook;
   private ChannelOutput  chanEcriture;
   private String         id;
   
   /**
    * Constructor
    * 
    * @param id
    * 		Id of the writer
    * @param o
    * 		Output channel
    * @param confText
    */
   public Writer (String id, ChannelOutput o, ChannelOutput confText) {
      this.id      = id;
      toBook       = o;
      chanEcriture = confText;
   }
   
   @Override
   public void run() {
      Random rand = new Random();
      final CSTimer t = new CSTimer();
      Scanner scan = new Scanner(System.in);
      
      while (true) {
         t.sleep((long)rand.nextInt(1800)+500);
         
         // Create a connexion inbetween a book and a reader
         One2OneConnection chan = Connection.createOne2One();
         
         // Send the interface of server connexion
         toBook.write(chan.server());
         t.sleep(100);
         // Take information from the keyboard and send the data
         // to the book
         System.out.println("The reader n¼" + id + ", your text: ");
         String str = scan.nextLine();
         chan.client().request(str);
         
         // Write the text in the textArea
         chanEcriture.write("\n The reader n¼" + id + " writes:\t" + str);  
         
         // Fake writing wait  
         t.sleep((long)rand.nextInt(1000)+500);
         chan.client().reply();
         
         System.out.println("\t\tEnd: writer " + id);
         
         // Handshake of end of rendez vous
         chan.client().request(null);
         chan.client().reply();         
      }
   }

}
