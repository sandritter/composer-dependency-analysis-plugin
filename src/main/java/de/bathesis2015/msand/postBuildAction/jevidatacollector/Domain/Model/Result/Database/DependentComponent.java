package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Util.DateFormatter;

/**
 * DependentComponent
 * this class represents a highlevel component A of a lowlevel depedency B.
 * component B is a depedency of component A.
 *  
 * @author Michael Sandritter
 */
public class DependentComponent {

	/**
	 * component name
	 */
	private String componentName;
	
	/**
	 * job name of the assosiated build job
	 */
	private String jobName;
	
	/**
	 * unique version identifier
	 */
	private String reference;
	
	/**
	 * human readable version
	 */
	private String version;
	
	/**
	 * executed build identifier
	 */
	private String buildId;
	
	/**
	 * timestamp of build execution
	 */
	private long timeStamp = -1;
	private int buildNumber;
	private String link;

	public String getFormattedTime()
	{
		if (timeStamp != -1) {
			return DateFormatter.getFormattedTime(timeStamp);
		}
		return "no date available";
	}

	public String getBuildId()
	{
		return buildId;
	}

	public void setBuildId(String buildId)
	{
		this.buildId = buildId;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public int getBuildNumber()
	{
		return buildNumber;
	}

	public void setBuildNumber(int buildNumber)
	{
		this.buildNumber = buildNumber;
	}

	public String getComponentName()
	{
		return componentName;
	}

	public void setComponentName(String componentName)
	{
		this.componentName = componentName;
	}

	public String getJobName()
	{
		return jobName;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
	}

	public String getReference()
	{
		return reference;
	}

	public void setReference(String reference)
	{
		this.reference = reference;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

}
