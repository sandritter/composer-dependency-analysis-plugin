package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Component;

public class ComponentBuilder {

	public Component getMock(String componentName, String sourceUrl, String sourceType){
		Component source = mock(Component.class);
		when(source.getSourceType()).thenReturn(sourceType);
		when(source.getSourceUrl()).thenReturn(sourceUrl);
		when(source.getName()).thenReturn(componentName);
		return source;
	}
}
