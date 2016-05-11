package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflection;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.VersionControlSource;

public class PackageDataImageBuilder {
	
	private VersionControlSourceBuilder sourceBuilder;
	
	public PackageDataImageBuilder(){
		this.sourceBuilder = new VersionControlSourceBuilder();
	}

	public DependencyReflection getMock(String identifier){
		String version = "v1." + identifier;
		String name = "name" + identifier;
		String ref = "reference" + identifier;
		String url = "url" + identifier;
		String type = getRandomSourceType();
		VersionControlSource source = sourceBuilder.getMock(ref, url, type);
		
		DependencyReflection pkg = mock(DependencyReflection.class);
		when(pkg.getName()).thenReturn(name);
		when(pkg.getVersion()).thenReturn(version);
		when(pkg.getSource()).thenReturn(source);
		
		return pkg;
	}
	
	private String getRandomSourceType(){
		Random r = new Random();
		int low = 1;
		int high = 2;
		int result = r.nextInt(high-low) + low;
		if (result == 1){
			return "git";
		} else {
			return "svn";
		}
	}
}
