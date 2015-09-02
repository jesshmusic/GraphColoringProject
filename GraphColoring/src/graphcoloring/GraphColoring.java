package graphcoloring;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author Jess Hendricks
 */
public class GraphColoring
{

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws URISyntaxException, IOException
	{
		// Get instance of the GraphChecker singleton object
		GraphChecker graphChecker = GraphChecker.getInstance();

		//  Variables for time testing
		long startTime;
		long endTime;

		//  Check smallgraph
		startTime = System.nanoTime();

		if (args.length == 0) {
			graphChecker.checkGraphColoring("smallgraph", "OUTPUT-smallGraphOut");
			endTime = System.nanoTime();
			System.out.println("Time: " + ((endTime - startTime) / 1000000) 
					+ " milliseconds.");

			// Check largegraph1
			startTime = System.nanoTime();
			graphChecker.checkGraphColoring("largegraph1", "OUTPUT-largeGraph1Out");
			endTime = System.nanoTime();
			System.out.println("Time: " + ((endTime - startTime) / 1000000) 
					+ " milliseconds.");

			// Check largegraph2
			startTime = System.nanoTime();
			graphChecker.checkGraphColoring("largegraph2", "OUTPUT-largeGraph2Out");
			endTime = System.nanoTime();
			System.out.println("Time: " + ((endTime - startTime) / 1000000) 
					+ " milliseconds.");
		} else {
			for (int i = 0; i < args.length; i++) {
				String outputFile = "OUTPUT" + args[i];
				graphChecker.checkGraphColoring(args[i], outputFile);
				endTime = System.nanoTime();
				System.out.println("Time: " + ((endTime - startTime) / 1000000) 
						+ " milliseconds.");
			}
		}
	}

}
