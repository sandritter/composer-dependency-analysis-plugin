package DataBuilder.Composer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.PackageDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.VersionControlSource;

public class PackageDataImageBuilder {
	
	private VersionControlSourceBuilder sourceBuilder;
	
	public PackageDataImageBuilder(){
		this.sourceBuilder = new VersionControlSourceBuilder();
	}

	public PackageDataImage getMock(String is){
		String version = "v1." + is;
		String name = "name" + is;
		String ref = "reference" + is;
		String url = "url" + is;
		String type = getRandomSourceType();
		VersionControlSource source = sourceBuilder.getMock(ref, url, type);
		
		PackageDataImage pkg = mock(PackageDataImage.class);
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
