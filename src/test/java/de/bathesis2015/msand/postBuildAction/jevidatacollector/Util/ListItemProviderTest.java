package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.FileType;
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
