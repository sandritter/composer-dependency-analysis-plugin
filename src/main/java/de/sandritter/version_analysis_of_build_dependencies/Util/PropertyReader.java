package de.sandritter.version_analysis_of_build_dependencies.Util;

import java.io.IOException;
import java.util.Properties;

import de.sandritter.version_analysis_of_build_dependencies.Exception.PluginConfigurationException;

/**
 * 
 * @author Michael Sandritter
 *
 */
public class PropertyReader {
	
	private static PropertyReader reader; 
	private Properties config;
	private static Logger logger;
    
    private PropertyReader() {} 
    
    /**
     * 
     * @return
     */
    public static PropertyReader getInstance() { 
    	if (reader == null) {
    		PropertyReader.reader = new PropertyReader();
    		logger = Logger.getInstance();
    	}
      return PropertyReader.reader; 
    } 
    
    /**
     * 
     * @return
     */
    public Properties getConfig(){
    	if (config == null) {
    		config = new Properties();
    		try {
    		    config.load(getClass().getResourceAsStream("/config.properties")); 
    		    System.out.println(config.getProperty("jenkins-log"));
    		} catch (IOException ex) {
    			logger.logFailure(
    				new PluginConfigurationException("could not load configuration from config.properties", ex), 
    				"Please check that your config file is right in place ;)"
    			);
    		    ex.printStackTrace();
    		}
    	}
    	return config;
    }
}
