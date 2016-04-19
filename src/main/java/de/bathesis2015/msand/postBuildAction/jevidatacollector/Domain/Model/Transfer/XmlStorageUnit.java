package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.PackageDataImage;

@XmlRootElement
/**
 * XmlStorage encapsulates the collected information which are stored to xml 
 * XmlStorageUnit.java
 *
 * @author Michael Sandritter
 * 03.10.2015
 */
public class XmlStorageUnit {
	
	private String revision;
	private List<PackageDataImage> requirement;
	
	@XmlElement
	public String getRevision() {
		return revision;
	}

	@XmlElement
	public List<PackageDataImage> getRequirement() {
		return requirement;
	}
	
	public void setRequirement(List<PackageDataImage> requirements) {
		this.requirement = requirements;
	}
	
	public void setRevision(String revision) {
		 this.revision = revision;
	}
}
