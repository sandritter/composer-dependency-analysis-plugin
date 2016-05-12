package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * DependencyReflectionCollection.java this is class is an image that holds the
 * information of the dependency file
 *
 * @author Michael Sandritter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DependencyReflectionCollection {

	/**
	 * list of packages loaded via composer
	 */
	@JsonProperty("packages")
	private List<DependencyReflection> packages;

	public List<DependencyReflection> getPackages()
	{
		return packages;
	}
}
