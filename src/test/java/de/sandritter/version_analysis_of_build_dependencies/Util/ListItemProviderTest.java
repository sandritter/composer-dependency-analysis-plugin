package de.sandritter.version_analysis_of_build_dependencies.Util;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;
import de.sandritter.version_analysis_of_build_dependencies.Util.ListItemProvider;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;

public class ListItemProviderTest {

	private Path workspacePath;
	
	@Before
	public void setUp()
	{
		String absoluteFilePath = getClass().getClassLoader().getResource("composer.json").getPath();
		File f = new File(absoluteFilePath);
		this.workspacePath = new File(f.getParent()).toPath();
	}
	
	@Test
	public void shouldCreateListItemProvider()
	{
		@SuppressWarnings("unused")
		ListItemProvider listItemProvider = new ListItemProvider();
	}
	
	@Test 
	public void shouldFillListWithAllJsonItems()
	{
		ListBoxModel lst = ListItemProvider.fillPathItems(workspacePath, FileType.COMPOSER_JSON);
		assertEquals(6, lst.size());
	}
	
	@Test 
	public void shouldFillListWithSpecificJsonItems()
	{
		//set up
		List<String> options = new ArrayList<String>();
		options.add("composer.json");
		options.add("source/composer.json");
		options.add("test/composer.json");
		options.add("TYPO3/extension1/composer.json");
		options.add("TYPO3/extension2/composer.json");
		options.add("default");
		
		//perform
		ListBoxModel lst = ListItemProvider.fillPathItems(workspacePath, FileType.COMPOSER_JSON);
		
		//assert
		for (Option option : lst) {
			assertTrue(options.contains(option.value));
		}
	}
	
	@Test 
	public void shouldFillListWithAllLockItems()
	{
		ListBoxModel lst = ListItemProvider.fillPathItems(workspacePath, FileType.COMPOSER_LOCK);
		assertEquals(7, lst.size());
	}
	
	@Test 
	public void shouldFillListWithSpecificLockItems()
	{
		//set up
		List<String> options = new ArrayList<String>();
		options.add("composer.lock");
		options.add("source/composer.lock");
		options.add("test/composer.lock");
		options.add("TYPO3/extension1/composer.lock");
		options.add("TYPO3/extension2/composer.lock");
		options.add("composer/composer.lock");
		options.add("default");
		
		//perform
		ListBoxModel lst = ListItemProvider.fillPathItems(workspacePath, FileType.COMPOSER_LOCK);
		
		//assert
		for (Option option : lst) {
			assertTrue(options.contains(option.value));
		}
	}
	
	@Test
	public void shouldOnlyReturnDefaultOptionWhenWorkspaceIsNull()
	{
		ListBoxModel lst = ListItemProvider.fillPathItems(null, FileType.COMPOSER_JSON);
		assertEquals(1, lst.size());
		assertEquals("default", lst.get(0).value);
	}
}
