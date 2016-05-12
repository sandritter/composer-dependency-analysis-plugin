package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.DependentComponentResolver;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.ComponentSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;

/**
 * AnalyseResult.java 
 * This class summarizes the version analysis of integration
 * dependencies. The {@link AnalyseResult} object holds a list of
 * {@link DependencyResult} objects, the main component, which was analyzed and
 * a {@link BuildData} object, that holds build specific information
 *
 * @author Michael Sandritter
 */
public class AnalyseResult extends ArrayList<DependencyResult> implements Serializable {

	private static final long serialVersionUID = -477899930704734201L;

	/**
	 * main component that has been analyzed {@link ComponentSummary}
	 */
	private ComponentSummary mainComponent = null;

	/**
	 * amount of {@link DependencyResult} with {@link Disparity}
	 */
	private double amountOfDependencyResultsWithDisparity = 0;

	/**
	 * build specific information {@link BuildData}
	 */
	private BuildData buildData;

	public AnalyseResult(BuildData buildData)
	{
		setBuildData(buildData);
	}

	public ComponentSummary getMainComponent()
	{
		return mainComponent;
	}

	public void increaseWarningCount()
	{
		amountOfDependencyResultsWithDisparity++;
	}

	public void setMainComponent(ComponentSummary mainComponent)
	{
		this.mainComponent = mainComponent;
	}

	public String getResolverUrl()
	{
		String url = buildData.getJenkinsUrl() + "job/" + buildData.getJobName() + "/"
				+ Integer.toString(buildData.getNumber()) + "/" + DependentComponentResolver.SUB_URL;
		return url;
	}

	/**
	 * get percentage of installed components with latest tested version
	 * 
	 * @return percentage ok
	 */
	public String getPercentageOk()
	{
		double percentage = (size() - amountOfDependencyResultsWithDisparity) * 100 / size();
		return Double.toString(round(percentage, 2));
	}

	/**
	 * get percentage of installed components, which are not tested in latest
	 * version
	 * 
	 * @return percentage with warning
	 */
	public String getPercentageWarnings()
	{
		double percentage = amountOfDependencyResultsWithDisparity * 100 / size();
		return Double.toString(round(percentage, 2));
	}

	public boolean hasDependencies()
	{
		if (size() > 0)
			return true;
		return false;
	}

	/**
	 * round decimal numbers up to two places behind the comma
	 * 
	 * @param value
	 * @param places
	 * @return
	 */
	private double round(double value, int places)
	{
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public BuildData getBuildData()
	{
		return buildData;
	}

	public void setBuildData(BuildData buildData)
	{
		this.buildData = buildData;
	}
}
