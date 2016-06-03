package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.DependencyReflectionMapper;

public class DependencyRelectionCollectionTest {

	@Test
	public void shouldFilterPackages() throws DataMappingFailedException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("composerFilter.lock").getFile());
		DependencyReflectionMapper mapper = new DependencyReflectionMapper();
		DependencyReflectionCollection collection = mapper.mapFileToDependencyReflectionData(file);
		assertEquals(4, collection.getPackages().size());
	}
	
	@Test
	public void shouldGetAllPackages() throws DataMappingFailedException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("composerFilter2.lock").getFile());
		DependencyReflectionMapper mapper = new DependencyReflectionMapper();
		DependencyReflectionCollection collection = mapper.mapFileToDependencyReflectionData(file);
		assertEquals(6, collection.getPackages().size());
	}
}
