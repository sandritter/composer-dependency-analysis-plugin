package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;



/**
 * this is class is an image that holds the information 
 * of the composer.json file
 *
 * @author Michael Sandritter
 * 21.09.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonDataImage{
	
	/**
	 * name of main component that is build
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * component type
	 */
	@JsonProperty("type")
	private String type;
	
	/**
	 * map of packages given by key=packagename and value=version
	 */
	@JsonProperty("require")
	private Map<String, String> requirements;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getRequirements() {
		return requirements;
	}
}
