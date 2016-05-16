package de.sandritter.version_analysis_of_build_dependencies;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.sandritter.version_analysis_of_build_dependencies.IntegrationAnalyser;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Analyse.AnalyseResult;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Analyse.DependencyResult;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Analyse.Disparity;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildData;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildDataBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory.DataLoaderFactory;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Interface.DataLoader;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Module.PersistenceModule;

public class IntegrationAnalyserTest {

	private BuildDataBuilder buildDataBuilder;
	private DataLoader dataLoader;
	private BuildData buildData;

	@Before
	public void setUp()
	{
		buildDataBuilder = new BuildDataBuilder();
		this.buildData = buildDataBuilder.getLightMock();

		ClassLoader loader = getClass().getClassLoader();
		String dbPath = loader.getResource("jevi.db").getPath();

		Injector injector = Guice.createInjector(new PersistenceModule());
		DataLoaderFactory dataLoaderFactory = injector.getInstance(DataLoaderFactory.class);
		this.dataLoader = dataLoaderFactory.create(dbPath);
	}

	@Test
	public void testIntegrationAnalysis() throws Exception
	{
		IntegrationAnalyser analyzer = new IntegrationAnalyser(buildData, dataLoader);
		AnalyseResult result = analyzer.getResults();
		assertEquals(5, result.size());
		for (DependencyResult depResult : result) {
			if (depResult.getAnalyseTarget().getComponentName().equals("Checkout")) {
				assertEquals(3, depResult.getDisparities().size());
				assertEquals("WARNING", depResult.getStatus());
			} else if (depResult.getAnalyseTarget().getComponentName().equals("Kunde")) {
				assertEquals("OK", depResult.getStatus());
				assertNull(depResult.getDisparities());
			} else if (depResult.getAnalyseTarget().getComponentName().equals("Produkt-Katalog")) {
				assertEquals("OK", depResult.getStatus());
				assertNull(depResult.getDisparities());
			} else if (depResult.getAnalyseTarget().getComponentName().equals("Billing")) {
				assertEquals("WARNING", depResult.getStatus());
				assertEquals(3, depResult.getDisparities().size());
			} else if (depResult.getAnalyseTarget().getComponentName().equals("Warenkorb")) {
				assertEquals("WARNING", depResult.getStatus());
				assertEquals(2, depResult.getDisparities().size());
				for (Disparity d : depResult.getDisparities()) {
					if (d.getTestedVersion().getComponentName().equals("Kunde")) {
						assertEquals("1.0", d.getTestedVersion().getVersion());
						assertEquals("1.1", d.getInstalledVersion().getVersion());
					}
				}
			} else {
				fail();
			}
		}
	}

	@Test
	public void testIntegrationAnalysisFailure() throws Exception
	{
		@SuppressWarnings("unused")
		IntegrationAnalyser analyzer = new IntegrationAnalyser(buildDataBuilder.getDefectMock(), dataLoader);
	}
}
