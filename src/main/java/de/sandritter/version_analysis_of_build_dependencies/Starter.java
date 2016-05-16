package de.sandritter.version_analysis_of_build_dependencies;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildData;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Module.MappingModule;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory.DataLoaderFactory;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Interface.DataLoader;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Module.FileLoadModule;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Module.PersistenceModule;

public class Starter { 
	
	public static void main(String[] args) throws Exception {
		
		Injector injector = Guice.createInjector(new FileLoadModule(), new MappingModule(), new PersistenceModule());
		DataLoaderFactory dataLoaderFactory = injector.getInstance(DataLoaderFactory.class);
		DataLoader dataLoader = dataLoaderFactory.create("/Users/Shared/Jenkins/Home/jevi.db");
		
		BuildData buildData = new BuildData();
		buildData.setBuildId("b-k1-03");
		buildData.setDbPath("/Users/Shared/Jenkins/Home/jevi.db");
		buildData.setNumber(4);
		buildData.setVersion("");
		IntegrationAnalyser analyser = new IntegrationAnalyser(buildData, dataLoader);
		System.out.println("success");
	}
	
}
