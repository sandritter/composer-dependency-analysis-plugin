package de.sandritter.version_analysis_of_build_dependencies.Util;

import java.io.PrintStream;

import hudson.model.BuildListener;

/**
 * Logger.java
 * This class is a singleton helper to log all relevant messages including warnings and error to console output
 *
 * @author Michael Sandritter
 */
public class Logger {

	public static final String LABEL = "\t[va]: ";
	public static final String SUCCESS = "-> SUCCESS: ";
	public static final String WARNING = "-> WARNING: ";
	private PrintStream out;
	private static Logger logger = null;
	private long startTime = 0;
	private int errorCount = 0;
	
	protected Logger(PrintStream logger){
		this.out = logger;
	}
	
	/**
	 * @param listener {@link BuildListener}
	 * @return
	 */
	public static Logger getInstance(BuildListener listener){
		if (logger == null) {
			logger = new Logger(listener.getLogger());
		}
		return logger;
	}
	
	//TODO
	public static Logger getInstance(){
		return logger;
	}
	
	/**
	 * prints a String to console output 
	 * @param s
	 */
	public void log(String s){
		out.println(s);
	}
	
	/**
	 * prints an empty line to console ouput
	 */
	public void println(){
		out.println();
	}
	
	public void logPluginStart() {
		startTime = System.currentTimeMillis();
		logger.println();
		logger.log("[VERSION ANALYSIS] : [va]");
	}
	
	/**
	 * calculates the time the plugin needed to process all his work and logs it
	 * in the console output of a build
	 * 
	 * @param start start time
	 * @param end end time
	 */
	private void logElapsedTime(long end) {
		long diff = end - startTime;
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		if (diff < 1000){			
			log(LABEL + "TOTEL TIME ELAPSED: " + diff + " milliseconds.");
		} else {
			log(LABEL + "TOTEL TIME ELAPSED: " + diffMinutes + " minutes " + diffSeconds + " seconds.");			
		}
	}
	
	public void logFailure(Exception e, String title) {
		logger.log(Logger.LABEL + Logger.WARNING + "-> " + title + ": ");
		logger.log(Logger.LABEL + Logger.WARNING + "error message: " + e.getMessage());
		logger.println();
		errorCount++;
	}
	
	/**
	 * logs the final process status of this plugin
	 * 
	 * @param start timestamp of the time when the peform method was invoked
	 */
	public void logFinalProcessStatus() {
		long end = System.currentTimeMillis();
		if (errorCount == 0) {
			logger.log(Logger.LABEL + Logger.SUCCESS
					+ "Integration Analysis Of Integration Dependencies ended successfully");
		} else {
			logger.log(Logger.LABEL + Logger.WARNING
					+ "Integration Analysis Of Integration Dependencies DIDN'T end successful");
			logger.log(Logger.LABEL + "warnings occured: " + errorCount);
		}
		logger.log(Logger.LABEL + "STARTED at: " + DateFormatter.getFormattedTime(startTime));
		logger.log(Logger.LABEL + "ENDED at: " + DateFormatter.getFormattedTime(startTime));
		this.logElapsedTime(end);
		logger.println();
	}
}
