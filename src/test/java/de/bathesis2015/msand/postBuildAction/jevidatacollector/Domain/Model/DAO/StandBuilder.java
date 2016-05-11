package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Stand;

public class StandBuilder {
	
	public Stand getMock(String reference, String version, String componentName){
		Stand source = mock(Stand.class);
		when(source.getReference()).thenReturn(reference);
		when(source.getVersion()).thenReturn(version);
		when(source.getComponentName()).thenReturn(componentName);
		return source;
	}
}
