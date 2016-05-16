package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO;

/**
 * Build.java the build class is a data-access-objet that holds build specific
 * informations
 *
 * @author Michael Sandritter
 */
public class Build {

	/**
	 * build identifier
	 */
	private String buildId;

	/**
	 * timestamp of the build start
	 */
	private long timestamp;

	/**
	 * build number
	 */
	private int number;

	/**
	 * name of the jenkins build job
	 */
	private String jobName;

	/**
	 * 
	 * @param buildId build identifier
	 * @param timestamp timestamp of the build start
	 * @param number build number
	 * @param jobName job name of the build job
	 */
	public Build(String buildId, long timestamp, int number, String jobName)
	{
		this.buildId = buildId;
		this.timestamp = timestamp;
		this.number = number;
		this.setJobName(jobName);
	}

	public String getBuildId()
	{
		return buildId;
	}

	public void setBuildId(String buildId)
	{
		this.buildId = buildId;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getJobName()
	{
		return jobName;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
	}
}
