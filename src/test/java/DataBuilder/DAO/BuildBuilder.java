package DataBuilder.DAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Build;

public class BuildBuilder {

	public Build getMock(String buildId, long timestamp, int number, String jobName){
		Build source = mock(Build.class);
		when(source.getBuildId()).thenReturn(buildId);
		when(source.getTimestamp()).thenReturn(timestamp);
		when(source.getJobName()).thenReturn(jobName);
		return source;
	}
}
