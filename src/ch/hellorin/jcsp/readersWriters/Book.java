package ch.hellorin.jcsp.readersWriters;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ConnectionServer;
import org.jcsp.lang.Guard;

/**
 * Process that represent the shared ressource. In this case,
 * a book
 *  
 * @author David LAWRENCE
 * @version 1.0
 */
public class Book implements CSProcess {
	//private String text;
	private AltingChannelInput comWriters; 
	private AltingChannelInput comReadersStart;
	private AltingChannelInput comReadersFinish;

	/**
	 * 
	 * @param inWriters
	 * 		Channel to read from the writer
	 * @param inReadersO
	 * 		Channel to realise that a reader wants to read
	 * @param inReadersF
	 * 		Channel to end a reader readaction
	 */
	public Book (AltingChannelInput inWriters,
			AltingChannelInput inReadersO,
			AltingChannelInput inReadersF) {
		comWriters        = inWriters;
		comReadersStart   = inReadersO;
		comReadersFinish  = inReadersF;
	}

	@Override
	public void run() {
		final Alternative alt = new Alternative(new Guard[] 
				{comWriters, comReadersStart, comReadersFinish});

		final int WRITER = 0, READER_START = 1;

		final Alternative alt2 = new Alternative(new Guard[]{comReadersStart, comReadersFinish});
		final int READEROREAD = 0, READERFREAD = 1;
		System.out.println("Ouverture du livre");
		int nbReaders = 0;
		while (true) {
			// If there is no more redaers, boths readers
			// and writers are accepted
			if (nbReaders == 0) {

				// select()     : arbitrary choice
				// priSelect()  : choice with the smallest index
				// fairSelect() : equitable choice
				switch (alt.select()) {
				case WRITER:
					ConnectionServer canal =  (ConnectionServer)
					comWriters.read();
					//text += (String) canal.request();
					canal.reply("Ok", true);

					// Wait until the writer is done writing
					canal.request();
					canal.reply("Ok", false);
					break;

				case READER_START:
					// If it is a start of reading, we add a new reader
					comReadersStart.read();
					nbReaders++;
					break;
				}  
			} else {
				// If there is a least one reader, we don't accept
				// any writer and accept only readers
				int choix = alt2.priSelect();
				if (choix == READEROREAD) {
					comReadersStart.read();
					nbReaders++;
				} else if (choix == READERFREAD) {
					comReadersFinish.read();
					nbReaders--;
				}
			}
		} 
	}
}
