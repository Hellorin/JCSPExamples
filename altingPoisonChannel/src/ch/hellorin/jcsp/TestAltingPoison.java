package ch.hellorin.jcsp;

import org.jcsp.lang.*;

/**
 * Testing alting with poisonable channels.
 *  
 * @author David LAWRENCE
 * @version 1.0
 */
public class TestAltingPoison {

	/** Main **/
	public static void main (String[] args) {
		// Case where the timeout is reached and the channel is poisoned
		int timeBeforeWrite = 200; // in ms
		int timeoutTime     = 100; // in ms
		System.out.println("###############################");
		System.out.println("### Timeout reached example ###");
		System.out.println("###############################");
		execute_example(timeoutTime, timeBeforeWrite);
		System.out.println("");
		
		// Case where a message is written on the channel
		timeBeforeWrite = 100; // in ms
		timeoutTime     = 200; // in ms
		System.out.println("#######################################");
		System.out.println("### Data written on channel example ###");
		System.out.println("#######################################");
		execute_example(timeoutTime, timeBeforeWrite);
		System.out.println("");
	}

	/**
	 * Execute the example with a given value of timeout
	 * 
	 * @param timeoutTime
	 * 		Waiting time before a timeout is reached
	 * @param timeBeforeWrite
	 * 		Waiting time before writing to the data channel
	 **/
	private static void execute_example(final int timeoutTime, final int timeBeforeWrite) {
		// Create a channel for data transmission with a poison Immunity of 0
		One2OneChannel dataLink = Channel.one2one(0);  

		// Execute two processes in parallel: One that wait either for
		// a data written on the data channel or for a timeout
		System.out.println("Execute the two processes");
		new Parallel (
				new CSProcess[] {
						new Processing(dataLink.in(), timeoutTime),
						new DataChannelManager(dataLink.out(), timeBeforeWrite)
				}
		).run();
		System.out.println("End of program");
	}

}
