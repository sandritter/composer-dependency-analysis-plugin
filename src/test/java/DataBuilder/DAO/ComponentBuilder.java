package DataBuilder.DAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Component;

public class ComponentBuilder {

	public Component getMock(String componentName, String sourceUrl, String sourceType){
		Component source = mock(Component.class);
		when(source.getSourceType()).thenReturn(sourceType);
		when(source.getSourceUrl()).thenReturn(sourceUrl);
		when(source.getName()).thenReturn(componentName);
		return source;
	}
}
