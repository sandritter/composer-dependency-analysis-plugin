package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * this is class is an image that holds the information 
 * of the composer.lock file
 *
 * @author Michael Sandritter
 * 21.09.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DependencyReflectionCollection{
	
	/**
	 * list of packages loaded via composer
	 */
	@JsonProperty("packages")
	private List<DependencyReflection> packages;

	public List<DependencyReflection> getPackages() {
		return packages;
	}
}
