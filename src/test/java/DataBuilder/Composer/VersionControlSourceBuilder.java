package DataBuilder.Composer;

import static org.mockito.Mockito.*;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.VersionControlSource;

public class VersionControlSourceBuilder {

	public VersionControlSource getMock(String reference, String sourceUrl, String sourceType){
		VersionControlSource source = mock(VersionControlSource.class);
		when(source.getReference()).thenReturn(reference);
		when(source.getType()).thenReturn(sourceType);
		when(source.getUrl()).thenReturn(sourceUrl);
		return source;
	}
}
