package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util;

import java.util.ArrayList;
import java.util.List;

import DataBuilder.DAO.BuildBuilder;
import DataBuilder.DAO.ComponentBuilder;
import DataBuilder.DAO.DependencyBuilder;
import DataBuilder.DAO.StandBuilder;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Build;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Component;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Dependency;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Stand;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Transport;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;

public class TestDataProvider {
	
	private StandBuilder standBuilder;
	private ComponentBuilder componentBuilder;
	private DependencyBuilder depBuilder;
	private BuildBuilder bBuilder;
	
	public TestDataProvider(){
		standBuilder = new StandBuilder();
		componentBuilder = new ComponentBuilder();
		depBuilder = new DependencyBuilder();
		bBuilder = new BuildBuilder();
	}
	
	
	public Transferable getStorageData(String buildId, int index, int amount){
		Transferable t = new Transport();
		List<Component> lc = new ArrayList<Component>();
		List<Stand> ls = new ArrayList<Stand>();
		List<Dependency> ld = new ArrayList<Dependency>();
		int j = index;
		for (int i = index; i < amount; i++){
			lc.add(componentBuilder.getMock(""+i, ""+i, ""+i));
			ls.add(standBuilder.getMock(""+i, ""+i, ""+i));
			ld.add(depBuilder.getMock(buildId, ""+i, "direct"));
			j++;
		}
		lc.add(componentBuilder.getMock(""+j, ""+j, ""+j));
		ls.add(standBuilder.getMock(""+j, ""+j, ""+j));
		ld.add(depBuilder.getMock(buildId, ""+j, "main"));
		Build build = bBuilder.getMock(buildId, (long) j, j, ""+j);
		t.setObject(Build.class, build);
		t.setList(Stand.class, ls);
		t.setList(Dependency.class, ld);
		t.setList(Component.class, lc);
		return t;
	}

}
