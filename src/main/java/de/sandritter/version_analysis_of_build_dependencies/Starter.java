package de.sandritter.version_analysis_of_build_dependencies;

import java.io.File;
import java.io.IOException;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.DependencyReflectionMapper;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.Interface.DependencyReflectionMapable;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.IO.FileLoader;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.IO.Interface.IOAccess;

public class Starter {

	public static void main(String[] args) throws IOException, DataMappingFailedException
	{
		IOAccess fileLoader = new FileLoader();
		File file = fileLoader.load("/Users/michael.sandritter/jenkins-plugins/version-analysis-of-build-dependencies/src/test/resources");
		DependencyReflectionMapable mapper = new DependencyReflectionMapper();
		DependencyReflectionCollection reflectionCollection = mapper.mapFileToDependencyReflectionData(file);
		System.out.println(reflectionCollection.getPackages().size());
	}

}
