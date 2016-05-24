package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * PackageDataImage.java This class is an image of the packages defined in a
 * composer.lock file
 *
 * @author Michael Sandritter
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DependencyReflection {

	/**
	 * component name
	 */
	@JsonProperty("name")
	private String name;

	/**
	 * component version
	 */
	@JsonProperty("version")
	private String version;

	/**
	 * 
	 */
	@JsonProperty("replace")
	private Map<String, String> replacementMap;

	/**
	 * {@link VersionControlSource}
	 */
	private VersionControlSource source;

	public VersionControlSource getSource()
	{
		return source;
	}

	public Map<String, String> getReplacementMap()
	{
		return replacementMap;
	}

	public void setReplacementMap(Map<String, String> map)
	{
		this.replacementMap = map;
	}

	public String getName()
	{
		return name;
	}

	public String getVersion()
	{
		return version;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	@JsonProperty("source")
	public void setSource(VersionControlSource source)
	{
		this.source = source;
	}
	
	@JsonProperty("dist")
	public void setDistSource(VersionControlSource source) 
	{
		this.source = source;
	}

}
