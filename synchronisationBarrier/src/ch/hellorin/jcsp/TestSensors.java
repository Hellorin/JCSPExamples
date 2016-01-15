package ch.hellorin.jcsp;

import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.Channel;
import org.jcsp.lang.*;

/**
 * Execute a simulation of a measuring devices. Wait for all the sensors
 * to send a data to display them
 * 
 * @author David LAWRENCE
 * @version 1.0
 */
public class TestSensors {

	/**
	 * @param args
	 * 	Number of sensors
	 */
	public static void main(String[] args) {
		Any2OneChannel com = Channel.any2one();
		One2OneChannel rst = Channel.one2one();

		final int nbSensors = 4;
		
		// Create a synchronization barrier
		Barrier bar = new Barrier(nbSensors);

		// Create the sensors
		Sensor[] capteurs = new Sensor[nbSensors];
		for (int i=0; i<nbSensors; i++) {
			capteurs[i] = new Sensor(i,com.out(), bar);
		}

		// Launch all the processes in parallel
		new Parallel (
			new CSProcess[] {
				new Parallel(capteurs),
				new MeasuringDevice(com.in(), 
					rst.in(), 
					nbSensors),
					new Reset(rst.out()
				)
			}
		).run();
	}

}
