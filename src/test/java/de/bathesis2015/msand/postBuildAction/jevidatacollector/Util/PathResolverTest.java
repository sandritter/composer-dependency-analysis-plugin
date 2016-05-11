package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.FileType;
import hudson.model.AbstractBuild;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PathResolver.class)
public class PathResolverTest {
	
	private PathResolver pathResolver;
	private String testWorkspacePath;
	
	@Before
	public void setUp() throws Exception
	{
		String absoluteFilePath = getClass().getClassLoader().getResource("composer.json").getPath();
		File f = new File(absoluteFilePath);
		this.testWorkspacePath = f.getParent();
		
		AbstractBuild<?, ?> build = null;
		
		this.pathResolver = PowerMock.createPartialMock(PathResolver.class, "loadWorkspacePath");
		PowerMock.expectPrivate(pathResolver, "loadWorkspacePath", build).andReturn(testWorkspacePath);
		PowerMock.replay(pathResolver);
	}
	
	@Test
	public void shouldGetAbsolutePathOfAComposerJsonFile()
	{
		String relativePath = "/test/composer.json";
		String expected = testWorkspacePath + relativePath;
		String path = pathResolver.getAbsolutePath(FileType.COMPOSER_JSON, relativePath);
		assertEquals(expected, path);
	}
	
	@Test
	public void shouldGetAbsolutePathOfAComposerLockFile()
	{
		String relativePath = "/test/composer.lock";
		String expected = testWorkspacePath + relativePath;
		String path = pathResolver.getAbsolutePath(FileType.COMPOSER_LOCK, relativePath);
		assertEquals(expected, path);
	}
	
	@Test
	public void shouldGetAbsolutePathOfAFileWithDefaultConfiguration()
	{
		String relativePath = "default";
		String expected = testWorkspacePath + "/source/composer.lock";
		String path = pathResolver.getAbsolutePath(FileType.COMPOSER_LOCK, relativePath);
		assertEquals(expected, path);
	}

}
