package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflection;

public class LockDataImageBuilder {
	
	private PackageDataImageBuilder pkgBuilder;
	
	public LockDataImageBuilder(){
		pkgBuilder = new PackageDataImageBuilder();
	}

	public DependencyReflectionCollection getMock(int amountPackages){
		DependencyReflectionCollection lock = mock(DependencyReflectionCollection.class);
		List<DependencyReflection> lst = getPackages(amountPackages);
		when(lock.getPackages()).thenReturn(lst);
		return lock;
	}
	
	private List<DependencyReflection> getPackages(int amount){
		List<DependencyReflection> lst = new ArrayList<DependencyReflection>();
		for (int i = 1; i < amount; i++){
			lst.add(pkgBuilder.getMock(Integer.toString(i)));
		}
		return lst;
	}
}
