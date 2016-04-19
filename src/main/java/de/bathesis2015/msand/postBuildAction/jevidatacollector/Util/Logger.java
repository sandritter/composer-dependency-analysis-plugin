package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util;

import java.io.PrintStream;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.BuildDependencyPublisher;

/**
 * 
 * Logger.java
 * This class is a singleton helper to log all relevant messages including warnings and error to cosole ouput
 *
 * @author Michael Sandritter
 *
 */
public class Logger {

	private PrintStream out;
	private static Logger logger = null;
	public static final String LABEL = "\t[va]: ";
	public static final String SUCCESS = "-> SUCCESS: ";
	public static final String WARNING = "-> WARNING: ";
	
	protected Logger(PrintStream logger){
		this.out = logger;
	}
	
	public static Logger getInstance(){
		if (logger == null) {
			logger = new Logger(BuildDependencyPublisher.logger);
		}
		return logger;
	}
	
	/**
	 * prints a String to console output 
	 * @param s
	 */
	public void println(String s){
		out.println(s);
	}
	
	/**
	 * prints an empty line to console ouput
	 */
	public void println(){
		out.println();
	}
	
	/**
	 * calculates the time the plugin needed to process all his work and logs it
	 * in the console output of a build
	 * 
	 * @param start
	 *            - start time
	 * @param nde
	 *            - end time
	 */
	public void logElapsedTime(long start, long end) {
		long diff = end - start;
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		if (diff < 1000){			
			println(LABEL + "TOTEL TIME ELAPSED: " + diff + " milliseconds.");
		} else {
			println(LABEL + "TOTEL TIME ELAPSED: " + diffMinutes + " minutes " + diffSeconds + " seconds.");			
		}
	}
}
