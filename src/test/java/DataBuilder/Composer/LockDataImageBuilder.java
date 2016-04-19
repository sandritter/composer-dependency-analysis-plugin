package DataBuilder.Composer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.LockDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.PackageDataImage;

public class LockDataImageBuilder {
	
	private PackageDataImageBuilder pkgBuilder;
	
	public LockDataImageBuilder(){
		pkgBuilder = new PackageDataImageBuilder();
	}

	public LockDataImage getMock(int amountPackages){
		LockDataImage lock = mock(LockDataImage.class);
		List<PackageDataImage> lst = getPackages(amountPackages);
		when(lock.getPackages()).thenReturn(lst);
		return lock;
	}
	
	private List<PackageDataImage> getPackages(int amount){
		List<PackageDataImage> lst = new ArrayList<PackageDataImage>();
		for (int i = 1; i < amount; i++){
			lst.add(pkgBuilder.getMock(Integer.toString(i)));
		}
		return lst;
	}
}
