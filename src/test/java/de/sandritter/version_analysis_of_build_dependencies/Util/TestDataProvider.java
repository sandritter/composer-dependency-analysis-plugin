package de.sandritter.version_analysis_of_build_dependencies.Util;

import java.util.ArrayList;
import java.util.List;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Build;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.BuildBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Component;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.ComponentBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Dependency;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.DependencyBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Stand;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.StandBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Transport;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;

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
		Build build = bBuilder.getMock(buildId, (long) j, j, ""+j, ""+j);
		t.setObject(Build.class, build);
		t.setList(Stand.class, ls);
		t.setList(Dependency.class, ld);
		t.setList(Component.class, lc);
		return t;
	}

}
