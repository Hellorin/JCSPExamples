package ch.hellorin.jcsp.readersWriters;

import java.awt.BorderLayout;
import java.awt.Frame;

import org.jcsp.awt.ActiveButton;
import org.jcsp.awt.ActiveTextArea;
import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;
import org.jcsp.util.OverWriteOldestBuffer;

/**
 * Test the readers writers problems without priorities
 *  
 * @author David LAWRENCE
 * @version 1.0
 */
public class TestRead_Writ {

   /**
    * @param args
    */
   public static void main(String[] args) {
	   // Create a window
      final Frame fenetre = new Frame("Readers-writers probem without priority");
      final String texte  = "Once upon a time:";
      
      // Create an active button awt with channels
      final One2OneChannel configureButton = Channel.one2one ();
      final One2OneChannel eventBt         = Channel.one2one (new OverWriteOldestBuffer (10));      
      final ActiveButton bt        = new ActiveButton(configureButton.in(), eventBt.out(), "Exit");
      ListenEvent listenEvent      = new ListenEvent(eventBt.in());
      
      // Create an active textArea
      final One2OneChannel configureTxt    = Channel.one2one();
      final ActiveTextArea txtArea = new ActiveTextArea(configureTxt.in(), null, texte);
      txtArea.setEditable(false);
      txtArea.setSize(500, 80);
      
      fenetre.add(txtArea, BorderLayout.NORTH);
      fenetre.add(bt, BorderLayout.SOUTH);
      fenetre.setSize(500, 250);
      fenetre.setVisible(true);
      
      final int nbWriters = 2;
      final int nbReaders  = 4;
      
      // Create channels
      Any2OneChannel comWriters = Channel.any2one();
      Any2OneChannel comReadersO = Channel.any2one();
      Any2OneChannel comReadersF = Channel.any2one();
      
      Book livre = new Book(comWriters.in(), comReadersO.in(), comReadersF.in());
      CSProcess[] writers = new Writer[nbWriters];
      CSProcess[] readers  = new Reader [nbReaders];
      
      // Create the writers
      for (int i=0; i < writers.length; i++)
         writers[i] = new Writer(String.valueOf(i), comWriters.out(), configureTxt.out());
      
      // Create the readers
      for (int i=0; i < readers.length; i++)
         readers[i] = new Reader(String.valueOf(i), comReadersO.out(), comReadersF.out());

      
      // Launch all processes
      new Parallel (
         new CSProcess[] {
               txtArea,
               new Parallel(writers),
               new Parallel(readers),
               livre,
               listenEvent
         }
      ).run();
      
   }

}
