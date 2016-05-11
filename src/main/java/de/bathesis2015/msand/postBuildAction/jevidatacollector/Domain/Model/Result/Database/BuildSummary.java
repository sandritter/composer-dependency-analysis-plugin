package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database;

/**
 * 
 * BuildSummary.java The BuildSummary-Object is used to hold build-specific
 * information, that has been loaded from the database
 *
 * @author Michael Sandritter
 *
 */
public class BuildSummary {

	/**
	 * build identifier
	 */
	private String buildId;

	/**
	 * build number
	 */
	private int buildNumber;

	/**
	 * timestamp of the build-start
	 */
	private long time_stamp;

	/**
	 * name of the jenkins-build-job
	 */
	private String jobName;

	public String getBuildId()
	{
		return buildId;
	}

	public void setBuildId(String buildId)
	{
		this.buildId = buildId;
	}

	public long getTime_stamp()
	{
		return time_stamp;
	}

	public void setTime_stamp(long time_stamp)
	{
		this.time_stamp = time_stamp;
	}

	public String getJobName()
	{
		return jobName;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
	}

	public int getBuildNumber()
	{
		return buildNumber;
	}

	public void setBuildNumber(int buildNumber)
	{
		this.buildNumber = buildNumber;
	}
}
