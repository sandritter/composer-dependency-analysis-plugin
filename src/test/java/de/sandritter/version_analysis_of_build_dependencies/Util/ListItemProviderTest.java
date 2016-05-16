package de.sandritter.version_analysis_of_build_dependencies.Util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;
import de.sandritter.version_analysis_of_build_dependencies.Util.ListItemProvider;
import hudson.util.ListBoxModel;

public class ListItemProviderTest {

	private String workspacePath;
	
	@Before
	public void setUp()
	{
		String absoluteFilePath = getClass().getClassLoader().getResource("composer.json").getPath();
		File f = new File(absoluteFilePath);
		this.workspacePath = f.getParent();
	}
	
	@Test 
	public void shouldFillListWithJsonItems()
	{
		ListBoxModel lst = ListItemProvider.fillPathItems(null, FileType.COMPOSER_JSON);
	}

}
