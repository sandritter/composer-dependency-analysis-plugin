package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Analyse;

import java.io.Serializable;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Database.ComponentSummary;

/**
 * Disparity.java 
 * A {@link Disparity} is created when a component version of the
 * total set of installed components is having a different version than the
 * version of the same component of the subset of components, which are tested
 * against each other.
 *
 * @author Michael Sandritter
 */
public class Disparity implements Serializable {

	private static final long serialVersionUID = -8773016422403843835L;

	/**
	 * component in latest tested version {@link ComponentSummary}
	 */
	private ComponentSummary testedVersion;

	/**
	 * component in latest version that has been installed during a build
	 * {@link ComponentSummary}
	 */
	private ComponentSummary versionInstalled;

	public Disparity(ComponentSummary tested, ComponentSummary installed)
	{
		this.testedVersion = tested;
		this.versionInstalled = installed;
	}

	public ComponentSummary getTestedVersion()
	{
		return testedVersion;
	}

	public ComponentSummary getInstalledVersion()
	{
		return versionInstalled;
	}

	public void setInstalledVersion(ComponentSummary comparisonObject)
	{
		this.versionInstalled = comparisonObject;
	}

}
