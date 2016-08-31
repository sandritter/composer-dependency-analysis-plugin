package de.sandritter.version_analysis_of_build_dependencies.Mapping.Facade;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Transport;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.DaoMapper;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.DependencyReflectionMapper;

public class MappingFacadeTest {
	
	private MappingFacade mappingFacade;

	@Test
	public void shouldMapRowData() throws DataMappingFailedException
	{
		// mock data
		DaoMapper daoMapper = mock(DaoMapper.class);
		DependencyReflectionMapper composerMapper = mock(DependencyReflectionMapper.class);
		Transferable transport = mock(Transport.class);
		this.mappingFacade = new MappingFacade(composerMapper, daoMapper, transport);
		
		ClassLoader loader = getClass().getClassLoader();
		String composerJsonPath = loader.getResource("composer.json").getPath();
		String composerLockPath = loader.getResource("composer.lock").getPath();
		
		Map<FileType, File> files = new HashMap<FileType, File>();
		files.put(FileType.COMPOSER_JSON, new File(composerJsonPath));
		files.put(FileType.COMPOSER_LOCK, new File(composerLockPath));
		
		when(composerMapper.mapFileToJsonDataImage(null)).thenReturn(null);
	
		// execute
		
		// verify
	}

}
