package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Build;

public class BuildBuilder {

	public Build getMock(String buildId, long timestamp, int number, String jobName){
		Build source = mock(Build.class);
		when(source.getBuildId()).thenReturn(buildId);
		when(source.getTimestamp()).thenReturn(timestamp);
		when(source.getJobName()).thenReturn(jobName);
		return source;
	}
}
