package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection;

import java.util.ArrayList;
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

	private List<DependencyReflection> filteredPackages = null;
	/**
	 * list of packages loaded via composer
	 */
	@JsonProperty("packages")
	private List<DependencyReflection> packages;

	public List<DependencyReflection> getPackages()
	{
		if (filteredPackages == null) {
			filteredPackages = filterPackages(packages);
		}
		return filteredPackages;
	}
	
	/**
	 * filteres dependency reflections when there name or reference property is null
	 * both properties are required as primary key
	 * if they don't exist they are filterd out
	 * @param packages
	 * @return
	 */
	private List<DependencyReflection> filterPackages(List<DependencyReflection> packages)
	{
		List<DependencyReflection> lst = new ArrayList<DependencyReflection>();
		for (DependencyReflection dr : packages) {
			if (dr.hasName() && dr.getSource().hasReference()) {
				lst.add(dr);
			}
		}
		return lst;
	}
	
}
