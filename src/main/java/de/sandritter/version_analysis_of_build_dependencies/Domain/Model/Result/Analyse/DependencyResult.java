package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Analyse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Database.BuildSummary;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Database.ComponentSummary;

/**
 * DependencyResult.java 
 * The {@link DependencyResult} object holds the
 * information about the analysis of an installed component.
 *
 * @author Michael Sandritter
 */
public class DependencyResult implements Serializable {

	private static final long serialVersionUID = -6366056612941244755L;
	private final String OK = "OK";
	private final String WARNING = "WARNING";

	/**
	 * installed component of a build, that has been reviewed
	 * {@link ComponentSummary}
	 */
	private ComponentSummary analyseTarget = null;

	/**
	 * build in which the analysis target was main component itself
	 * {@link BuildSummary}
	 */
	private BuildSummary relatedBuild = null;

	/**
	 * list of dependencies of the analyseTarget which are not tested in latest
	 * version (list of {@link Disparity})
	 */
	private List<Disparity> disparities = null;

	/**
	 * status - OK/WARNING
	 */
	private String status = OK;

	/**
	 * if a related build exists isLinked=true, else false
	 */
	private boolean isLinked = false;

	/**
	 * url to the analysis report of the related build
	 */
	private String link;

	/**
	 * constructor
	 * 
	 * @param analyseTarget {@link ComponentSummary}
	 */
	public DependencyResult(ComponentSummary analyseTarget)
	{
		this.status = OK;
		this.analyseTarget = analyseTarget;
	}

	public DependencyResult()
	{
		this.status = OK;
	}

	public String getStatus()
	{
		return status;
	}

	public boolean hasWarnings()
	{
		if (disparities != null)
			return true;
		return false;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public void addDisparity(Disparity d)
	{
		status = WARNING;
		if (disparities == null) {
			disparities = new ArrayList<Disparity>();
		}
		disparities.add(d);
	}

	public List<Disparity> getDisparities()
	{
		return disparities;
	}

	public ComponentSummary getAnalyseTarget()
	{
		return analyseTarget;
	}

	public void setAnalyseTarget(ComponentSummary componentSummary)
	{
		this.analyseTarget = componentSummary;
	}

	public BuildSummary getRelatedBuild()
	{
		return relatedBuild;
	}

	public void setRelatedBuild(BuildSummary relatedBuild)
	{
		this.relatedBuild = relatedBuild;
	}

	public boolean isLinked()
	{
		return isLinked;
	}

	public void setLinked(boolean isLinked)
	{
		this.isLinked = isLinked;
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
