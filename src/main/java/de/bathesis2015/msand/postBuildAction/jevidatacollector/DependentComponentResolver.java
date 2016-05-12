package de.bathesis2015.msand.postBuildAction.jevidatacollector;

import java.util.Collection;
import java.util.Map;

import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.BuildDependencyPublisher.DescriptorImpl;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.ComponentSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.DependentComponent;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataLoader;
import hudson.Launcher;
import hudson.PluginWrapper;
import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.tasks.Recorder;
import jenkins.model.Jenkins;

/**
 * BuildDependencyPublisher.java
 * 
 * This object is implementing an {@link Action} so it can be one part of build step.
 * This action performs a search for all highlevel components which include
 * a specific lowlevel dependency. So whenever a lowlevel dependency changed,
 * all highlevel components can be informed or rebuild with the new version
 * of the lowlevel dependency
 * 
 * implements {@link Action} and {@link StaplerProxy}
 * 
 * @author Michael Sandritter
 */
public class DependentComponentResolver implements Action, StaplerProxy {

	private DataLoader dataLoader;
	private BuildData buildData;
	private Map<String, DependentComponent> map;
	public final static String SUB_URL = "dependency-resolver";

	public DependentComponentResolver(DataLoader dataLoader, BuildData buildData) throws Exception
	{
		this.dataLoader = dataLoader;
		this.buildData = buildData;
		resolveDependentComponents(buildData);
	}

	@SuppressWarnings("unchecked")
	private void resolveDependentComponents(BuildData buildData) throws Exception
	{
		String componentName = loadComponentNameOfMainComponent(buildData.getBuildId());
		Transferable t = dataLoader.loadDependentComponents(componentName);
		map = (Map<String, DependentComponent>) t.getMap(DependentComponent.class);
		for (DependentComponent c : map.values()) {
			String url = buildData.getJenkinsUrl() + "job/" + c.getJobName() + "/build?delay=0sec";
			c.setLink(url);
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getAnalysisUrl()
	{
		String url = buildData.getJenkinsUrl() + "job/" + buildData.getJobName() + "/"
				+ Integer.toString(buildData.getNumber()) + "/" + IntegrationAnalyser.SUB_URL;
		return url;
	}

	/**
	 * 
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	private String loadComponentNameOfMainComponent(String buildId) throws Exception
	{
		Transferable t = dataLoader.loadMainComponent(buildId);
		ComponentSummary c = (ComponentSummary) t.getObject(ComponentSummary.class);
		return c.getComponentName();
	}

	@Override
	public Object getTarget()
	{
		return null;
	}

	@Override
	public String getIconFileName()
	{
		PluginWrapper wrapper = Jenkins.
				getInstance().
				getPluginManager()
				.whichPlugin(BuildDependencyPublisher.class);
		return "plugin/" + wrapper.getShortName() + "/images/logo.png";
	}

	@Override
	public String getDisplayName()
	{
		return "Dependency-Resolver";
	}

	@Override
	public String getUrlName()
	{
		return SUB_URL;
	}

	public Collection<DependentComponent> getDependentComponents()
	{
		return map.values();
	}

}
